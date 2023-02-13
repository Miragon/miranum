package io.miragon.miranum.connect.message;


import io.miragon.miranum.connect.message.impl.DeliverMessagePort;
import io.miragon.miranum.connect.message.impl.MessageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageAutoConfiguration {

    @Bean
    public MessageService miranumMessageService(final DeliverMessagePort deliverMessagePort) {
        return new MessageService(deliverMessagePort);
    }
}

