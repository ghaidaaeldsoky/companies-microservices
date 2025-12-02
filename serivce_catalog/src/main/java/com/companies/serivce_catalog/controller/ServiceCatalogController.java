package com.companies.serivce_catalog.controller;

import com.companies.serivce_catalog.api.ApiResponse;
import com.companies.serivce_catalog.dto.ServiceRequest;
import com.companies.serivce_catalog.dto.ServiceResponse;
import com.companies.serivce_catalog.dto.UpdateActiveRequest;
import com.companies.serivce_catalog.service.ServiceCatalogService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/services")
public class ServiceCatalogController {

    private final ServiceCatalogService service;

    public ServiceCatalogController(ServiceCatalogService service) {
        this.service = service;
    }

    // add new
    @PostMapping
    public ResponseEntity<ApiResponse<ServiceResponse>> create(
            @Valid @RequestBody ServiceRequest request
    ) {
        ServiceResponse response = service.create(request);
        return ResponseEntity
                .status(201)
                .body(ApiResponse.success("Service created successfully", response));
    }

    // get all (optional active filter)
    @GetMapping
    public ResponseEntity<ApiResponse<List<ServiceResponse>>> getAll(
            @RequestParam(required = false) Boolean active
    ) {
        List<ServiceResponse> list = service.getAll(active);
        return ResponseEntity.ok(
                ApiResponse.success("Services retrieved successfully", list)
        );
    }

    // get by id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ServiceResponse>> getById(
            @PathVariable("id") UUID id
    ) {
        ServiceResponse response = service.getById(id);
        return ResponseEntity.ok(
                ApiResponse.success("Service retrieved successfully", response)
        );
    }

    // get by code (optional extra endpoint)
    @GetMapping("/by-code/{code}")
    public ResponseEntity<ApiResponse<ServiceResponse>> getByCode(
            @PathVariable("code") String code
    ) {
        ServiceResponse response = service.getByCode(code);
        return ResponseEntity.ok(
                ApiResponse.success("Service retrieved successfully by code", response)
        );
    }

    // edit
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ServiceResponse>> update(
            @PathVariable("id") UUID id,
            @Valid @RequestBody ServiceRequest request
    ) {
        ServiceResponse response = service.update(id, request);
        return ResponseEntity.ok(
                ApiResponse.success("Service updated successfully", response)
        );
    }

    // update active flag
    @PatchMapping("/{id}/active")
    public ResponseEntity<ApiResponse<ServiceResponse>> updateActive(
            @PathVariable("id") UUID id,
            @Valid @RequestBody UpdateActiveRequest request
    ) {
        ServiceResponse response = service.updateActive(id, request);
        return ResponseEntity.ok(
                ApiResponse.success("Service active flag updated successfully", response)
        );
    }

    // delete
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable("id") UUID id) {
        service.delete(id);
        return ResponseEntity.ok(
                ApiResponse.success("Service deleted successfully", null)
        );
    }
}
