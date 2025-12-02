package com.companies.investor_profile.mapper;

import com.companies.investor_profile.dto.InvestorProfileRequest;
import com.companies.investor_profile.dto.InvestorProfileResponse;
import com.companies.investor_profile.entity.InvestorProfile;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InvestorProfileMapper {

    // create: request -> entity (id, timestamps handled in entity)
    @Mapping(target = "investorId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    InvestorProfile toEntity(InvestorProfileRequest request);

    // update: merge request into existing entity
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "investorId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromRequest(InvestorProfileRequest request, @MappingTarget InvestorProfile entity);

    // entity -> response
    InvestorProfileResponse toResponse(InvestorProfile entity);

    List<InvestorProfileResponse> toResponseList(List<InvestorProfile> entities);
}
