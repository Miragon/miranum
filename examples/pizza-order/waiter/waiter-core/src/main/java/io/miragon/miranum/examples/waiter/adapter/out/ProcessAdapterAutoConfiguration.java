package io.miragon.miranum.examples.waiter.adapter.out;

import io.miragon.miranum.connect.process.api.ProcessApi;
import io.miragon.miranum.examples.waiter.application.port.out.PlaceOrderPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcessAdapterAutoConfiguration {

    @Bean
    public PlaceOrderPort placeOrderPort(final ProcessApi processApi) {
        return new ProcessAdapter(processApi);
    }
}