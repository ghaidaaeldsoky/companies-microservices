package com.companies.serivce_request.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "service_request")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceRequest {

    @Id
    @Column(name = "service_request_id", nullable = false, updatable = false)
    private UUID serviceRequestId;

    @Column(name = "service_id", nullable = false)
    private UUID serviceId;

    @Column(name = "user_id", nullable = false, length = 100)
    private String userId;

    @Column(name = "egyid", nullable = false, length = 20)
    private String egyid;

    @Column(name = "investor_id")
    private UUID investorId;

    @Column(name = "company_id")
    private Long companyId;

    // Eligibility & Access Rights
    @Column(name = "eligibility_required", nullable = false)
    private boolean eligibilityRequired;

    @Column(name = "eligibility_approved")
    private Boolean eligibilityApproved;

    @Column(name = "eligibility_checked_at")
    private OffsetDateTime eligibilityCheckedAt;

    @Column(name = "access_rights_required", nullable = false)
    private boolean accessRightsRequired;

    @Column(name = "access_rights_approved")
    private Boolean accessRightsApproved;

    @Column(name = "access_checked_at")
    private OffsetDateTime accessCheckedAt;

    // Agencies & Inspection
    @Column(name = "primary_agency_id")
    private UUID primaryAgencyId;

    @Column(name = "secondary_agencies", columnDefinition = "text")
    private String secondaryAgenciesJson; // e.g. ["agency-1","agency-2"]

    @Column(name = "inspection_required", nullable = false)
    private boolean inspectionRequired;

    @Enumerated(EnumType.STRING)
    @Column(name = "inspection_status", length = 30)
    private InspectionStatus inspectionStatus;

    @Column(name = "inspection_reference", length = 100)
    private String inspectionReference;

    // Fees & Payment
    @Column(name = "total_fees_amount")
    private Double totalFeesAmount;

    @Column(name = "service_fees", columnDefinition = "text")
    private String serviceFeesJson; // JSON describing items

    @Column(name = "fees_approved")
    private Boolean feesApproved;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", length = 20)
    private PaymentStatus paymentStatus;

    @Column(name = "payment_reference", length = 100)
    private String paymentReference;

    @Column(name = "payment_receipt_no", length = 100)
    private String paymentReceiptNo;

    @Column(name = "payment_date")
    private OffsetDateTime paymentDate;

    // Required Data
    // @Column(name = "required_data_ids", columnDefinition = "text")
    // private String requiredDataIdsJson;

    // @Column(name = "required_data_retrieved", columnDefinition = "text")
    // private String requiredDataRetrievedJson;

    // @Column(name = "required_data_uploaded", columnDefinition = "text")
    // private String requiredDataUploadedJson;

    // @Column(name = "required_data_final", columnDefinition = "text")
    // private String requiredDataFinalJson;

    // Deliverable
    @Enumerated(EnumType.STRING)
    @Column(name = "deliverable_preference", length = 20)
    private DeliverablePreference deliverablePreference;

    @Column(name = "deliverable_address", length = 500)
    private String deliverableAddress;

    // Status lifecycle
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private ServiceRequestStatus status;

    @Column(name = "status_reason", length = 500)
    private String statusReason;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "submitted_at")
    private OffsetDateTime submittedAt;

    @Column(name = "completed_at")
    private OffsetDateTime completedAt;

    @Column(name = "last_updated_at", nullable = false)
    private OffsetDateTime lastUpdatedAt;

    @PrePersist
    void prePersist() {
        if (serviceRequestId == null) {
            serviceRequestId = UUID.randomUUID();
        }
        OffsetDateTime now = OffsetDateTime.now();
        createdAt = now;
        lastUpdatedAt = now;

        if (status == null) {
            status = ServiceRequestStatus.SUBMITTED;
        }
        if (deliverablePreference == null) {
            deliverablePreference = DeliverablePreference.DIGITAL;
        }
        if (inspectionRequired && inspectionStatus == null) {
            inspectionStatus = InspectionStatus.NOT_SCHEDULED;
        }
        if (!inspectionRequired && inspectionStatus == null) {
            inspectionStatus = InspectionStatus.NOT_REQUIRED;
        }
        if (paymentStatus == null) {
            paymentStatus = PaymentStatus.NOT_REQUIRED;
        }
    }

    @PreUpdate
    void preUpdate() {
        lastUpdatedAt = OffsetDateTime.now();
    }
}
