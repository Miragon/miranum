package io.miragon.miranum.connect;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudEventsMessageAutoConfiguration {

    @Bean
    public CloudEventsMessageHandler cloudEventsMessageHandler() {
        return new CloudEventsMessageHandler();
    }

}
