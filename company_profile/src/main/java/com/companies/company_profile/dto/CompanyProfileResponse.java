package com.companies.company_profile.dto;

import java.time.Instant;

import com.companies.company_profile.entity.CompanyStatus;

public record CompanyProfileResponse(
        Long id,
        String name,
        String registrationNumber,
        String address,
        String email,
        String phone,
        CompanyStatus status,
        Instant createdAt,
        Instant updatedAt
) {
    
}
