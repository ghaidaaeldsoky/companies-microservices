package com.companies.serivce_request.dto;

public record ServiceRequestRequiredDataUpdateRequest(
        String requiredDataRetrievedJson,
        String requiredDataUploadedJson,
        String requiredDataFinalJson
) {}
