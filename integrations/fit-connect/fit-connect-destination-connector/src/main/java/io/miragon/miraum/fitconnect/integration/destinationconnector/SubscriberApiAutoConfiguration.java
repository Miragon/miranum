package io.miragon.miraum.fitconnect.integration.destinationconnector;

import io.miragon.miranum.connect.process.api.ProcessApi;
import io.miragon.miraum.fitconnect.integration.destinationconnector.api.AuthorityApi;
import io.miragon.miraum.fitconnect.integration.destinationconnector.impl.AuthorityApiImpl;
import io.miragon.miraum.fitconnect.integration.destinationconnector.impl.DestinationConnectorScheduler;
import io.miragon.miraum.fitconnect.integration.gen.api.EinreichungsempfangApi;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SubscriberProperties.class)
public class SubscriberApiAutoConfiguration {

    @Bean
    public AuthorityApi authorityApi(final EinreichungsempfangApi einreichungsempfangApi, final SubscriberProperties authorityProperties, final ProcessApi processApi) {
        return new AuthorityApiImpl(einreichungsempfangApi, authorityProperties, processApi);
    }

    @Bean
    public DestinationConnectorScheduler destinationConnectorScheduler(final AuthorityApi authorityApi) {
        return new DestinationConnectorScheduler(authorityApi);
    }
}
