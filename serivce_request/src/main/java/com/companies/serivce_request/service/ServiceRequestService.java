package com.companies.serivce_request.service;

import com.companies.serivce_request.client.ServiceCatalogClient;
import com.companies.serivce_request.client.dto.ServiceCatalogServiceResponse;
import com.companies.serivce_request.dto.*;
import com.companies.serivce_request.entity.*;
import com.companies.serivce_request.exception.ResourceNotFoundException;
import com.companies.serivce_request.repository.ServiceRequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ServiceRequestService {

    private final ServiceRequestRepository repository;
    private final ServiceCatalogClient serviceCatalogClient;

    public ServiceRequestService(ServiceRequestRepository repository,
                                 ServiceCatalogClient serviceCatalogClient) {
        this.repository = repository;
        this.serviceCatalogClient = serviceCatalogClient;
    }
    // Create with calling service catalog service

     // CREATE DRAFT
    public ServiceRequestResponse createFromCatalog(ServiceRequestSmartCreateRequest req) {

        // Convert req strings to UUID and Long
        UUID serviceuuid = UUID.fromString(req.serviceId());
        UUID investorUuid = UUID.fromString(req.investorId());
        Long companyLong = Long.parseLong(req.companyId());

        // 1) Get service config from Service Catalog (sync)
        
        ServiceCatalogServiceResponse svc = serviceCatalogClient.getServiceById(serviceuuid);

        System.out.println("Response == "+ svc );
        if (svc == null || Boolean.FALSE.equals(svc.active())) {
            throw new ResourceNotFoundException("Service not found or inactive: " + req.serviceId());
        }

        // 2) Build entity
        ServiceRequest entity = new ServiceRequest();
        entity.setServiceId(serviceuuid);
        entity.setUserId(req.userId());
        entity.setEgyid(req.egyid());
        entity.setInvestorId(investorUuid);
        entity.setCompanyId(companyLong);

        // Flags copied from Service Catalog
        entity.setEligibilityRequired(Boolean.TRUE.equals(svc.eligibilityRequired()));
        entity.setAccessRightsRequired(Boolean.TRUE.equals(svc.accessRightsRequired()));
        entity.setInspectionRequired(Boolean.TRUE.equals(svc.defaultInspectionRequired()));

        // Fees Amount
        entity.setTotalFeesAmount(BigDecimal.valueOf(req.totalFeesAmount()));
        entity.setFeesApproved(true);

        // Owning agency as default primary agency
        entity.setPrimaryAgencyId(svc.owningAgencyId());

        // Defaults
        entity.setDeliverablePreference(
                req.deliverablePreference() != null ? req.deliverablePreference() : DeliverablePreference.DIGITAL
        );
        entity.setDeliverableAddress(req.deliverableAddress());

        entity.setStatus(ServiceRequestStatus.SUBMITTED);

        ServiceRequest saved = repository.save(entity);
        return toResponse(saved);
    }

    // CREATE DRAFT
    public ServiceRequestResponse create(ServiceRequestCreateRequest req) {
        ServiceRequest entity = new ServiceRequest();

        entity.setServiceId(req.serviceId());
        entity.setUserId(req.userId());
        entity.setEgyid(req.egyid());
        entity.setInvestorId(req.investorId());
        entity.setCompanyId(Long.parseLong(req.companyId()));

        entity.setEligibilityRequired(Boolean.TRUE.equals(req.eligibilityRequired()));
        entity.setAccessRightsRequired(Boolean.TRUE.equals(req.accessRightsRequired()));
        entity.setInspectionRequired(Boolean.TRUE.equals(req.inspectionRequired()));

        entity.setPrimaryAgencyId(req.primaryAgencyId());
        entity.setSecondaryAgenciesJson(req.secondaryAgenciesJson());
        // entity.setRequiredDataIdsJson(req.requiredDataIdsJson());

        entity.setDeliverablePreference(
                req.deliverablePreference() != null ? req.deliverablePreference() : DeliverablePreference.DIGITAL
        );
        entity.setDeliverableAddress(req.deliverableAddress());

        entity.setStatus(ServiceRequestStatus.SUBMITTED);

        ServiceRequest saved = repository.save(entity);
        return toResponse(saved);
    }

    // SUBMIT (DRAFT -> SUBMITTED)
    public ServiceRequestResponse submit(UUID id) {
        ServiceRequest entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service request not found: " + id));

        entity.setStatus(ServiceRequestStatus.SUBMITTED);
        entity.setSubmittedAt(java.time.OffsetDateTime.now());

        return toResponse(entity);
    }

    // GET
    @Transactional(readOnly = true)
    public List<ServiceRequestResponse> getAll(ServiceRequestStatus status, String userId) {
        List<ServiceRequest> list;

        if (status != null) {
            list = repository.findByStatus(status);
        } else if (userId != null) {
            list = repository.findByUserId(userId);
        } else {
            list = repository.findAll();
        }

        return list.stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public ServiceRequestResponse getById(UUID id) {
        ServiceRequest entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service request not found: " + id));
        return toResponse(entity);
    }

    // STATUS UPDATE
    public ServiceRequestResponse updateStatus(UUID id, ServiceRequestStatusUpdateRequest req) {
        ServiceRequest entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service request not found: " + id));

        entity.setStatus(req.status());
        entity.setStatusReason(req.statusReason());

        // set completedAt on terminal statuses
        if (req.status() == ServiceRequestStatus.REJECTED
                || req.status() == ServiceRequestStatus.CANCELLED
                || req.status() == ServiceRequestStatus.PUBLISH_APPROVE) {
            entity.setCompletedAt(java.time.OffsetDateTime.now());
        }

        return toResponse(entity);
    }

    // FEES + PAYMENT UPDATE
    public ServiceRequestResponse updateFeesAndPayment(UUID id, ServiceRequestFeesPaymentUpdateRequest req) {
        ServiceRequest entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service request not found: " + id));

        if (req.totalFeesAmount() != null) {
            entity.setTotalFeesAmount(BigDecimal.valueOf(req.totalFeesAmount()));
        }
        if (req.serviceFeesJson() != null) {
            entity.setServiceFeesJson(req.serviceFeesJson());
        }
        if (req.feesApproved() != null) {
            entity.setFeesApproved(req.feesApproved());
        }

        if (req.paymentStatus() != null) {
            entity.setPaymentStatus(req.paymentStatus());
        }
        if (req.paymentReference() != null) {
            entity.setPaymentReference(req.paymentReference());
        }
        if (req.paymentReceiptNo() != null) {
            entity.setPaymentReceiptNo(req.paymentReceiptNo());
        }
        if (req.paymentDate() != null) {
            entity.setPaymentDate(req.paymentDate());
        }

        return toResponse(entity);
    }

    // REQUIRED DATA UPDATE
    public ServiceRequestResponse updateRequiredData(UUID id, ServiceRequestRequiredDataUpdateRequest req) {
        ServiceRequest entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service request not found: " + id));

        // if (req.requiredDataRetrievedJson() != null) {
        //     entity.setRequiredDataRetrievedJson(req.requiredDataRetrievedJson());
        // }
        // if (req.requiredDataUploadedJson() != null) {
        //     entity.setRequiredDataUploadedJson(req.requiredDataUploadedJson());
        // }
        // if (req.requiredDataFinalJson() != null) {
        //     entity.setRequiredDataFinalJson(req.requiredDataFinalJson());
        // }

        return toResponse(entity);
    }

    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Service request not found: " + id);
        }
        repository.deleteById(id);
    }

    // Mapper
    private ServiceRequestResponse toResponse(ServiceRequest e) {
        return new ServiceRequestResponse(
                e.getServiceRequestId(),
                e.getServiceId(),
                e.getUserId(),
                e.getEgyid(),
                e.getInvestorId(),
                e.getCompanyId(),
                e.isEligibilityRequired(),
                e.getEligibilityApproved(),
                e.getEligibilityCheckedAt(),
                e.isAccessRightsRequired(),
                e.getAccessRightsApproved(),
                e.getAccessCheckedAt(),
                e.getPrimaryAgencyId(),
                e.getSecondaryAgenciesJson(),
                e.isInspectionRequired(),
                e.getInspectionStatus(),
                e.getInspectionReference(),
                e.getTotalFeesAmount().doubleValue(),
                e.getServiceFeesJson(),
                e.getFeesApproved(),
                e.getPaymentStatus(),
                e.getPaymentReference(),
                e.getPaymentReceiptNo(),
                e.getPaymentDate(),
                // e.getRequiredDataIdsJson(),
                // e.getRequiredDataRetrievedJson(),
                // e.getRequiredDataUploadedJson(),
                // e.getRequiredDataFinalJson(),
                e.getDeliverablePreference(),
                e.getDeliverableAddress(),
                e.getStatus(),
                e.getStatusReason(),
                e.getCreatedAt(),
                e.getSubmittedAt(),
                e.getCompletedAt(),
                e.getLastUpdatedAt()
        );
    }
}
