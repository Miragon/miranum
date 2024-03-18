package io.miranum.platform.engine.configuration;

import io.holunda.polyflow.view.jpa.EnablePolyflowJpaView;
import org.axonframework.eventhandling.deadletter.jpa.DeadLetterEntry;
import org.axonframework.eventhandling.tokenstore.jpa.TokenEntry;
import org.axonframework.eventsourcing.eventstore.jpa.DomainEventEntry;
import org.axonframework.modelling.saga.repository.jpa.SagaEntry;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnablePolyflowJpaView
@EntityScan(basePackageClasses = {
        TokenEntry.class,
        DeadLetterEntry.class,
        SagaEntry.class,
        DomainEventEntry.class
})
public class PolyflowPersistenceConfiguration {


}
