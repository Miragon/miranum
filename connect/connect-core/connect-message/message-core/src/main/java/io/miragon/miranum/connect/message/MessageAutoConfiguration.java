package io.miragon.miranum.connect.message;


import io.miragon.miranum.connect.message.application.port.out.DeliverMessagePort;
import io.miragon.miranum.connect.message.application.service.CorrelateMessageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class MessageAutoConfiguration {

    @Bean
    public CorrelateMessageService correlateMessageService(DeliverMessagePort deliverMessagePort) {
        return new CorrelateMessageService(deliverMessagePort);
    }
}

