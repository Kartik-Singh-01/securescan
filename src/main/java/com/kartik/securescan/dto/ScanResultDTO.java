package com.kartik.securescan.dto;

import java.util.List;

public class ScanResultDTO {

    private String url;

    private int statusCode;

    private int securityScore;
    
    
    private List<String> missingHeaders;

    private List<String> recommendations;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getSecurityScore() {
        return securityScore;
    }

    public void setSecurityScore(int securityScore) {
        this.securityScore = securityScore;
    }

    public List<String> getMissingHeaders() {
        return missingHeaders;
    }

    public void setMissingHeaders(List<String> missingHeaders) {
        this.missingHeaders = missingHeaders;
    }

    public List<String> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<String> recommendations) {
        this.recommendations = recommendations;
    }
}