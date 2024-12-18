package ru.khehelk.cityroutes.directoryservice.config;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;

public class PostgresContainerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:15.3");
        container.start();

        TestPropertyValues.of(
            "spring.datasource.url=" + container.getJdbcUrl(),
            "spring.datasource.username=" + container.getUsername(),
            "spring.datasource.password=" + container.getPassword(),
            "spring.jpa.hibernate.ddl-auto=update"
        ).applyTo(applicationContext.getEnvironment());
    }

}