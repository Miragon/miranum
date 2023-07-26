/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2022
 */
package io.muenchendigital.digiwf.s3.integration.configuration.gracefulshutdown;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;


/**
 * The {@link HealthIndicator} which is used in class {@link GracefulShutdown}
 * to manipulate the health actuator endpoint during a shutdown.
 */
@Component
@ToString
@Slf4j
public class GracefulShutdownHealthCheck implements HealthIndicator {

    public static final String GRACEFULSHUTDOWN = "Gracefulshutdown";

    public static final String APPLICATION_UP_MESSAGE = "application up";

    public static final String APPLICATION_DOWN_MESSAGE = "gracefully shutting down";

    private Health health;

    public GracefulShutdownHealthCheck() {
        setStatusUp();
    }

    @Override
    public Health health() {
        return health;
    }

    /**
     * The method to set the actuator health endpoint to status {@link Status#UP}.
     */
    public void setStatusUp() {
        health = new Health.Builder().withDetail(GRACEFULSHUTDOWN, APPLICATION_UP_MESSAGE).up().build();
        log.info("Gracefulshutdown healthcheck UP");
    }

    /**
     * The method to set the actuator health endpoint to status {@link Status#DOWN}.
     */
    public void setStatusDown() {
        health = new Health.Builder().withDetail(GRACEFULSHUTDOWN, APPLICATION_DOWN_MESSAGE).down().build();
        log.info("Gracefulshutdown healthcheck DOWN");
    }

}
