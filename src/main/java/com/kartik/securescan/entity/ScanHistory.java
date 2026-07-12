package com.kartik.securescan.entity;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "scan_history")
public class ScanHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "URL cannot be empty.")

    @Pattern(
        regexp = "^(http|https)://.*$",
        message = "URL must start with http:// or https://"
    )
    private String url;

    private Integer statusCode;

    private Integer securityScore;

    private LocalDateTime scanDate;

    public ScanHistory() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Integer getSecurityScore() {
        return securityScore;
    }

    public void setSecurityScore(Integer securityScore) {
        this.securityScore = securityScore;
    }

    public LocalDateTime getScanDate() {
        return scanDate;
    }

    public void setScanDate(LocalDateTime scanDate) {
        this.scanDate = scanDate;
    }
}