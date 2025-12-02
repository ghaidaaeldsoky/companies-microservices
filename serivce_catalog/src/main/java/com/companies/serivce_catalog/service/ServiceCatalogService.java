package com.companies.serivce_catalog.service;

import com.companies.serivce_catalog.dto.ServiceRequest;
import com.companies.serivce_catalog.dto.ServiceResponse;
import com.companies.serivce_catalog.dto.UpdateActiveRequest;
import com.companies.serivce_catalog.entity.ServiceEntity;
import com.companies.serivce_catalog.exception.ResourceNotFoundException;
import com.companies.serivce_catalog.mapper.ServiceMapper;
import com.companies.serivce_catalog.repository.ServiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ServiceCatalogService {

    private final ServiceRepository repository;
    private final ServiceMapper mapper;

    public ServiceCatalogService(ServiceRepository repository,
                                 ServiceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    // CREATE
    public ServiceResponse create(ServiceRequest request) {
        ServiceEntity entity = mapper.toEntity(request);
        ServiceEntity saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    // GET ALL (optional active filter)
    @Transactional(readOnly = true)
    public List<ServiceResponse> getAll(Boolean active) {
        List<ServiceEntity> entities;

        if (active != null) {
            entities = repository.findByActive(active);
        } else {
            entities = repository.findAll();
        }

        return mapper.toResponseList(entities);
    }

    // GET BY ID
    @Transactional(readOnly = true)
    public ServiceResponse getById(UUID id) {
        ServiceEntity entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found: " + id));
        return mapper.toResponse(entity);
    }

    // GET BY CODE
    @Transactional(readOnly = true)
    public ServiceResponse getByCode(String code) {
        ServiceEntity entity = repository.findByServiceCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found with code: " + code));
        return mapper.toResponse(entity);
    }

    // UPDATE (full update via PUT)
    public ServiceResponse update(UUID id, ServiceRequest request) {
        ServiceEntity existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found: " + id));

        // Map request to a new entity, then keep id & createdAt
        ServiceEntity updated = mapper.toEntity(request);
        updated.setServiceId(existing.getServiceId());
        updated.setCreatedAt(existing.getCreatedAt());
        // updatedAt will be handled by @PreUpdate

        ServiceEntity saved = repository.save(updated);
        return mapper.toResponse(saved);
    }

    // UPDATE ACTIVE FLAG (PATCH)
    public ServiceResponse updateActive(UUID id, UpdateActiveRequest req) {
        ServiceEntity entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found: " + id));

        entity.setActive(req.active());
        // updatedAt updated via @PreUpdate
        return mapper.toResponse(entity);
    }

    // DELETE
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Service not found: " + id);
        }
        repository.deleteById(id);
    }
}
