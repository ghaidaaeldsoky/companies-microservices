package com.companies.serivce_request.dto;

import com.companies.serivce_request.entity.ServiceRequestStatus;
import jakarta.validation.constraints.NotNull;

public record ServiceRequestStatusUpdateRequest(
        @NotNull
        ServiceRequestStatus status,
        String statusReason
) {}
