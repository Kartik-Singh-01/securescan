SecureScan – Web Security Header Analyzer

SecureScan is a Spring Boot REST API that analyzes a website's HTTP response headers and provides a security score, missing-header information, and remediation recommendations.

The application communicates with the submitted website through an HTTP GET request. It does not copy website source code, access the target website's database, perform penetration testing, or inspect SSL certificates in detail.

Features

Scan a website URL and retrieve its HTTP response status and headers

Analyze five commonly recommended security headers:

Content-Security-Policy

Strict-Transport-Security

X-Frame-Options

X-Content-Type-Options

Referrer-Policy

Calculate a security score based on the configured scoring rules

Identify missing security headers

Generate remediation recommendations

Store scan history in PostgreSQL

JWT-based stateless authentication

BCrypt password hashing

Role-based authorization

Bean Validation for incoming requests

Centralized exception handling

SLF4J-based application logging

Swagger/OpenAPI documentation

Docker support

Technology Stack

Java 21

Spring Boot

Spring Web

Spring Security

JWT

BCrypt

Spring Data JPA

Hibernate

PostgreSQL

Bean Validation

SLF4J

Swagger/OpenAPI

Docker

HttpURLConnection

Architecture

Client
  |
  v
Controller
  |
  v
Service
  |
  +--> WebsiteScanner --> Target Website
  |
  +--> HeaderAnalyzer
  |
  +--> Score and Recommendation Logic
  |
  v
Repository
  |
  v
PostgreSQL

The main responsibilities are separated as follows:

Controller: Receives HTTP requests and returns HTTP responses.

Service: Coordinates validation, scanning, analysis, scoring, and persistence.

WebsiteScanner: Opens the HTTP connection and retrieves the target response.

HeaderAnalyzer: Checks the returned security headers.

Repository: Persists and retrieves scan history.

Entity: Represents database records.

DTO: Defines the API request and response structure.

Security configuration: Protects endpoints and validates JWTs.

Global exception handler: Converts exceptions into consistent API error responses.

Scan Workflow

The authenticated client submits a website URL.

The controller receives the request body.

Bean Validation checks that the input is valid.

The service sends the URL to WebsiteScanner.

WebsiteScanner creates a URL object and opens an HttpURLConnection.

The scanner sends a GET request to the target website.

The target website returns an HTTP response.

SecureScan reads the response status code and headers.

HeaderAnalyzer checks whether the required security headers are present.

The score and recommendations are generated.

The scan result is stored as scan history in PostgreSQL.

A response DTO is returned to the client.

Example Request

The exact endpoint name may vary according to the controller implementation. A typical scan request is:

POST /scan
Content-Type: application/json
Authorization: Bearer <jwt-token>

{
  "url": "https://example.com"
}

Example Response

{
  "url": "https://example.com",
  "statusCode": 200,
  "securityScore": 80,
  "missingHeaders": [
    "Content-Security-Policy"
  ],
  "recommendations": [
    "Add a Content-Security-Policy header."
  ]
}

The response fields depend on the implemented DTOs.

Security Headers

Content-Security-Policy

Controls which sources the browser is allowed to load and helps reduce cross-site scripting risks.

Strict-Transport-Security

Instructs browsers to use HTTPS for future requests and helps reduce SSL-stripping risks.

X-Frame-Options

Controls whether a page can be embedded in a frame and helps reduce clickjacking risks.

X-Content-Type-Options

With the value nosniff, it prevents browsers from incorrectly guessing a resource's content type.

Referrer-Policy

Controls how much referrer information is shared with other websites.

The current analyzer focuses on header presence unless deeper value validation has been implemented separately.

Authentication and Authorization

SecureScan uses stateless JWT authentication.

The user registers or logs in.

The password is verified using BCrypt.

A signed JWT is generated after successful authentication.

The client sends the token in the Authorization header.

Spring Security validates the token for protected requests.

The user's roles determine whether the request is allowed.

Passwords are never stored in plain text. BCrypt hashes are stored instead, and PasswordEncoder.matches() verifies login credentials.

Authentication answers “Who are you?” Authorization answers “What are you allowed to do?”

Database Persistence

Scan history is represented by a JPA entity and stored in PostgreSQL.

Entity object
    |
    v
Spring Data Repository
    |
    v
Hibernate
    |
    v
SQL INSERT/SELECT
    |
    v
PostgreSQL

Spring Data JPA reduces boilerplate database code, while Hibernate performs the object-relational mapping and generates SQL.

Validation and Error Handling

Bean Validation is applied to request DTOs using annotations such as @NotBlank, @NotNull, and URL-related validation rules.

Centralized exception handling uses @ControllerAdvice and @ExceptionHandler to return consistent error responses for:

Invalid input

Invalid URLs

Network failures

Timeouts

Authentication failures

Authorization failures

Database errors

Sensitive implementation details and stack traces should not be returned to clients.

Logging

SLF4J is used for application logging instead of System.out.println().

Useful events include:

Scan request received

Scan completed

Target response status

Timeout or network failure

Authentication failures

Database failures

Passwords, JWTs, and other sensitive information must never be logged.

Swagger/OpenAPI

Swagger/OpenAPI documents the REST API and provides an interactive way to test endpoints. It can document:

Request bodies

Response DTOs

Validation errors

Authentication requirements

JWT Bearer authentication

HTTP response codes

The Swagger UI URL depends on the configured OpenAPI library and application settings.

Docker

Docker packages the application and its runtime dependencies into reproducible environments.

A typical setup contains:

A Spring Boot application container

A PostgreSQL database container

A shared Docker network

A persistent database volume

Environment variables for database and JWT configuration

Secrets and passwords should be provided through environment variables or a secret-management system rather than hardcoded in the image.

Running Locally

Prerequisites:

Java 21

Maven or Gradle

PostgreSQL

Docker, if using containers

Configure the database and security properties in the application's configuration files or environment variables. Then run the application using the project's build tool.

Typical Maven command:

mvn spring-boot:run

Typical package command:

mvn clean package

Use the exact commands defined by the project repository.

Limitations

SecureScan currently focuses on foundational HTTP security-header analysis. It does not necessarily:

Perform penetration testing

Crawl multiple pages

Execute JavaScript

Analyze dynamic browser behavior

Inspect SSL certificates in detail

Validate every security-header directive

Provide a complete vulnerability assessment

For production use, URL validation should also include SSRF protection, private-network blocking, safe redirect handling, DNS-rebinding protection, and strict connection/read timeouts.

Future Improvements

Header-value and directive validation

Weighted scoring and severity levels

Safe redirect-chain analysis

Detailed SSL/TLS inspection

Asynchronous scanning

Retry and rate-limiting strategies

Pagination and database indexes for scan history

Audit logging

Refresh tokens and MFA

Monitoring and CI/CD integration

Horizontal scaling for high scan volume
