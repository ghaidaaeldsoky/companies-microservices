package com.companies.serivce_request.repository;

import com.companies.serivce_request.entity.ServiceRequest;
import com.companies.serivce_request.entity.ServiceRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, UUID> {

    List<ServiceRequest> findByUserId(String userId);

    List<ServiceRequest> findByStatus(ServiceRequestStatus status);

    List<ServiceRequest> findByServiceId(UUID serviceId);
}
