package io.miragon.miraum.fitconnect.integration.destinationconnector.impl;

import io.miragon.miraum.fitconnect.integration.destinationconnector.SubscriberProperties;
import io.miragon.miraum.fitconnect.integration.destinationconnector.api.AuthorityApi;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;

@Service
@AllArgsConstructor
@EnableConfigurationProperties(SubscriberProperties.class)
public class DestinationConnectorScheduler {

    private final AuthorityApi authorityApi;

    @Scheduled(fixedRateString = "${fitconnect.subscriber.fixed-rate}")
    public void pollAndAcceptPickupReadySubmissions() throws ParseException, IOException {
        authorityApi.fetchAndAcceptPickupReadySubmissions();
    }
}
