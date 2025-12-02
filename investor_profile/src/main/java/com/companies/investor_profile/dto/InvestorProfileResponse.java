package com.companies.investor_profile.dto;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public record InvestorProfileResponse(
        UUID investorId,
        String egyid,
        String userId,
        String fullNameEn,
        String fullNameAr,
        LocalDate dateOfBirth,
        String nationality,
        String addressLine1,
        String addressLine2,
        String city,
        String governorate,
        String country,
        String email,
        String mobileNumber,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {}
