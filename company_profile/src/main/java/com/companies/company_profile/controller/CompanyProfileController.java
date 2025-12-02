package com.companies.company_profile.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.companies.company_profile.api.ApiResponse;
import com.companies.company_profile.dto.CompanyProfileRequest;
import com.companies.company_profile.dto.CompanyProfileResponse;
import com.companies.company_profile.dto.UpdateStatusRequest;
import com.companies.company_profile.entity.CompanyStatus;
import com.companies.company_profile.service.CompanyProfileService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/companies")
public class CompanyProfileController {
    private final CompanyProfileService service;

    public CompanyProfileController(CompanyProfileService service) {
        this.service = service;
    }

    // add new
    @PostMapping
    public ResponseEntity<ApiResponse<CompanyProfileResponse>> create(
            @Valid @RequestBody CompanyProfileRequest request
    ) {
        CompanyProfileResponse response = service.create(request);
        return ResponseEntity
                .status(201)
                .body(ApiResponse.success("Company created successfully", response));
    }

    // get all (optional status filter)
    @GetMapping
    public ResponseEntity<ApiResponse<List<CompanyProfileResponse>>> getAll(
            @RequestParam(required = false) CompanyStatus status
    ) {
        List<CompanyProfileResponse> list = service.getAll(status);
        return ResponseEntity.ok(
                ApiResponse.success("Companies retrieved successfully", list)
        );
    }

    // get by id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CompanyProfileResponse>> getById(
            @PathVariable Long id
    ) {
        CompanyProfileResponse response = service.getById(id);
        return ResponseEntity.ok(
                ApiResponse.success("Company retrieved successfully", response)
        );
    }

    // edit
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CompanyProfileResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody CompanyProfileRequest request
    ) {
        CompanyProfileResponse response = service.update(id, request);
        return ResponseEntity.ok(
                ApiResponse.success("Company updated successfully", response)
        );
    }

    // delete
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(
                ApiResponse.success("Company deleted successfully", null)
        );
    }

    // update status
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<CompanyProfileResponse>> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateStatusRequest request
    ) {
        CompanyProfileResponse response = service.updateStatus(id, request);
        return ResponseEntity.ok(
                ApiResponse.success("Company status updated successfully", response)
        );
    }
}
