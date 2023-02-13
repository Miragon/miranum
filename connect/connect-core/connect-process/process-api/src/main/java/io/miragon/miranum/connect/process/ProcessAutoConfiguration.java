package io.miragon.miranum.connect.process;

import io.miragon.miranum.connect.process.impl.ProcessService;
import io.miragon.miranum.connect.process.impl.StartProcessPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcessAutoConfiguration {

    @Bean
    public ProcessService miranumProcessService(final StartProcessPort startProcessPort) {
        return new ProcessService(startProcessPort);
    }
}
