package io.miragon.miranum.platform.connect.process;

import io.miragon.miranum.platform.connect.process.impl.ProcessApiImpl;
import io.miragon.miranum.platform.connect.process.impl.StartProcessPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcessAutoConfiguration {

    @Bean
    public ProcessApiImpl miranumProcessApi(final StartProcessPort startProcessPort) {
        return new ProcessApiImpl(startProcessPort);
    }
}
