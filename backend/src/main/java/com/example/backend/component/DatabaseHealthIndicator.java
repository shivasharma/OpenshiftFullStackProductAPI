package com.example.backend.component;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
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
                // Retrieve database metadata
                DatabaseMetaData metaData = connection.getMetaData();

                // Get connection details
                String url = metaData.getURL();
                String userName = metaData.getUserName();
                String databaseProductName = metaData.getDatabaseProductName();
                String databaseProductVersion = metaData.getDatabaseProductVersion();

                // Build health details
                return Health.up()
                        .withDetail("database", "Connection is healthy")
                        .withDetail("url", url)
                        .withDetail("user", userName)
                        .withDetail("databaseProductName", databaseProductName)
                        .withDetail("databaseProductVersion", databaseProductVersion)
                        .build();
            } else {
                return Health.down().withDetail("database", "Connection is invalid").build();
            }
        } catch (SQLException e) {
            return Health.down(e)
                    .withDetail("database", "Connection failed: " + e.getMessage())
                    .build();
        }
    }
}