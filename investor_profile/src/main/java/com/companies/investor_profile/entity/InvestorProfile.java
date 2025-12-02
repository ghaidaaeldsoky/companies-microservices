package com.companies.investor_profile.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "investor_profile")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvestorProfile {

    @Id
    @Column(name = "investor_id", nullable = false, updatable = false)
    private UUID investorId;

    @Column(name = "egyid", nullable = false, unique = true, length = 20)
    private String egyid;

    @Column(name = "user_id", nullable = false, length = 100)
    private String userId;

    @Column(name = "full_name_en", nullable = false, length = 255)
    private String fullNameEn;

    @Column(name = "full_name_ar", length = 255)
    private String fullNameAr;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "nationality", length = 100)
    private String nationality;

    @Column(name = "address_line1", length = 255)
    private String addressLine1;

    @Column(name = "address_line2", length = 255)
    private String addressLine2;

    @Column(name = "city", length = 100)
    private String city;

    @Column(name = "governorate", length = 100)
    private String governorate;

    @Column(name = "country", length = 100)
    private String country;

    @Column(name = "email", length = 255)
    private String email;

    @Column(name = "mobile_number", length = 50)
    private String mobileNumber;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @PrePersist
    void prePersist() {
        if (investorId == null) {
            investorId = UUID.randomUUID();
        }
        OffsetDateTime now = OffsetDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    void preUpdate() {
        updatedAt = OffsetDateTime.now();
    }
}
