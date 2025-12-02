package com.companies.investor_profile.controller;

import com.companies.investor_profile.api.ApiResponse;
import com.companies.investor_profile.dto.InvestorProfileRequest;
import com.companies.investor_profile.dto.InvestorProfileResponse;
import com.companies.investor_profile.service.InvestorProfileService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/investors")
public class InvestorProfileController {

    private final InvestorProfileService service;

    public InvestorProfileController(InvestorProfileService service) {
        this.service = service;
    }

    // add new
    @PostMapping
    public ResponseEntity<ApiResponse<InvestorProfileResponse>> create(
            @Valid @RequestBody InvestorProfileRequest request
    ) {
        InvestorProfileResponse response = service.create(request);
        return ResponseEntity
                .status(201)
                .body(ApiResponse.success("Investor created successfully", response));
    }

    // get all
    @GetMapping
    public ResponseEntity<ApiResponse<List<InvestorProfileResponse>>> getAll() {
        List<InvestorProfileResponse> list = service.getAll();
        return ResponseEntity.ok(
                ApiResponse.success("Investors retrieved successfully", list)
        );
    }

    // get by id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<InvestorProfileResponse>> getById(
            @PathVariable("id") UUID id
    ) {
        InvestorProfileResponse response = service.getById(id);
        return ResponseEntity.ok(
                ApiResponse.success("Investor retrieved successfully", response)
        );
    }

    // get by national ID (egyid)
    @GetMapping("/by-egyid/{egyid}")
    public ResponseEntity<ApiResponse<InvestorProfileResponse>> getByEgyid(
            @PathVariable("egyid") String egyid
    ) {
        InvestorProfileResponse response = service.getByEgyid(egyid);
        return ResponseEntity.ok(
                ApiResponse.success("Investor retrieved successfully by EGYID", response)
        );
    }

    // edit (full/partial - nulls ignored thanks to MapStruct config)
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<InvestorProfileResponse>> update(
            @PathVariable("id") UUID id,
            @Valid @RequestBody InvestorProfileRequest request
    ) {
        InvestorProfileResponse response = service.update(id, request);
        return ResponseEntity.ok(
                ApiResponse.success("Investor updated successfully", response)
        );
    }

    // delete
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable("id") UUID id) {
        service.delete(id);
        return ResponseEntity.ok(
            ApiResponse.success("Investor deleted successfully", null)
        );
    }
}
