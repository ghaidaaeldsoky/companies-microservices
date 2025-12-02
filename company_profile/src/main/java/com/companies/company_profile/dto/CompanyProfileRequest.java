package com.companies.company_profile.dto;

import com.companies.company_profile.entity.CompanyStatus;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CompanyProfileRequest(
    
        @NotBlank String name,
        String registrationNumber,
        String address,
        @Email String email,
        String phone,
        CompanyStatus status
    ) {
}
