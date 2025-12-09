package com.companies.serivce_request.dto;

import java.util.UUID;

import com.companies.serivce_request.entity.DeliverablePreference;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ServiceRequestSmartCreateRequest(
     @NotNull
        String serviceId,

        @NotBlank
        @Size(max = 100)
        String userId,

        @NotBlank
        @Size(max = 20)
        String egyid,

        String investorId,
        String companyId,

        DeliverablePreference deliverablePreference,
        String deliverableAddress
) {
    
}
