package com.companies.serivce_request.dto;

import com.companies.serivce_request.entity.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record ServiceRequestResponse(
        UUID serviceRequestId,
        UUID serviceId,
        String userId,
        String egyid,
        UUID investorId,
        UUID companyId,

        boolean eligibilityRequired,
        Boolean eligibilityApproved,
        OffsetDateTime eligibilityCheckedAt,

        boolean accessRightsRequired,
        Boolean accessRightsApproved,
        OffsetDateTime accessCheckedAt,

        UUID primaryAgencyId,
        String secondaryAgenciesJson,

        boolean inspectionRequired,
        InspectionStatus inspectionStatus,
        String inspectionReference,

        BigDecimal totalFeesAmount,
        String serviceFeesJson,
        Boolean feesApproved,

        PaymentStatus paymentStatus,
        String paymentReference,
        String paymentReceiptNo,
        OffsetDateTime paymentDate,

        // String requiredDataIdsJson,
        // String requiredDataRetrievedJson,
        // String requiredDataUploadedJson,
        // String requiredDataFinalJson,

        DeliverablePreference deliverablePreference,
        String deliverableAddress,

        ServiceRequestStatus status,
        String statusReason,

        OffsetDateTime createdAt,
        OffsetDateTime submittedAt,
        OffsetDateTime completedAt,
        OffsetDateTime lastUpdatedAt
) {}
