package com.autohub.api.repository;

import com.autohub.api.repository.entity.Business;
import com.autohub.api.repository.entity.Faq;
import com.autohub.api.repository.entity.LandingSection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FaqRepository extends JpaRepository<Faq, Long> {
    List<Faq> findByBusinessAndEnabledTrueOrderByIdAsc(Business business);

    List<Faq> findByBusinessOrderByIdAsc(Business business);

    Optional<Faq> findByIdAndBusiness(Long id, Business business);

    boolean existsByBusinessAndQuestionIgnoreCase(Business business, String question);

    List<Faq> findByBusinessAndEnabledTrue(Business business);

    List<Faq> findByBusinessId(UUID business);

    List<Faq> findByBusiness(Business business);
}
