package io.miragon.miranum.connect.binder.adapter.scs.worker;

import io.miragon.miranum.connect.binder.adapter.scs.message.BpmnErrorEvent;
import io.miragon.miranum.connect.binder.adapter.scs.message.CorrelateMessageEvent;
import io.miragon.miranum.connect.binder.worker.application.port.in.ExecuteMethodUseCase;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Consumer;
import java.util.function.Supplier;

@EnableConfigurationProperties
public class SpringCloudStreamAutoConfiguration {

    @Bean
    public SpringCloudStreamAdapter springCloudStreamAdapter(
            final ExecuteMethodUseCase executeMethodUseCase,
            final Sinks.Many<Message<String>> incidentSink,
            final Sinks.Many<Message<BpmnErrorEvent>> bpmnErrorSink,
            final Sinks.Many<Message<CorrelateMessageEvent>> correlateMessageSink) {
        return new SpringCloudStreamAdapter(executeMethodUseCase, incidentSink, bpmnErrorSink, correlateMessageSink);
    }

    @Bean
    public Sinks.Many<Message<BpmnErrorEvent>> sendBpmnErrorSink() {
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    @Bean
    public Sinks.Many<Message<CorrelateMessageEvent>> correlateMessageSink() {
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    @Bean
    public Supplier<Flux<Message<BpmnErrorEvent>>> sendBpmnError(final Sinks.Many<Message<BpmnErrorEvent>> sink) {
        return sink::asFlux;
    }

    @Bean
    public Sinks.Many<Message<String>> sendIncidentSink() {
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    @Bean
    public Supplier<Flux<Message<String>>> sendIncident(final Sinks.Many<Message<String>> sink) {
        return sink::asFlux;
    }

    @Bean
    public Consumer<Message<?>> consumeEvents(final SpringCloudStreamAdapter adapter) {
        return adapter::execute;
    }

}
