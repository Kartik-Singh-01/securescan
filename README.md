# 🛡️ SecureScan

SecureScan is a Spring Boot-based Web Security Header & Vulnerability Analyzer that scans websites for HTTP security headers, identifies missing security headers, calculates a security score, and stores scan history in PostgreSQL.

---

## 🚀 Features

- Website Security Header Scanner
- HTTP Response Header Analysis
- Security Score Calculation
- Scan History Management
- JWT Authentication
- Role-Based Authorization (RBAC)
- Swagger API Documentation
- Docker Support
- Docker Compose Support
- Global Exception Handling
- Professional Logging (SLF4J)

---

## 🛠 Tech Stack

### Backend
- Java 21
- Spring Boot
- Spring Security
- Hibernate / JPA
- JWT
- Maven

### Database
- PostgreSQL

### API Testing
- Swagger
- Postman

### DevOps
- Docker
- Docker Compose
- Git
- GitHub

---

## 🏗 Project Architecture

```
              User
                │
                ▼
        Spring Boot REST API
                │
      ┌─────────┴─────────┐
      ▼                   ▼
Website Scanner      Authentication
      │                   │
      ▼                   ▼
Header Analyzer         JWT
      │
      ▼
Security Score Engine
      │
      ▼
 PostgreSQL Database
```

---

## 📂 Project Structure

```
src
├── controller
├── service
├── repository
├── entity
├── dto
├── security
├── analyzer
├── scanner
├── config
├── exception
└── util
```

---

## ⚙ Installation

### Clone Repository

```bash
git clone https://github.com/Kartik-Singh-01/securescan.git
```

```bash
cd securescan
```

### Build

```bash
./mvnw clean package
```

### Run

```bash
./mvnw spring-boot:run
```

---

## 🐳 Docker

Build Image

```bash
docker build -t securescan .
```

Run Container

```bash
docker run -p 8080:8080 securescan
```

Docker Compose

```bash
docker compose up
```

---

## 📖 API Documentation

Swagger UI

```
http://localhost:8080/swagger-ui/index.html
```

---

## 🔗 API Endpoints

| Method | Endpoint | Description |
|---------|----------|-------------|
| POST | /register | Register User |
| POST | /login | Login User |
| POST | /scan | Scan Website |
| GET | /history | Get Scan History |
| DELETE | /history/{id} | Delete Scan |
| GET | /report | Security Report |

---

## 🔐 Authentication

SecureScan uses JWT Authentication for securing REST APIs.

Features include:

- User Registration
- Login
- JWT Token Generation
- Token Validation
- Role-Based Authorization

---

## 🚀 Future Improvements

- React Frontend
- PDF Security Reports
- Email Notifications
- Scheduled Website Scans
- OWASP Integration
- Vulnerability Database
- Admin Dashboard

---

## 👨‍💻 Author

**Kartik Singh**

GitHub:

https://github.com/Kartik-Singh-01
