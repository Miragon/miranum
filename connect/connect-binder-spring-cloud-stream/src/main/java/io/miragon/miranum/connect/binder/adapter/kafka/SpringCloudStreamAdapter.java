package io.miragon.miranum.connect.binder.adapter.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.miragon.miranum.connect.binder.application.port.in.ExecuteMethodCommand;
import io.miragon.miranum.connect.binder.application.port.in.ExecuteMethodUseCase;
import io.miragon.miranum.connect.binder.application.port.out.BindWorkerPort;
import io.miragon.miranum.connect.binder.domain.BusinessException;
import io.miragon.miranum.connect.binder.domain.TechnicalException;
import io.miragon.miranum.connect.binder.domain.WorkerInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.function.context.MessageRoutingCallback;
import org.springframework.messaging.Message;
import reactor.core.publisher.Sinks;

import java.util.HashMap;
import java.util.Map;

@Slf4j
class SpringCloudStreamAdapter implements BindWorkerPort, MessageRoutingCallback {

    private final ExecuteMethodUseCase executeMethodUseCase;

    private final Sinks.Many<Message<BpmnErrorEvent>> bpmnErrorSink;

    private final Sinks.Many<Message<String>> incidentSink;

    private final Sinks.Many<Message<CorrelateMessageEvent>> correlateMessageSink;

    private final Map<String, WorkerInfo> useCases = new HashMap<>();

    private final ObjectMapper objectMapper = new ObjectMapper();

    public SpringCloudStreamAdapter(
            final ExecuteMethodUseCase executeMethodUseCase,
            final Sinks.Many<Message<String>> incidentSink,
            final Sinks.Many<Message<BpmnErrorEvent>> bpmnErrorSink,
            final Sinks.Many<Message<CorrelateMessageEvent>> correlateMessageSink) {
        this.executeMethodUseCase = executeMethodUseCase;
        this.incidentSink = incidentSink;
        this.bpmnErrorSink = bpmnErrorSink;
        this.correlateMessageSink = correlateMessageSink;
    }

    @Override
    public void bind(final WorkerInfo workerInfo) {
        this.useCases.put(workerInfo.getType(), workerInfo);
    }


    public void execute(final Message<?> message) {
        try {

            final String type = (String) message.getHeaders().get("type");
            final WorkerInfo useCase = this.useCases.get(type);
            final Object data = this.objectMapper.convertValue(message.getPayload(), this.useCases.get(type).getInputType());
            final Object result = this.executeMethodUseCase.execute(new ExecuteMethodCommand(data, useCase));

        } catch (final BusinessException exception) {
            log.error("business error detected", exception);
        } catch (final TechnicalException exception) {
            log.error("technical error detected", exception);
        } catch (final Exception exception) {
            log.error("general exception detected", exception);
        }

    }

}
