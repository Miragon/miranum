package io.miragon.miraum.fitconnect.integration.authority;

import io.miragon.miranum.connect.process.api.ProcessApi;
import io.miragon.miraum.fitconnect.integration.gen.api.EinreichungsempfangApi;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(AuthorityProperties.class)
public class AuthorityApiAutoConfiguration {

    @Bean
    public AuthorityApi authorityApi(final EinreichungsempfangApi einreichungsempfangApi, final AuthorityProperties authorityProperties, final ProcessApi processApi) {
        return new AuthorityApi(einreichungsempfangApi, authorityProperties, processApi);
    }
}