package com.example.backend.component;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class DownstreamServiceHealthIndicator implements HealthIndicator {

    private final RestTemplate restTemplate;
    private final String downstreamServiceUrl = "https://api.downstream-service.com/health";

    public DownstreamServiceHealthIndicator(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Health health() {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(downstreamServiceUrl, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return Health.up().withDetail("downstream-service", "Service is healthy").build();
            } else {
                return Health.down().withDetail("downstream-service", "Service returned status: " + response.getStatusCode()).build();
            }
        } catch (Exception e) {
            return Health.down(e).withDetail("downstream-service", "Service is unavailable: " + e.getMessage()).build();
        }
    }
}