package com.example.backend.component;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class DatabaseHealthIndicator implements HealthIndicator {

    private final DataSource dataSource;

    public DatabaseHealthIndicator(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Health health() {
        try (Connection connection = dataSource.getConnection()) {
            if (connection.isValid(1)) { // Check if the connection is valid
                return Health.up().withDetail("database", "Connection is healthy").build();
            } else {
                return Health.down().withDetail("database", "Connection is invalid").build();
            }
        } catch (SQLException e) {
            return Health.down(e).withDetail("database", "Connection failed: " + e.getMessage()).build();
        }
    }
}