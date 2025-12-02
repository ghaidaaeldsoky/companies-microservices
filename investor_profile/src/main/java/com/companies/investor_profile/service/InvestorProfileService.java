package com.companies.investor_profile.service;

import com.companies.investor_profile.exception.ResourceNotFoundException;
import com.companies.investor_profile.mapper.InvestorProfileMapper;
import com.companies.investor_profile.dto.InvestorProfileRequest;
import com.companies.investor_profile.dto.InvestorProfileResponse;
import com.companies.investor_profile.entity.InvestorProfile;
import com.companies.investor_profile.repository.InvestorProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class InvestorProfileService {

    // private final InvestorProfileRepository repository;

    // public InvestorProfileService(InvestorProfileRepository repository) {
    //     this.repository = repository;
    // }

    // // ---------------------------------------------------------
    // // CREATE
    // // ---------------------------------------------------------
    // public InvestorProfileResponse create(InvestorProfileRequest request) {
    //     InvestorProfile entity = new InvestorProfile();

    //     mapRequestToEntity(request, entity);

    //     InvestorProfile saved = repository.save(entity);
    //     return mapEntityToResponse(saved);
    // }

    // // ---------------------------------------------------------
    // // GET ALL
    // // ---------------------------------------------------------
    // @Transactional(readOnly = true)
    // public List<InvestorProfileResponse> getAll() {
    //     return repository.findAll().stream()
    //             .map(this::mapEntityToResponse)
    //             .toList();
    // }

    // // ---------------------------------------------------------
    // // GET BY ID
    // // ---------------------------------------------------------
    // @Transactional(readOnly = true)
    // public InvestorProfileResponse getById(UUID id) {
    //     InvestorProfile entity = repository.findById(id)
    //             .orElseThrow(() -> new ResourceNotFoundException("Investor not found: " + id));

    //     return mapEntityToResponse(entity);
    // }

    // // ---------------------------------------------------------
    // // GET BY NATIONAL ID (egyid)
    // // ---------------------------------------------------------
    // @Transactional(readOnly = true)
    // public InvestorProfileResponse getByEgyid(String egyid) {
    //     InvestorProfile entity = repository.findByEgyid(egyid)
    //             .orElseThrow(() -> new ResourceNotFoundException("Investor not found for EGYID: " + egyid));

    //     return mapEntityToResponse(entity);
    // }

    // // ---------------------------------------------------------
    // // UPDATE
    // // ---------------------------------------------------------
    // public InvestorProfileResponse update(UUID id, InvestorProfileRequest request) {
    //     InvestorProfile entity = repository.findById(id)
    //             .orElseThrow(() -> new ResourceNotFoundException("Investor not found: " + id));

    //     mapRequestToEntity(request, entity); // overwrite simple fields

    //     return mapEntityToResponse(entity);
    // }

    // // ---------------------------------------------------------
    // // DELETE
    // // ---------------------------------------------------------
    // public void delete(UUID id) {
    //     if (!repository.existsById(id)) {
    //         throw new ResourceNotFoundException("Investor not found: " + id);
    //     }
    //     repository.deleteById(id);
    // }

    // // ---------------------------------------------------------
    // // MAPPING HELPERS
    // // ---------------------------------------------------------
    // private void mapRequestToEntity(InvestorProfileRequest req, InvestorProfile entity) {
    //     entity.setEgyid(req.egyid());
    //     entity.setUserId(req.userId());
    //     entity.setFullNameEn(req.fullNameEn());
    //     entity.setFullNameAr(req.fullNameAr());
    //     entity.setDateOfBirth(req.dateOfBirth());
    //     entity.setNationality(req.nationality());
    //     entity.setAddressLine1(req.addressLine1());
    //     entity.setAddressLine2(req.addressLine2());
    //     entity.setCity(req.city());
    //     entity.setGovernorate(req.governorate());
    //     entity.setCountry(req.country());
    //     entity.setEmail(req.email());
    //     entity.setMobileNumber(req.mobileNumber());
    // }

    // private InvestorProfileResponse mapEntityToResponse(InvestorProfile e) {
    //     return new InvestorProfileResponse(
    //             e.getInvestorId(),
    //             e.getEgyid(),
    //             e.getUserId(),
    //             e.getFullNameEn(),
    //             e.getFullNameAr(),
    //             e.getDateOfBirth(),
    //             e.getNationality(),
    //             e.getAddressLine1(),
    //             e.getAddressLine2(),
    //             e.getCity(),
    //             e.getGovernorate(),
    //             e.getCountry(),
    //             e.getEmail(),
    //             e.getMobileNumber(),
    //             e.getCreatedAt(),
    //             e.getUpdatedAt()
    //     );
    // }


    private final InvestorProfileRepository repository;
    private final InvestorProfileMapper mapper;

    public InvestorProfileService(InvestorProfileRepository repository,
                                  InvestorProfileMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public InvestorProfileResponse create(InvestorProfileRequest request) {
        System.out.println("Printing before adding..");
        InvestorProfile entity = mapper.toEntity(request);
        System.out.println("Printing after mapping and before saving");
        InvestorProfile saved = repository.save(entity);
        System.out.println("After saving");
        return mapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<InvestorProfileResponse> getAll() {
        return mapper.toResponseList(repository.findAll());
    }

    @Transactional(readOnly = true)
    public InvestorProfileResponse getById(UUID id) {
        InvestorProfile entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Investor not found: " + id));
        return mapper.toResponse(entity);
    }

    @Transactional(readOnly = true)
    public InvestorProfileResponse getByEgyid(String egyid) {
        InvestorProfile entity = repository.findByEgyid(egyid)
                .orElseThrow(() -> new ResourceNotFoundException("Investor not found for EGYID: " + egyid));
        return mapper.toResponse(entity);
    }

    public InvestorProfileResponse update(UUID id, InvestorProfileRequest request) {
        InvestorProfile entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Investor not found: " + id));

        mapper.updateEntityFromRequest(request, entity);
        // entity will be auto-saved at transaction end
        return mapper.toResponse(entity);
    }

    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Investor not found: " + id);
        }
        repository.deleteById(id);
    }
}
