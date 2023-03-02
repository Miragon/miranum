package io.miragon.miranum.examples;

import com.nimbusds.jose.JOSEException;
import io.camunda.zeebe.spring.client.EnableZeebeClient;
import io.miragon.miraum.fitconnect.integration.authority.api.AuthorityApi;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.text.ParseException;

@SpringBootApplication
@EnableZeebeClient
@AllArgsConstructor
@EnableScheduling
public class FitConnectExampleC8Application {

    private final AuthorityApi authorityApi;

    @Scheduled(fixedRate = 10_000)
    void pollForPickupReadySubmissions() throws ParseException, IOException, JOSEException {
        authorityApi.pollAndAcceptPickupReadySubmissions();
    }

    public static void main(String[] args) {
        SpringApplication.run(FitConnectExampleC8Application.class, args);
    }
}