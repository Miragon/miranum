package io.miragon.miranum.examples.pizzaorder.kitchen.adapter.in.miranum;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.miragon.miranum.connect.message.api.CorrelateMessageCommand;
import io.miragon.miranum.connect.message.api.MessageApi;
import io.miragon.miranum.connect.worker.api.Worker;
import io.miragon.miranum.examples.pizzaorder.kitchen.application.port.in.PreparePizzaCommand;
import io.miragon.miranum.examples.pizzaorder.kitchen.application.port.in.PreparePizzaUseCase;
import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
public class MiranumKitchenAdapter {

    private static final String MESSAGE_NAME = "PizzaPrepared";
    private final PreparePizzaUseCase preparePizzaUseCase;
    private final MessageApi messageApi;

    @Worker(type = "preparePizza")
    public void preparePizza(PreparePizzaCommand preparePizzaCommand) {
        var future = CompletableFuture.runAsync(() -> preparePizzaUseCase.preparePizza(preparePizzaCommand));
        future.thenRun(() -> {
            var objectMapper = new ObjectMapper();
            Map<String, Object> variables = objectMapper.convertValue(preparePizzaCommand, new TypeReference<>() {
            });
            var correlateMessageCommand = new CorrelateMessageCommand(
                    MESSAGE_NAME,
                    preparePizzaCommand.getEmail(),
                    variables);
            messageApi.correlateMessage(correlateMessageCommand);
        });
    }
}