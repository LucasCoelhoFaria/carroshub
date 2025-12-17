package com.autohub.api.service;

import com.autohub.api.repository.BusinessRepository;
import com.autohub.api.repository.FaqRepository;
import com.autohub.api.repository.LandingSectionRepository;
import com.autohub.api.repository.ServiceRepository;
import com.autohub.api.repository.entity.Business;
import com.autohub.api.repository.entity.Faq;
import com.autohub.api.repository.entity.LandingSection;
import com.autohub.api.repository.entity.ServiceEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BusinessService {

    private final BusinessRepository businessRepository;
    private final LandingSectionRepository sectionRepository;
    private final ServiceRepository serviceRepository;
    private final FaqRepository faqRepository;

    private final BusinessSeedService businessSeedService;

    public Business createBusiness(
            String businessName,
            String googleTokenHash,
            String whatsapp
    ) {
        if (businessRepository.existsByBusinessName(businessName)) {
            throw new IllegalArgumentException("Empreendimento já existe");
        }

        if (businessRepository.existsByGoogleTokenHash(googleTokenHash)) {
            throw new IllegalArgumentException("Token já utilizado");
        }

        Business business = new Business();
        business.setBusinessName(businessName.toLowerCase());
        business.setGoogleTokenHash(googleTokenHash);
        business.setWhatsapp(whatsapp);

        Business savedBusiness = businessRepository.save(business);

        businessSeedService.createDefaultLanding(savedBusiness);

        return savedBusiness;
    }

    public Optional<Business> findByBusinessName(String name) {
        return businessRepository.findByBusinessName(name.toLowerCase());
    }

    public Optional<Business> findByTokenHash(String tokenHash) {
        return businessRepository.findByGoogleTokenHash(tokenHash);
    }

    public Optional<Map<String, Object>> getFullBusinessData(String businessName) {
        return businessRepository.findByBusinessNameIgnoreCase(businessName)
                .map(business -> {
                    Map<String, Object> result = new HashMap<>();
                    result.put("id", business.getId());
                    result.put("businessName", business.getBusinessName());
                    result.put("whatsapp", business.getWhatsapp());

                    List<LandingSection> sections = sectionRepository
                            .findByBusinessAndEnabledTrueOrderByIdAsc(business);
                    Map<String, Object> landingSections = sections.stream()
                            .collect(Collectors.toMap(
                                    LandingSection::getType,
                                    LandingSection::getContent
                            ));
                    result.put("landingSections", landingSections);

                    result.put("hero", landingSections.get("HERO"));
                    result.put("footer", landingSections.get("FOOTER"));

                    List<ServiceEntity> services = serviceRepository.findByBusinessAndEnabledTrue(business);
                    result.put("services", services);

                    List<Faq> faqs = faqRepository.findByBusinessAndEnabledTrue(business);
                    result.put("faq", faqs);

                    return result;
                });
    }
}
