package io.miragon.miranum.connect.binder.adapter.scs.worker;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.miragon.miranum.connect.binder.message.application.port.in.CorrelateMessageCommand;
import io.miragon.miranum.connect.binder.message.application.port.out.DeliverMessagePort;
import io.miragon.miranum.connect.binder.worker.application.port.in.ExecuteMethodCommand;
import io.miragon.miranum.connect.binder.worker.application.port.in.ExecuteMethodUseCase;
import io.miragon.miranum.connect.binder.worker.application.port.out.BindWorkerPort;
import io.miragon.miranum.connect.binder.worker.domain.BusinessException;
import io.miragon.miranum.connect.binder.worker.domain.TechnicalException;
import io.miragon.miranum.connect.binder.worker.domain.WorkerInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class SpringCloudStreamWorkerAdapter implements BindWorkerPort {

    private final ExecuteMethodUseCase executeMethodUseCase;

    private final DeliverMessagePort deliverMessagePort;

    private final Map<String, WorkerInfo> useCases = new HashMap<>();

    private final ObjectMapper objectMapper = new ObjectMapper();

    public SpringCloudStreamWorkerAdapter(
            final ExecuteMethodUseCase executeMethodUseCase,
            final DeliverMessagePort deliverMessagePort) {
        this.executeMethodUseCase = executeMethodUseCase;
        this.deliverMessagePort = deliverMessagePort;
    }

    @Override
    public void bind(final WorkerInfo workerInfo) {
        this.useCases.put(workerInfo.getType(), workerInfo);
    }


    public void execute(final Message<?> message) {
        try {

            final String type = (String) message.getHeaders().get("type");
            final String messageName = (String) message.getHeaders().get("messageName");
            final String correlationKey = (String) message.getHeaders().get("correlationKey");

            final WorkerInfo useCase = this.useCases.get(type);
            final Object data = this.objectMapper.convertValue(message.getPayload(), this.useCases.get(type).getInputType());
            final Object result = this.executeMethodUseCase.execute(new ExecuteMethodCommand(data, useCase));

            Map<String, Object> resultMap = new HashMap<>();

            if (result != null) {
                resultMap = this.objectMapper.convertValue(result, new TypeReference<Map<String, Object>>() {
                });
            }

            final CorrelateMessageCommand command = CorrelateMessageCommand.builder()
                    .messageName(messageName)
                    .correlationKey(correlationKey)
                    .variables(resultMap)
                    .build();

            this.deliverMessagePort.deliverMessage(command);

        } catch (final BusinessException exception) {
            log.error("business error detected", exception);
        } catch (final TechnicalException exception) {
            log.error("technical error detected", exception);
        } catch (final Exception exception) {
            log.error("general exception detected", exception);
        }

    }

}
