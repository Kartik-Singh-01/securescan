package com.kartik.securescan.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kartik.securescan.analyzer.HeaderAnalyzer;
import com.kartik.securescan.dto.ScanResultDTO;
import com.kartik.securescan.entity.ScanHistory;
import com.kartik.securescan.exception.ScanNotFoundException;
import com.kartik.securescan.repository.ScanHistoryRepository;
import com.kartik.securescan.scanner.WebsiteScanner;

@Service
public class ScanHistoryService {

    private static final Logger logger =
            LoggerFactory.getLogger(ScanHistoryService.class);

    @Autowired
    private ScanHistoryRepository repository;

    @Autowired
    private WebsiteScanner websiteScanner;

    @Autowired
    private HeaderAnalyzer headerAnalyzer;

    // Save Scan
    public ScanHistory saveScan(ScanHistory scan) {

        logger.info("Saving scan for URL: {}", scan.getUrl());

        scan.setStatusCode(
                websiteScanner.getStatusCode(scan.getUrl()));

        scan.setSecurityScore(0);

        scan.setScanDate(LocalDateTime.now());

        ScanHistory savedScan = repository.save(scan);

        logger.info("Scan saved successfully with ID: {}", savedScan.getId());

        return savedScan;

    }

    // Get All Scans
    public List<ScanHistory> getAllScans() {

        logger.info("Fetching all scan history.");

        return repository.findAll();

    }

    // Get Scan By ID
    public ScanHistory getScanById(Long id) {

        logger.info("Fetching scan with ID: {}", id);

        return repository.findById(id)
                .orElseThrow(() ->
                        new ScanNotFoundException(
                                "No scan exists with ID " + id));

    }

    // Delete Scan
    public String deleteScan(Long id) {

        logger.info("Deleting scan with ID: {}", id);

        ScanHistory scan = repository.findById(id)
                .orElseThrow(() ->
                        new ScanNotFoundException(
                                "No scan exists with ID " + id));

        repository.delete(scan);

        logger.info("Scan deleted successfully.");

        return "Scan deleted successfully.";

    }

    // Generate Complete Security Report
    public ScanResultDTO generateReport(String url) {

        logger.info("Generating security report for: {}", url);

        Map<String, List<String>> headers =
                websiteScanner.getHeaders(url);

        List<String> missingHeaders =
                headerAnalyzer.analyzeHeaders(headers);

        int score =
                headerAnalyzer.calculateSecurityScore(missingHeaders);

        List<String> recommendations =
                headerAnalyzer.generateRecommendations(missingHeaders);

        ScanResultDTO dto = new ScanResultDTO();

        dto.setUrl(url);

        dto.setStatusCode(
                websiteScanner.getStatusCode(url));

        dto.setSecurityScore(score);

        dto.setMissingHeaders(missingHeaders);

        dto.setRecommendations(recommendations);

        logger.info("Security report generated successfully for: {}", url);

        return dto;

    }

}