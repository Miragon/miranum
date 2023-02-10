package io.miragon.miranum.connect.process;

import io.miragon.miranum.connect.process.application.port.out.StartProcessPort;
import io.miragon.miranum.connect.process.application.service.StartProcessService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcessAutoConfiguration {

    @Bean
    public StartProcessService startProcessService(StartProcessPort startProcessPort) {
        return new StartProcessService(startProcessPort);
    }
}
