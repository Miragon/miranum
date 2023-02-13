package io.miragon.miranum.examples.pizzaorder.application.service;

import io.miragon.miranum.connect.message.application.port.in.CorrelateMessageCommand;
import io.miragon.miranum.connect.message.application.port.in.CorrelateMessageUseCase;
import io.miragon.miranum.connect.worker.domain.Worker;
import io.miragon.miranum.examples.pizzaorder.application.port.in.OrderCommand;
import io.miragon.miranum.examples.pizzaorder.application.port.in.PreparePizzaUseCase;
import io.miragon.miranum.examples.pizzaorder.domain.Cook;
import lombok.AllArgsConstructor;

import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
public class PreparePizzaService implements PreparePizzaUseCase {

    private final CorrelateMessageUseCase correlateMessageUseCase;

    @Override
    @Worker(type = "preparePizza")
    public OrderCommand preparePizza(OrderCommand orderCommand) {
        final String CORRELATION_KEY = "pizzaPrepared";
        final String MESSAGE_NAME = "Message_WaitForPizza";
        var cook = new Cook();
        CompletableFuture
                .runAsync(cook::makePizza)
                .thenRun(() -> {
                    var correlateMessageCommand = new CorrelateMessageCommand();
                    correlateMessageCommand.setCorrelationKey(CORRELATION_KEY);
                    correlateMessageCommand.setMessageName(MESSAGE_NAME);
                    correlateMessageUseCase.correlateMessage(new CorrelateMessageCommand());
                });
        return orderCommand;
    }
}