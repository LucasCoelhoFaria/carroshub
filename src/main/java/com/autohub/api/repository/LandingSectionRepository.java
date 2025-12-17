package com.autohub.api.repository;

import com.autohub.api.repository.entity.Business;
import com.autohub.api.repository.entity.LandingSection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LandingSectionRepository extends JpaRepository<LandingSection, Long> {

    List<LandingSection> findByBusinessAndEnabledTrueOrderByIdAsc(Business business);

    List<LandingSection> findByBusinessOrderByIdAsc(Business business);

    Optional<LandingSection> findByBusinessAndType(Business business, String type);

    Optional<LandingSection> findByIdAndBusiness(Long id, Business business);

    boolean existsByBusinessAndType(Business business, String type);

    List<LandingSection> findByBusinessId(UUID business);
    List<LandingSection> findByBusiness(Business business);
}
