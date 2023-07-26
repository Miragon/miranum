/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2022
 */
package io.muenchendigital.digiwf.s3.integration.configuration.gracefulshutdown;

import io.muenchendigital.digiwf.s3.integration.S3IntegrationApplication;
import io.muenchendigital.digiwf.s3.integration.TestConstants;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(
        classes = { S3IntegrationApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"spring.datasource.url=jdbc:h2:mem:digitalwf;DB_CLOSE_ON_EXIT=FALSE",
                "refarch.gracefulshutdown.pre-wait-seconds=0"})
@ActiveProfiles(profiles = {TestConstants.SPRING_TEST_PROFILE, TestConstants.SPRING_NO_SECURITY_PROFILE})
@Disabled("while S3-integration-starter always makes connection test to S3")
public class GracefulShutdownHealthCheckTest {

    @Autowired
    private GracefulShutdownHealthCheck gracefulShutdownHealthCheck;

    private final Map<String, Object> expectedDetailMap = new LinkedHashMap<>();

    @Test
    public void health() {
        assertEquals(Status.UP, gracefulShutdownHealthCheck.health().getStatus());
        expectedDetailMap.put(GracefulShutdownHealthCheck.GRACEFULSHUTDOWN, GracefulShutdownHealthCheck.APPLICATION_UP_MESSAGE);
        assertEquals(expectedDetailMap, gracefulShutdownHealthCheck.health().getDetails());
        expectedDetailMap.clear();
    }

    @Test
    public void setReady() {
        gracefulShutdownHealthCheck.setStatusDown();
        assertEquals(Status.DOWN, gracefulShutdownHealthCheck.health().getStatus());
        expectedDetailMap.put(GracefulShutdownHealthCheck.GRACEFULSHUTDOWN, GracefulShutdownHealthCheck.APPLICATION_DOWN_MESSAGE);
        assertEquals(expectedDetailMap, gracefulShutdownHealthCheck.health().getDetails());
        expectedDetailMap.clear();

        gracefulShutdownHealthCheck.setStatusUp();
        assertEquals(Status.UP, gracefulShutdownHealthCheck.health().getStatus());
        expectedDetailMap.put(GracefulShutdownHealthCheck.GRACEFULSHUTDOWN, GracefulShutdownHealthCheck.APPLICATION_UP_MESSAGE);
        assertEquals(expectedDetailMap, gracefulShutdownHealthCheck.health().getDetails());
        expectedDetailMap.clear();
    }

}
