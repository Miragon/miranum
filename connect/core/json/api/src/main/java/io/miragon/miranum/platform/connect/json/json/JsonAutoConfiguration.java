package io.miragon.miranum.platform.connect.json.json;


import io.miragon.miranum.platform.connect.json.json.api.JsonApi;
import io.miragon.miranum.platform.connect.json.json.impl.JsonApiImpl;
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

