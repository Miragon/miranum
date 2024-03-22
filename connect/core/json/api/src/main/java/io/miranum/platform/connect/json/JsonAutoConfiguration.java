package io.miranum.platform.connect.json;


import io.miranum.platform.connect.json.api.JsonApi;
import io.miranum.platform.connect.json.impl.JsonApiImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public JsonApi miranumJsonApi() {
        return new JsonApiImpl();
    }


}

