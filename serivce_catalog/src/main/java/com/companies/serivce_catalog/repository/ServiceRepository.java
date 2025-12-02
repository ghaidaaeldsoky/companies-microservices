package com.companies.serivce_catalog.repository;

import com.companies.serivce_catalog.entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ServiceRepository extends JpaRepository<ServiceEntity, UUID> {

    Optional<ServiceEntity> findByServiceCode(String serviceCode);

    List<ServiceEntity> findByActive(boolean active);
}
