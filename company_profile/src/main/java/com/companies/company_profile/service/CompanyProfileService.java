package com.companies.company_profile.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.companies.company_profile.dto.CompanyProfileRequest;
import com.companies.company_profile.dto.CompanyProfileResponse;
import com.companies.company_profile.dto.UpdateStatusRequest;
import com.companies.company_profile.entity.CompanyProfile;
import com.companies.company_profile.entity.CompanyStatus;
import com.companies.company_profile.exception.ResourceNotFoundException;
import com.companies.company_profile.repository.CompanyProfileRepository;

@Service
@Transactional
public class CompanyProfileService {

    private final CompanyProfileRepository repository;

    public CompanyProfileService(CompanyProfileRepository repository) {
        this.repository = repository;
    }

    public CompanyProfileResponse create(CompanyProfileRequest request) {
        CompanyProfile entity = CompanyProfile.builder()
                .name(request.name())
                .registrationNumber(request.registrationNumber())
                .address(request.address())
                .email(request.email())
                .phone(request.phone())
                .status(request.status() != null ? request.status() : CompanyStatus.ACTIVE)
                .build();

        return toResponse(repository.save(entity));
    }

    @Transactional(readOnly = true)
    public List<CompanyProfileResponse> getAll(CompanyStatus statusFilter) {
        List<CompanyProfile> companies;

        if (statusFilter != null) {
            companies = repository.findByStatus(statusFilter);
        } else {
            companies = repository.findAll();
        }

        return companies.stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public CompanyProfileResponse getById(Long id) {
        CompanyProfile entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found: " + id));
        return toResponse(entity);
    }

    public CompanyProfileResponse update(Long id, CompanyProfileRequest request) {
        CompanyProfile entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found: " + id));

        entity.setName(request.name());
        entity.setRegistrationNumber(request.registrationNumber());
        entity.setAddress(request.address());
        entity.setEmail(request.email());
        entity.setPhone(request.phone());
        if (request.status() != null) {
            entity.setStatus(request.status());
        }

        return toResponse(entity);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Company not found: " + id);
        }
        repository.deleteById(id);
    }

    public CompanyProfileResponse updateStatus(Long id, UpdateStatusRequest request) {
        CompanyProfile entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found: " + id));
        entity.setStatus(request.status());
        return toResponse(entity);
    }

    private CompanyProfileResponse toResponse(CompanyProfile entity) {
        return new CompanyProfileResponse(
                entity.getId(),
                entity.getName(),
                entity.getRegistrationNumber(),
                entity.getAddress(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

}
