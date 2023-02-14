package io.miragon.miranum.examples.pizzaorder.application.service;

import io.miragon.miranum.connect.message.api.CorrelateMessageCommand;
import io.miragon.miranum.connect.message.api.MessageApi;
import io.miragon.miranum.connect.worker.api.Worker;
import io.miragon.miranum.examples.pizzaorder.application.port.in.OrderCommand;
import io.miragon.miranum.examples.pizzaorder.application.port.in.PreparePizzaUseCase;
import io.miragon.miranum.examples.pizzaorder.domain.Cook;
import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.concurrent.Executors;

@AllArgsConstructor
public class PreparePizzaService implements PreparePizzaUseCase {

    private final MessageApi messageApi;

    @Override
    @Worker(type = "preparePizza")
    public void preparePizza(OrderCommand orderCommand) {
        final String CORRELATION_KEY = "pizzaReady";
        final String MESSAGE_NAME = "Message_WaitForPizza";
        var cook = new Cook();
        var future = Executors.newSingleThreadExecutor().submit(cook::makePizza);
        while (!future.isDone()) {/* wait until pizza done */}
        var correlateMessageCommand = new CorrelateMessageCommand(MESSAGE_NAME, CORRELATION_KEY, Map.of());
        messageApi.correlateMessage(correlateMessageCommand);
    }
}