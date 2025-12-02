package com.companies.serivce_catalog.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "service")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceEntity {

    @Id
    @Column(name = "service_id", nullable = false, updatable = false)
    private UUID serviceId;

    @Column(name = "service_code", nullable = false, unique = true, length = 50)
    private String serviceCode;

    @Column(name = "name_en", nullable = false, length = 255)
    private String nameEn;

    @Column(name = "name_ar", length = 255)
    private String nameAr;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    // For POC: keep as plain UUID fields, no FK mapping
    @Column(name = "domain_id")
    private UUID domainId;

    @Column(name = "primary_category_id")
    private UUID primaryCategoryId;

    @Column(name = "owning_agency_id")
    private UUID owningAgencyId;

    @Column(name = "eligibility_required", nullable = false)
    private boolean eligibilityRequired;

    @Column(name = "access_rights_required", nullable = false)
    private boolean accessRightsRequired;

    @Column(name = "default_inspection_required", nullable = false)
    private boolean defaultInspectionRequired;

    @Column(name = "is_active", nullable = false)
    private boolean active;

    @Column(name = "display_order")
    private Integer displayOrder;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @PrePersist
    void prePersist() {
        if (serviceId == null) {
            serviceId = UUID.randomUUID();
        }
        OffsetDateTime now = OffsetDateTime.now();
        createdAt = now;
        updatedAt = now;

        // defaults like in DDL
        // eligibility_required/access_rights_required/default_inspection_required default = false
        // is_active default = true
        if (!this.active) {
            this.active = true;
        }
        if (this.displayOrder == null) {
            this.displayOrder = 0;
        }
    }

    @PreUpdate
    void preUpdate() {
        updatedAt = OffsetDateTime.now();
    }
}
