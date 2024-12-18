package ru.khehelk.cityroutes.authservice.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = "ru.khehelk.cityroutes")
@EnableJpaRepositories(basePackages = "ru.khehelk.cityroutes")
@SpringBootApplication(scanBasePackages = "ru.khehelk.cityroutes")
public class AuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

}
