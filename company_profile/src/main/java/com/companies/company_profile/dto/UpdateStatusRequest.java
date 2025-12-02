package com.companies.company_profile.dto;

import com.companies.company_profile.entity.CompanyStatus;

import jakarta.validation.constraints.NotNull;

public record UpdateStatusRequest(

    @NotNull CompanyStatus status
) {
    
}
