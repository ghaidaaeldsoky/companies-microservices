package com.companies.serivce_catalog.mapper;

import com.companies.serivce_catalog.dto.ServiceRequest;
import com.companies.serivce_catalog.dto.ServiceResponse;
import com.companies.serivce_catalog.entity.ServiceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ServiceMapper {

    // Request -> Entity for create / update
    @Mapping(target = "serviceId", ignore = true)    // generated in @PrePersist
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ServiceEntity toEntity(ServiceRequest request);

    // Entity -> Response
    ServiceResponse toResponse(ServiceEntity entity);

    List<ServiceResponse> toResponseList(List<ServiceEntity> entities);
}
