package com.companies.investor_profile.repository;


import com.companies.investor_profile.entity.InvestorProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface InvestorProfileRepository extends JpaRepository<InvestorProfile, UUID> {

    Optional<InvestorProfile> findByEgyid(String egyid);
}
