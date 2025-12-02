package com.companies.serivce_catalog.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ServiceResponse(
        UUID serviceId,
        String serviceCode,
        String nameEn,
        String nameAr,
        String description,
        UUID domainId,
        UUID primaryCategoryId,
        UUID owningAgencyId,
        boolean eligibilityRequired,
        boolean accessRightsRequired,
        boolean defaultInspectionRequired,
        boolean active,
        Integer displayOrder,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {}
