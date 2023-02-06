package io.miragon.cmisadapter.configuration;

import io.miragon.cmisadapter.CmisAdapter;
import io.miragon.cmisadapter.client.CmisClient;
import io.miragon.cmisadapter.client.ICmisClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(CmisServerProperties.class)
public class CmisAdapterConfiguration {

    @Bean
    ICmisClient cmisClient(final CmisServerProperties cmisServerProperties) {
        return new CmisClient(cmisServerProperties);
    }

    @Bean
    CmisAdapter cmisAdapter(final CmisServerProperties cmisServerProperties) {
        return new CmisAdapter(this.cmisClient(cmisServerProperties));
    }
}
