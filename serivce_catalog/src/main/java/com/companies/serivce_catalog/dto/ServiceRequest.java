package com.companies.serivce_catalog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record ServiceRequest(
        @NotBlank
        @Size(max = 50)
        String serviceCode,

        @NotBlank
        @Size(max = 255)
        String nameEn,

        String nameAr,
        String description,

        UUID domainId,
        UUID primaryCategoryId,
        UUID owningAgencyId,

        Boolean eligibilityRequired,
        Boolean accessRightsRequired,
        Boolean defaultInspectionRequired,

        Boolean active,
        Integer displayOrder
) {}
