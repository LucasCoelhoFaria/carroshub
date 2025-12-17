package com.autohub.api.service;

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

import java.time.Year;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class BusinessSeedService {

    private final ServiceRepository serviceRepository;
    private final FaqRepository faqRepository;
    private final LandingSectionRepository sectionRepository;


    public void createDefaultLanding(Business business) {
        createHeroSection(business);
        createDefaultServices(business);
        createDefaultFaqs(business);
        createFooterSection(business);
    }

    private void createHeroSection(Business business) {

        LandingSection hero = new LandingSection();
        hero.setType("HERO");
        hero.setEnabled(true);
        hero.setBusiness(business);

        Map<String, Object> content = Map.of(
                "title", business.getBusinessName(),
                "subtitle", "Estética automotiva profissional com qualidade, confiança e acabamento impecável."
        );

        hero.setContent(content);

        sectionRepository.save(hero);
    }


    private void createDefaultServices(Business business) {

        List<ServiceEntity> services = List.of(

                createService("Lavagem Completa",
                        "Lavagem externa e interna detalhada, garantindo limpeza e conservação do veículo.", business),

                createService("Polimento Técnico",
                        "Correção de pintura para remoção de riscos leves e brilho intenso.", business),

                createService("Vitrificação/Cerâmica",
                        "Proteção avançada da pintura com alta durabilidade.", business),

                createService("Higienização Interna",
                        "Limpeza profunda de bancos, teto e carpetes.", business)
        );

        serviceRepository.saveAll(services);
    }

    private ServiceEntity createService(String name, String description, Business business) {
        ServiceEntity s = new ServiceEntity();
        s.setName(name);
        s.setDescription(description);
        s.setEnabled(true);
        s.setBusiness(business);
        return s;
    }

    private void createDefaultFaqs(Business business) {

        List<Faq> faqs = List.of(

                createFaq(
                        "Preciso agendar com antecedência?",
                        "Sim, recomendamos agendamento prévio para garantir o melhor atendimento.",
                        business),

                createFaq(
                        "Quanto tempo dura o serviço?",
                        "O tempo varia conforme o serviço, podendo levar de 2 a 6 horas.",
                        business),

                createFaq(
                        "Quais formas de pagamento são aceitas?",
                        "Aceitamos Pix, cartão de crédito e débito.",
                        business)
        );

        faqRepository.saveAll(faqs);
    }

    private Faq createFaq(String question, String answer, Business business) {
        Faq f = new Faq();
        f.setQuestion(question);
        f.setAnswer(answer);
        f.setEnabled(true);
        f.setBusiness(business);
        return f;
    }

    private void createFooterSection(Business business) {

        LandingSection footer = new LandingSection();
        footer.setType("FOOTER");
        footer.setEnabled(true);
        footer.setBusiness(business);

        Map<String, Object> content = Map.of(
                "text", String.format(
                        "© %d %s - Todos os direitos reservados",
                        Year.now().getValue(),
                        business.getBusinessName()
                )
        );

        footer.setContent(content);

        sectionRepository.save(footer);
    }

}
