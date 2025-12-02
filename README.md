# 📘 Service Platform – Microservices POC 
A modular microservices platform using Spring Boot, PostgreSQL, Docker, and RESTful APIs to support:
- Investor Profile Service
- Company Profile Service
- Service Catalog Service
- Service Request Service
The system follows **database-per-service, clean API contracts, and dynamic service-to-service communication**.

---
## 🚀 Architecture Overview
Each service runs independently and communicates through REST using configurable URLs defined in application.yml and overridden via environment variables.
```bash
┌─────────────────────┐
│ Investor Profile     │  → PostgreSQL (investor-db)
└─────────────────────┘

┌─────────────────────┐
│ Company Profile      │ → PostgreSQL (company-db)
└─────────────────────┘

┌─────────────────────┐
│ Service Catalog      │ → PostgreSQL (catalog-db)
└─────────────────────┘

┌─────────────────────┐
│ Service Request      │ → PostgreSQL (request-db)
│ (Orchestrator)       │
│  - calls Catalog     │
└─────────────────────┘
```

---
# 🧰 Technologies

- **Java 21**
- **Spring Boot 3.4+**
- **Spring Data JPA**
- **PostgreSQL (Docker)**
- **MapStruct**
- **RestClient (Spring 6)**
- **Lombok**
- **Docker Compose**

---
## 🗂️ Microservices
### 🧩 1. Investor Profile Service

Handles individual investor info.

#### Key fields:
- `investorId`, `egyid`, `userId`
- `fullNameEn`, `fullNameAr`
- Address
- Email, phone

Supports **CRUD + search.**

---
### 🏢 2. Company Profile Service

Manages companies
#### Supports:
- Create/update company Info

---
### 📚 3. Service Catalog Service

Defines the available government services (POC simplified to one table).

#### Fields include:

- `serviceCode`
- `nameEn`, `nameAr`
- `description`
- `eligibilityRequired`
- `accessRightsRequired`
- `defaultInspectionRequired`
- `owningAgencyId`

---
### 📝 4. Service Request Service (Core Orchestrator)

Handles the **entire service request lifecycle**, including:

- Pulling configuration from Service Catalog

- Managing eligibility, access rights, agencies, inspection, fees, payment

- Persisting the request workflow

---
### 📝 Service Request Data Model (Simplified)
```bash
{
  "serviceId": "UUID",
  "userId": "string",
  "egyid": "string",
  "investorId": "UUID",
  "companyId": "UUID",

  "eligibilityRequired": true,
  "eligibilityApproved": null,
  "eligibilityCheckedAt": null,

  "accessRightsRequired": false,
  "accessRightsApproved": null,
  "accessCheckedAt": null,

  "primaryAgencyId": null,
  "secondaryAgenciesJson": null,

  "inspectionRequired": true,
  "inspectionStatus": "NOT_SCHEDULED",
  "inspectionReference": null,

  "totalFeesAmount": null,
  "paymentStatus": "NOT_REQUIRED",

  "deliverablePreference": "DIGITAL",
  "status": "DRAFT"
}

```

---
### 🔄 Service Request Lifecycle
```bash
DRAFT 
  ↓
SUBMITTED
  ↓
INITIAL_APPROVE 
  ↓
COMPLY_APPROVE 
  ↓
FINAL_APPROVE 
  ↓
PUBLISH_APPROVE

REJECTED  (any stage)
CANCELLED (any stage)
```
#### Inspection lifecycle (if required):
```bash
NOT_SCHEDULED → SCHEDULED → COMPLETED
                   ↓
                CANCELLED

```

---
### 🌐 API – Service Request Endpoints
Base URL:
```bash
/api/requests
```
---
### ✅ Create Draft
```bash
POST /api/requests
```
Creates a new request in **DRAFT**.

### ✅ Submit Request
```bash
POST /api/requests/{id}/submit
```
Moves status → SUBMITTED.

### ✅ List Requests
```bash
GET /api/requests?status=&userId=
```

### ✅ Get by ID
```bash
GET /api/requests/{id}
```

### ✅ Update Status
```bash
PATCH /api/requests/{id}/status
```

### 💵 Update Fees & Payment
```bash
PATCH /api/requests/{id}/fees-payment
```

### ❌ Delete Request
```bash
DELETE /api/requests/{id}
```

---
### 🧪 Testing Example
Create Draft:
```bash
curl -X POST http://localhost:8084/api/requests \
 -H "Content-Type: application/json" \
 -d '{
   "serviceId": "YOUR-SERVICE-ID",
   "userId": "portal-100",
   "egyid": "29811234567891",
   "investorId": "INV-ID",
   "companyId": "COMP-ID",
   "deliverablePreference": "DIGITAL"
 }'
```

---
### Access services
| Service          | Port |
| ---------------- | ---- |
| Investor Profile | 8082 |
| Company Profile  | 8081 |
| Service Catalog  | 8083 |
| Service Request  | 8084 |
