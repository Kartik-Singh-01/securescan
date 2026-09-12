// Generate Complete Security Report
public ScanResultDTO generateReport(String url) {

    logger.info("Generating security report for: {}", url);

    // Get target website status code first
    int statusCode = websiteScanner.getStatusCode(url);

    ScanResultDTO dto = new ScanResultDTO();

    dto.setUrl(url);
    dto.setStatusCode(statusCode);

    // Only calculate security score if the target was successfully scanned
    if (statusCode < 200 || statusCode >= 300) {

        logger.warn(
                "Scan unsuccessful for {}. Target returned HTTP status {}",
                url,
                statusCode);

        dto.setSecurityScore(0);
        dto.setMissingHeaders(List.of());
        dto.setRecommendations(List.of());

        return dto;
    }

    // Get HTTP headers
    Map<String, List<String>> headers =
            websiteScanner.getHeaders(url);

    // Find missing security headers
    List<String> missingHeaders =
            headerAnalyzer.analyzeHeaders(headers);

    // Calculate security score
    int score =
            headerAnalyzer.calculateSecurityScore(missingHeaders);

    // Generate recommendations
    List<String> recommendations =
            headerAnalyzer.generateRecommendations(missingHeaders);

    dto.setSecurityScore(score);
    dto.setMissingHeaders(missingHeaders);
    dto.setRecommendations(recommendations);

    logger.info(
            "Security report generated successfully for: {}",
            url);

    return dto;
}
