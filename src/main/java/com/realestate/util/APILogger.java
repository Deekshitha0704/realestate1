package com.realestate.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class APILogger {

    /**
     * Log API request
     */
    public void logRequest(String method, String endpoint, Object payload) {
        log.info("=== API REQUEST ===");
        log.info("Method: {}", method);
        log.info("Endpoint: {}", endpoint);
        if (payload != null) {
            log.info("Payload: {}", payload);
        }
    }

    /**
     * Log API response
     */
    public void logResponse(String endpoint, int statusCode, Object response) {
        log.info("=== API RESPONSE ===");
        log.info("Endpoint: {}", endpoint);
        log.info("Status Code: {}", statusCode);
        log.info("Response: {}", response);
    }

    /**
     * Log error
     */
    public void logError(String endpoint, Exception ex) {
        log.error("=== API ERROR ===");
        log.error("Endpoint: {}", endpoint);
        log.error("Error: {}", ex.getMessage(), ex);
    }

    /**
     * Log performance metrics
     */
    public void logPerformance(String endpoint, long executionTime) {
        log.info("=== API PERFORMANCE ===");
        log.info("Endpoint: {}", endpoint);
        log.info("Execution Time: {}ms", executionTime);
    }
}
