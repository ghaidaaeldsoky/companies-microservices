package com.companies.serivce_request.dto;

import com.companies.serivce_request.entity.DeliverablePreference;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record ServiceRequestCreateRequest(
        @NotNull
        UUID serviceId,

        @NotBlank
        @Size(max = 100)
        String userId,

        @NotBlank
        @Size(max = 20)
        String egyid,

        UUID investorId,
        String companyId,

        Boolean eligibilityRequired,
        Boolean accessRightsRequired,
        Boolean inspectionRequired,

        UUID primaryAgencyId,
        Double totalFeesAmount,

        String secondaryAgenciesJson,   // optional JSON text
        String requiredDataIdsJson,     // optional JSON text

        DeliverablePreference deliverablePreference,
        String deliverableAddress
) {}
