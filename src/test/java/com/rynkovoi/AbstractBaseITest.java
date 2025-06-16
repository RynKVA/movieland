package com.rynkovoi;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.spring.api.DBRider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
@DBRider
@SpringBootTest
@Testcontainers
@DBUnit(
        cacheConnection = false,
        columnSensing = true,
        caseInsensitiveStrategy = Orthography.LOWERCASE
)
public class AbstractBaseITest {

    @Autowired
    protected MockMvc mockMvc;

    @ServiceConnection
    private static final PostgreSQLContainer<?> POSTGRES_SQL_CONTAINER = new PostgreSQLContainer<>("postgres:15");

    static {
        System.out.println("static block");
        POSTGRES_SQL_CONTAINER.start();
    }

    protected String getResponseAsString(String jsonPath) {
        URL resource = getClass().getClassLoader().getResource(jsonPath);
        try {
            return FileUtils.readFileToString(new File(resource.toURI()), StandardCharsets.UTF_8);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException("Unable to find file: " + jsonPath, e);
        }
    }


}

