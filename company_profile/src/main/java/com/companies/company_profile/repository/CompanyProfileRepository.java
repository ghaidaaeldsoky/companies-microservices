package com.companies.company_profile.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.companies.company_profile.entity.CompanyProfile;
import com.companies.company_profile.entity.CompanyStatus;

public interface CompanyProfileRepository extends JpaRepository<CompanyProfile, Long> {
        List<CompanyProfile> findByStatus(CompanyStatus status);
}
