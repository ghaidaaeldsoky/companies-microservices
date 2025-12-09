package com.companies.serivce_request.controller;

import com.companies.serivce_request.api.ApiResponse;
import com.companies.serivce_request.dto.*;
import com.companies.serivce_request.entity.ServiceRequestStatus;
import com.companies.serivce_request.service.ServiceRequestService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/requests")
public class ServiceRequestController {

    private final ServiceRequestService service;

    public ServiceRequestController(ServiceRequestService service) {
        this.service = service;
    }

    // CREATE DRAFT
    @PostMapping
    public ResponseEntity<ApiResponse<ServiceRequestResponse>> create(
            @Valid @RequestBody ServiceRequestCreateRequest req
    ) {
        ServiceRequestResponse response = service.create(req);
        return ResponseEntity.status(201)
                .body(ApiResponse.success("Service request created (DRAFT)", response));
    }

    // CREATE With Catalog service
    @PostMapping("/rest")
    public ResponseEntity<ApiResponse<ServiceRequestResponse>> createFromCatalog(
            @Valid @RequestBody ServiceRequestSmartCreateRequest req
    ) {
        ServiceRequestResponse response = service.createFromCatalog(req);
        return ResponseEntity.status(201)
                .body(ApiResponse.success("Service request created (DRAFT)", response));
    }

    // SUBMIT
    @PostMapping("/{id}/submit")
    public ResponseEntity<ApiResponse<ServiceRequestResponse>> submit(
            @PathVariable("id") UUID id
    ) {
        ServiceRequestResponse response = service.submit(id);
        return ResponseEntity.ok(
                ApiResponse.success("Service request submitted", response)
        );
    }

    // GET ALL (optional filters)
    @GetMapping
    public ResponseEntity<ApiResponse<List<ServiceRequestResponse>>> getAll(
            @RequestParam(required = false) ServiceRequestStatus status,
            @RequestParam(required = false) String userId
    ) {
        List<ServiceRequestResponse> list = service.getAll(status, userId);
        return ResponseEntity.ok(
                ApiResponse.success("Service requests retrieved successfully", list)
        );
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ServiceRequestResponse>> getById(
            @PathVariable("id") UUID id
    ) {
        ServiceRequestResponse response = service.getById(id);
        return ResponseEntity.ok(
                ApiResponse.success("Service request retrieved successfully", response)
        );
    }

    // STATUS UPDATE
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<ServiceRequestResponse>> updateStatus(
            @PathVariable("id") UUID id,
            @Valid @RequestBody ServiceRequestStatusUpdateRequest req
    ) {
        ServiceRequestResponse response = service.updateStatus(id, req);
        return ResponseEntity.ok(
                ApiResponse.success("Service request status updated", response)
        );
    }

    // FEES + PAYMENT
    @PatchMapping("/{id}/fees-payment")
    public ResponseEntity<ApiResponse<ServiceRequestResponse>> updateFeesAndPayment(
            @PathVariable("id") UUID id,
            @Valid @RequestBody ServiceRequestFeesPaymentUpdateRequest req
    ) {
        ServiceRequestResponse response = service.updateFeesAndPayment(id, req);
        return ResponseEntity.ok(
                ApiResponse.success("Fees and payment updated", response)
        );
    }

    // REQUIRED DATA
    @PatchMapping("/{id}/required-data")
    public ResponseEntity<ApiResponse<ServiceRequestResponse>> updateRequiredData(
            @PathVariable("id") UUID id,
            @Valid @RequestBody ServiceRequestRequiredDataUpdateRequest req
    ) {
        ServiceRequestResponse response = service.updateRequiredData(id, req);
        return ResponseEntity.ok(
                ApiResponse.success("Required data updated", response)
        );
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable("id") UUID id) {
        service.delete(id);
        return ResponseEntity.ok(
                ApiResponse.success("Service request deleted", null)
        );
    }
}
