package io.miragon.miranum.connect;

import io.miragon.miranum.connect.json.impl.JsonSchemaFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.nio.charset.Charset;

@Configuration
public class MessageCloudEventsAutoConfiguration {

    @Bean
    public MessageCloudEventHandler cloudEventsMessageHandler() {
        return new MessageCloudEventHandler();
    }
}
