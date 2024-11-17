package ru.khehelk.migrationservice.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MigrationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.exit(
            SpringApplication.run(MigrationServiceApplication.class, args)
        );
    }

}
