package io.miragon.miranum.connect.message;


import io.miragon.miranum.connect.message.impl.CorrelateMessageService;
import io.miragon.miranum.connect.message.impl.DeliverMessagePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageAutoConfiguration {

    @Bean
    public CorrelateMessageService correlateMessageService(final DeliverMessagePort deliverMessagePort) {
        return new CorrelateMessageService(deliverMessagePort);
    }
}

