package ru.khehelk.cityroutes.adminservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import ru.khehelk.cityroutes.adminservice.app.AdminServiceApplication;
import ru.khehelk.cityroutes.adminservice.config.PostgresContainerInitializer;

@SpringBootTest(classes = AdminServiceApplication.class)
@ContextConfiguration(initializers = PostgresContainerInitializer.class)
class AdminServiceApplicationTests {

    @Test
    void contextLoads() {
    }

}
