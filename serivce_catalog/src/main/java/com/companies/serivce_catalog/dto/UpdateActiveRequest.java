package com.companies.serivce_catalog.dto;

import jakarta.validation.constraints.NotNull;

public record UpdateActiveRequest(
        @NotNull Boolean active
) {}
