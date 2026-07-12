package com.kartik.securescan.analyzer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class HeaderAnalyzer {

    // Analyze Missing Security Headers
    public List<String> analyzeHeaders(Map<String, List<String>> headers) {

        List<String> missingHeaders = new ArrayList<>();

        if (!headers.containsKey("Content-Security-Policy")) {
            missingHeaders.add("Content-Security-Policy");
        }

        if (!headers.containsKey("Strict-Transport-Security")) {
            missingHeaders.add("Strict-Transport-Security");
        }

        if (!headers.containsKey("X-Frame-Options")) {
            missingHeaders.add("X-Frame-Options");
        }

        if (!headers.containsKey("X-Content-Type-Options")) {
            missingHeaders.add("X-Content-Type-Options");
        }

        if (!headers.containsKey("Referrer-Policy")) {
            missingHeaders.add("Referrer-Policy");
        }

        return missingHeaders;
    }

    // Calculate Security Score
    public int calculateSecurityScore(List<String> missingHeaders) {

        int score = 100;

        score -= missingHeaders.size() * 20;

        if (score < 0) {
            score = 0;
        }

        return score;
    }

    // Generate Security Recommendations
    public List<String> generateRecommendations(List<String> missingHeaders) {

        List<String> recommendations = new ArrayList<>();

        for (String header : missingHeaders) {

            switch (header) {

                case "Content-Security-Policy":
                    recommendations.add("Add Content-Security-Policy to protect against Cross-Site Scripting (XSS).");
                    break;

                case "Strict-Transport-Security":
                    recommendations.add("Enable HSTS to force HTTPS connections.");
                    break;

                case "X-Frame-Options":
                    recommendations.add("Add X-Frame-Options to prevent Clickjacking attacks.");
                    break;

                case "X-Content-Type-Options":
                    recommendations.add("Add X-Content-Type-Options with value 'nosniff'.");
                    break;

                case "Referrer-Policy":
                    recommendations.add("Configure Referrer-Policy to reduce information leakage.");
                    break;
            }
        }

        return recommendations;
    }

}