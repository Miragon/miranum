package io.miranum.platform.engine.configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.kotlin.KotlinModule;
import io.holunda.polyflow.bus.jackson.ObjectMapperConfigurationHelper;

/**
 * Configuration for the object mapper.
 */
public class PolyflowObjectMapper {
    public static ObjectMapper DEFAULT =
            ObjectMapperConfigurationHelper
                    .configurePolyflowJacksonObjectMapper(
                            new ObjectMapper()
                                    .registerModule(new JavaTimeModule())
                                    .registerModule(new KotlinModule.Builder().build())
                                    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                                    .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                    );
}
