package com.kartik.securescan.scanner;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class WebsiteScanner {

    private static final Logger logger =
            LoggerFactory.getLogger(WebsiteScanner.class);

    // Get HTTP Status Code
    public int getStatusCode(String websiteUrl) {

        try {

            logger.info("Scanning website: {}", websiteUrl);

            URL url = new URL(websiteUrl);

            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            connection.setConnectTimeout(5000);

            connection.connect();

            int status = connection.getResponseCode();

            logger.info("Status Code for {} : {}", websiteUrl, status);

            return status;

        } catch (IOException e) {

            logger.error("Error while scanning website: {}", websiteUrl, e);

            return -1;

        }

    }

    // Get All HTTP Response Headers
    public Map<String, List<String>> getHeaders(String websiteUrl) {

        try {

            logger.info("Fetching HTTP headers for {}", websiteUrl);

            URL url = new URL(websiteUrl);

            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            connection.setConnectTimeout(5000);

            connection.connect();

            Map<String, List<String>> originalHeaders =
                    connection.getHeaderFields();

            Map<String, List<String>> headers =
                    new HashMap<>();

            for (Map.Entry<String, List<String>> entry : originalHeaders.entrySet()) {

                if (entry.getKey() != null) {

                    headers.put(entry.getKey(), entry.getValue());

                }

            }

            logger.info("Successfully fetched {} headers from {}",
                    headers.size(), websiteUrl);

            return headers;

        } catch (IOException e) {

            logger.error("Failed to fetch headers from {}", websiteUrl, e);

            return new HashMap<>();

        }

    }

}