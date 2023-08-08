/*
package code.medconnect.integration.configuration;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration
public class TestContainerConfiguration {
    private static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER = new PostgreSQLContainer<>("postgres:15.0");

    static {
        POSTGRESQL_CONTAINER.start();
    }

    @Bean
    public DataSourceProperties dataSourceProperties() {
        DataSourceProperties properties = new DataSourceProperties();
        properties.setUrl(POSTGRESQL_CONTAINER.getJdbcUrl());
        properties.setUsername(POSTGRESQL_CONTAINER.getUsername());
        properties.setPassword(POSTGRESQL_CONTAINER.getPassword());
        properties.setDriverClassName(POSTGRESQL_CONTAINER.getDriverClassName());
        return properties;
    }

    @Bean
    public DriverManagerDataSource dataSource(DataSourceProperties properties) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(properties.getDriverClassName());
        dataSource.setUrl(properties.getUrl());
        dataSource.setUsername(properties.getUsername());
        dataSource.setPassword(properties.getPassword());
        return dataSource;
    }
}
*/
