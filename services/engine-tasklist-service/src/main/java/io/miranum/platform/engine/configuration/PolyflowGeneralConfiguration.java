package io.miranum.platform.engine.configuration;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.holunda.polyflow.bus.jackson.config.FallbackPayloadObjectMapperAutoConfiguration;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.axonframework.modelling.saga.repository.SagaStore;
import org.axonframework.modelling.saga.repository.inmemory.InMemorySagaStore;
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

    @Bean
    @Qualifier(FallbackPayloadObjectMapperAutoConfiguration.PAYLOAD_OBJECT_MAPPER)
    public ObjectMapper payloadObjectMapper() {
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

    /**
     * In Memory, so no event storage is available in this component.
     *
     * @return in-memory storage engine, to make Axon Framework happy.
     */
    @Bean
    public EventStorageEngine inMemoryStorageEngine() {
        return new InMemoryEventStorageEngine();
    }

    /**
     * No sagas should be handled in this component.
     *
     * @return in-memory saga-store to make Axon Framework happy.
     */
    @Bean
    public SagaStore<?> inMemorySagaStore() {
        return new InMemorySagaStore();
    }

}
