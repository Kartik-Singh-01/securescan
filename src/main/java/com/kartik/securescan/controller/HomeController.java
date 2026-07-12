package com.kartik.securescan.controller;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import java.util.Map;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kartik.securescan.analyzer.HeaderAnalyzer;
import com.kartik.securescan.dto.ScanResultDTO;
import com.kartik.securescan.entity.ScanHistory;
import com.kartik.securescan.scanner.WebsiteScanner;
import com.kartik.securescan.service.ScanHistoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(
        name = "SecureScan API",
        description = "Web Security Header & Vulnerability Analyzer"
)
public class HomeController {

    private static final Logger logger =
            LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private ScanHistoryService scanHistoryService;

    @Autowired
    private WebsiteScanner websiteScanner;

    @Autowired
    private HeaderAnalyzer headerAnalyzer;

    // Home API
    @Operation(summary = "Welcome API")
    @GetMapping("/")
    public String home() {

        logger.info("Home API accessed.");

        return "Welcome to SecureScan - Web Security Header & Vulnerability Analyzer";

    }

    // Save Scan
    @Operation(summary = "Save Website Scan")
    @PostMapping("/scan")
    public ScanHistory saveScan(
            @Valid
            @RequestBody ScanHistory scan) {

        logger.info("Received scan request for URL: {}", scan.getUrl());

        return scanHistoryService.saveScan(scan);

    }

    // Get HTTP Headers
    @Operation(summary = "Get HTTP Headers")
    @GetMapping("/headers")
    public Map<String, List<String>> getHeaders(
            @RequestParam String url) {

        logger.info("Fetching headers for {}", url);

        return websiteScanner.getHeaders(url);

    }

    // Analyze Security Headers
    @Operation(summary = "Analyze Security Headers")
    @GetMapping("/analyze")
    public List<String> analyze(
            @RequestParam String url) {

        logger.info("Analyzing security headers for {}", url);

        return headerAnalyzer.analyzeHeaders(
                websiteScanner.getHeaders(url));

    }

    // Generate Security Report
    @Operation(summary = "Generate Security Report")
    @GetMapping("/report")
    public ScanResultDTO generateReport(
            @RequestParam String url) {

        logger.info("Generating security report for {}", url);

        return scanHistoryService.generateReport(url);

    }

    // Get All Scan History
    @Operation(summary = "Get All Scan History")
    @GetMapping("/history")
    public List<ScanHistory> getAllScans() {

        logger.info("Fetching all scan history.");

        return scanHistoryService.getAllScans();

    }

    // Get Scan By ID
    @Operation(summary = "Get Scan By ID")
    @GetMapping("/history/{id}")
    public ScanHistory getScanById(
            @PathVariable Long id) {

        logger.info("Fetching scan with ID: {}", id);

        return scanHistoryService.getScanById(id);

    }

    // Delete Scan
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete Scan By ID")
    @DeleteMapping("/history/{id}")
    public String deleteScan(
            @PathVariable Long id) {

        logger.info("Deleting scan with ID: {}", id);

        return scanHistoryService.deleteScan(id);

    }

}