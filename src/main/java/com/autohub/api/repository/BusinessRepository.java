package com.autohub.api.repository;


import com.autohub.api.repository.entity.Business;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BusinessRepository extends JpaRepository<Business, UUID> {

    Optional<Business> findByBusinessName(String businessName);

    Optional<Business> findByBusinessNameIgnoreCase(String businessName);

    Optional<Business> findByGoogleTokenHash(String googleTokenHash);

    boolean existsByBusinessName(String businessName);

    boolean existsByGoogleTokenHash(String googleTokenHash);

}