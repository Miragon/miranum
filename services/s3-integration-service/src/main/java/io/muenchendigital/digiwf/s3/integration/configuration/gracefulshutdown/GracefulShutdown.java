/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2022
 */
package io.muenchendigital.digiwf.s3.integration.configuration.gracefulshutdown;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Connector;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * This class handles the graceful shutdown by serving all pending requests after
 * shutdown initialization via a SIGTERM signal.
 *
 * To use this functionality, the spring boot actuator endpoint "/actuator/health"
 * has to be activated within the application which uses this functionality.
 */
@Component
@ToString
@EqualsAndHashCode(callSuper = false)
@Slf4j
public class GracefulShutdown implements TomcatConnectorCustomizer, ApplicationListener<ContextClosedEvent> {

    private static final long MILLIS_PER_SECOND = 1000L;

    private volatile Connector connector;

    @Getter
    @Value("${refarch.gracefulshutdown.pre-wait-seconds:20}")
    private int preWaitSeconds;

    @Getter
    @Value("${refarch.gracefulshutdown.shutdown-wait-seconds:20}")
    private int shutdownWaitSeconds;

    /**
     * The health checker which is used to manipulate the health actuator endpoint.
     */
    private final GracefulShutdownHealthCheck healthCheck;

    public  GracefulShutdown(final GracefulShutdownHealthCheck healthCheck) {
        this.healthCheck = healthCheck;
    }

    /**
     * The tomcat connector.
     *
     * @param connector
     */
    @Override
    public void customize(final Connector connector) {
        this.connector = connector;
    }

    /**
     * The {@link ContextClosedEvent} which is used to handle the graceful shutdown.
     *
     * The shutdown is delayed till all pending requests are handled by the webserver.
     *
     * At the beginning of the shutdown process the actuator endpoint "/actuator/health"
     * is set from status UP to status DOWN.
     *
     * @param event The {@link ContextClosedEvent} to gracefully shutdown.
     */
    @Override
    public void onApplicationEvent(@NotNull final ContextClosedEvent event) {
        log.info("context close event happened");

        healthCheck.setStatusDown();

        try {
            Thread.sleep(preWaitSeconds * MILLIS_PER_SECOND);

            this.connector.pause();
            final Executor executor = this.connector.getProtocolHandler().getExecutor();
            if (executor instanceof ThreadPoolExecutor) {

                ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executor;
                threadPoolExecutor.shutdown();
                if (!threadPoolExecutor.awaitTermination(shutdownWaitSeconds, TimeUnit.SECONDS)) {
                    log.warn("Tomcat thread pool did not shut down gracefully within {} seconds. Proceeding with forceful shutdown", shutdownWaitSeconds);
                    threadPoolExecutor.shutdownNow();
                } else {
                    log.warn("The application gracefully shuts down now. All pending requests were served.");
                }
            }
        } catch (InterruptedException ex) {
            log.warn("InterruptedException during shutdown happened");
            Thread.currentThread().interrupt();
        }

    }

}

