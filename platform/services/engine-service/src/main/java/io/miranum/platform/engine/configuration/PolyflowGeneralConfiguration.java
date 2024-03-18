package io.miranum.platform.engine.configuration;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class PolyflowGeneralConfiguration {

    @Bean
    @Primary
    public ObjectMapper primaryObjectMapper() {
        return PolyflowObjectMapper.DEFAULT;
    }


    /**
     * Provides an objectmapper for Axon message serialization.
     *
     * @return objectmapper.
     */
    @Bean("defaultAxonObjectMapper")
    @Qualifier("defaultAxonObjectMapper")
    public ObjectMapper defaultAxonObjectMapper() {
        return PolyflowObjectMapper.DEFAULT;
    }


}
