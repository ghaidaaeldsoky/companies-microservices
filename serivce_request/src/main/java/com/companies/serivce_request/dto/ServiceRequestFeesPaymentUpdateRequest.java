package com.companies.serivce_request.dto;

import com.companies.serivce_request.entity.PaymentStatus;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record ServiceRequestFeesPaymentUpdateRequest(
        BigDecimal totalFeesAmount,
        String serviceFeesJson,
        Boolean feesApproved,

        PaymentStatus paymentStatus,
        String paymentReference,
        String paymentReceiptNo,
        OffsetDateTime paymentDate
) {}
