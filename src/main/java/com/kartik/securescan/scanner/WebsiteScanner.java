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

    private static final int CONNECT_TIMEOUT = 5000;
    private static final int READ_TIMEOUT = 10000;

    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) "
            + "AppleWebKit/537.36 (KHTML, like Gecko) "
            + "Chrome/131.0.0.0 Safari/537.36";

    // Create HTTP connection
    private HttpURLConnection createConnection(String websiteUrl)
            throws IOException {

        URL url = new URL(websiteUrl);

        HttpURLConnection connection =
                (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");

        connection.setConnectTimeout(CONNECT_TIMEOUT);
        connection.setReadTimeout(READ_TIMEOUT);

        // Make the request look like a normal browser request
        connection.setRequestProperty("User-Agent", USER_AGENT);
        connection.setRequestProperty(
                "Accept",
                "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        connection.setRequestProperty(
                "Accept-Language",
                "en-US,en;q=0.9");

        // Automatically follow redirects
        connection.setInstanceFollowRedirects(true);

        return connection;
    }

    // Get HTTP Status Code
    public int getStatusCode(String websiteUrl) {

        HttpURLConnection connection = null;

        try {

            logger.info("Scanning website: {}", websiteUrl);

            connection = createConnection(websiteUrl);

            connection.connect();

            int status = connection.getResponseCode();

            logger.info(
                    "Status Code for {} : {}",
                    websiteUrl,
                    status);

            return status;

        } catch (IOException e) {

            logger.error(
                    "Error while scanning website: {}",
                    websiteUrl,
                    e);

            return -1;

        } finally {

            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    // Get All HTTP Response Headers
    public Map<String, List<String>> getHeaders(String websiteUrl) {

        HttpURLConnection connection = null;

        try {

            logger.info(
                    "Fetching HTTP headers for {}",
                    websiteUrl);

            connection = createConnection(websiteUrl);

            connection.connect();

            Map<String, List<String>> originalHeaders =
                    connection.getHeaderFields();

            Map<String, List<String>> headers =
                    new HashMap<>();

            if (originalHeaders != null) {

                for (Map.Entry<String, List<String>> entry :
                        originalHeaders.entrySet()) {

                    if (entry.getKey() != null) {

                        headers.put(
                                entry.getKey(),
                                entry.getValue());
                    }
                }
            }

            logger.info(
                    "Successfully fetched {} headers from {}",
                    headers.size(),
                    websiteUrl);

            return headers;

        } catch (IOException e) {

            logger.error(
                    "Failed to fetch headers from {}",
                    websiteUrl,
                    e);

            return new HashMap<>();

        } finally {

            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
