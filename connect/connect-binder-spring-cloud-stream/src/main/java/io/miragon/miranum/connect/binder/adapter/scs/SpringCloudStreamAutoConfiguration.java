package io.miragon.miranum.connect.binder.adapter.scs;

import io.miragon.miranum.connect.binder.adapter.scs.message.SpringCloudStreamMessageAdapter;
import io.miragon.miranum.connect.binder.adapter.scs.worker.SpringCloudStreamWorkerAdapter;
import io.miragon.miranum.connect.binder.message.application.port.in.CorrelateMessageCommand;
import io.miragon.miranum.connect.binder.message.application.port.out.DeliverMessagePort;
import io.miragon.miranum.connect.binder.worker.application.port.in.ExecuteMethodUseCase;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import reactor.core.publisher.Sinks;

import java.util.function.Consumer;

@EnableConfigurationProperties
public class SpringCloudStreamAutoConfiguration {

    @Bean
    public DeliverMessagePort deliverMessagePort() {
        return new SpringCloudStreamMessageAdapter();
    }

    @Bean
    public SpringCloudStreamWorkerAdapter springCloudStreamAdapter(
            final ExecuteMethodUseCase executeMethodUseCase,
            final DeliverMessagePort deliverMessagePort) {
        return new SpringCloudStreamWorkerAdapter(executeMethodUseCase, deliverMessagePort);
    }

    @Bean
    public Sinks.Many<Message<CorrelateMessageCommand>> correlateMessageSink() {
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    @Bean
    public Consumer<Message<?>> consumeWorkerEvents(final SpringCloudStreamWorkerAdapter adapter) {
        return adapter::execute;
    }

}
