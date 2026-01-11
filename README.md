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

## ▶️ How to Run the Platform (Local Development)

### 1️⃣ Prerequisites

Make sure you have the following installed:

* Java 21
* Maven 3.9+
* Docker
* Docker Compose

Verify:

```bash
java -version
mvn -version
docker -v
docker-compose -v
```

---

### 2️⃣ Start Databases (PostgreSQL – Docker)

All databases are containerized using **Docker Compose** (database-per-service).

From the project root (where `docker-compose.yml` exists):

```bash
docker-compose up --build
```

This will start the following containers:

| Service DB      | Container Name    | Host Port | DB Name     |
| --------------- | ----------------- | --------- | ----------- |
| Company         | postgres-company  | 5433      | company_db  |
| Investor        | postgres-investor | 5434      | investor_db |
| Catalog         | postgres-catalog  | 5435      | catalog_db  |
| Service Request | postgres-request  | 5436      | request_db  |

> 💡 Data is persisted using Docker volumes (`company_pgdata`, `investor_pgdata`, etc.)

To stop containers:

```bash
docker-compose down
```

---

### 3️⃣ Run Microservices (Spring Boot)

Each microservice runs independently.

> ⚠️ **Important:** Make sure Docker databases are running before starting services.

---

#### 🧩 Investor Profile Service

```bash
cd investor-profile-service
mvn clean spring-boot:run
```

Runs on:

```
http://localhost:8082
```

---

#### 🏢 Company Profile Service

```bash
cd company-profile-service
mvn clean spring-boot:run
```

Runs on:

```
http://localhost:8081
```

---

#### 📚 Service Catalog Service

```bash
cd service-catalog-service
mvn clean spring-boot:run
```

Runs on:

```
http://localhost:8083
```

---

#### 📝 Service Request Service (Core Orchestrator)

```bash
cd service-request-service
mvn clean spring-boot:run
```

Runs on:

```
http://localhost:8084
```

---

### 4️⃣ Access Services Summary

| Service          | Port |
| ---------------- | ---- |
| Investor Profile | 8082 |
| Company Profile  | 8081 |
| Service Catalog  | 8083 |
| Service Request  | 8084 |

---
## 🧪 Postman Collection

A ready-to-use Postman collection is included for all services.

* **Collection:** [postman/Microservices-POC.postman_collection](https://www.postman.com/ghaidaaeldsoky/workspace/companies-workspace/collection/20573921-b1e202b5-a57c-4557-aecb-ab6fb23fba65?action=share&creator=20573921)

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
## 🔐 Authentication & Keycloak Integration

All backend services in this project are secured using **Keycloak** and **JWT bearer tokens**.

### Keycloak Setup

- **Realm:** `master`
- **Issuer URI (OIDC):**  
  `http://keycloak:8180/realms/master`
- **Tested confidential clients (one per service):**
  - `company-profile-client`
  - `investor-profile-client`
  - `service-catalog-client`
  - `service-req-client`

Each of these clients is:

- Type: **OpenID Connect**
- **Confidential** (client authentication enabled)
- **Direct Access Grants** enabled (for testing with Postman using username/password)
- (Optional) **Service accounts** can be enabled later for machine-to-machine calls.

> 💡 From inside the Docker network, services talk to Keycloak via `http://keycloak:8180`.  
> From your host (Postman / browser), you typically reach it via `http://localhost:8180` (depending on your `docker-compose` ports).

---

### 🧪 How to Test Authentication with Postman
#### 1️⃣ Get an Access Token from Keycloak

Use any of the tested clients, e.g. service-req-client or company-profile-client.
You must also have a user created in the gov realm.
#### Request

- **Method:** `POST`
- **URL (from host):**
```bash
http://localhost:8180/realms/master/protocol/openid-connect/token
```
(If you call from inside the Docker network, use `http://keycloak:8180/...` instead)

**Body:** `x-www-form-urlencoded`:

| Key           | Value                    |
| ------------- | ------------------------ |
| grant_type    | password                 |
| client_id     | jbpm-client              |
| client_secret | -client secret-          |
| username      | -keycloak username-      |
| password      | -keycloak user password- |

#### Response (example)
```bash
{
  "access_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9....",
  "expires_in": 300,
  "refresh_token": "....",
  "token_type": "Bearer",
  ...
}
```
Copy the value of `access_token`.

---
#### 2️⃣ Call Any Secured Service Endpoint
#### Example: company-profile test endpoint:
- **Method:** `GET`
- **URL:**
```bash
http://localhost:8081/api/companies
```
(Adjust port/path according to the service)

- **Headers:**

| Header        | Value                            |
| ------------- | -------------------------------- |
| Authorization | Bearer -paste-access-token-here- |

- If you remove the Authorization header, you should get: `401 Unauthorized`
