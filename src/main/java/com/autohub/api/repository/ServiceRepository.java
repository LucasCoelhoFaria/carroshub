package com.autohub.api.repository;


import com.autohub.api.repository.entity.Business;
import com.autohub.api.repository.entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {
    List<ServiceEntity> findByBusinessAndEnabledTrueOrderByIdAsc(Business business);

    List<ServiceEntity> findByBusinessOrderByIdAsc(Business business);

    Optional<ServiceEntity> findByIdAndBusiness(Long id, Business business);

    boolean existsByBusinessAndNameIgnoreCase(Business business, String name);

    List<ServiceEntity> findByBusinessAndEnabledTrue(Business business);

    List<ServiceEntity> findByBusinessId(UUID business);

    List<ServiceEntity> findByBusiness(Business business);
}