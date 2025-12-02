package com.companies.investor_profile.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record InvestorProfileRequest(
        @NotBlank
        @Size(max = 20)
        String egyid,

        @NotBlank
        @Size(max = 100)
        String userId,

        @NotBlank
        @Size(max = 255)
        String fullNameEn,

        String fullNameAr,
        LocalDate dateOfBirth,
        String nationality,

        String addressLine1,
        String addressLine2,
        String city,
        String governorate,
        String country,

        @Email
        String email,

        String mobileNumber
) {}
