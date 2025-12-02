package com.companies.serivce_request.client.dto;

import java.util.UUID;

public record ServiceCatalogServiceResponse(
        UUID serviceId,
        String serviceCode,
        String nameEn,
        Boolean eligibilityRequired,
        Boolean accessRightsRequired,
        Boolean defaultInspectionRequired,
        UUID owningAgencyId,
        Boolean active
) {}
