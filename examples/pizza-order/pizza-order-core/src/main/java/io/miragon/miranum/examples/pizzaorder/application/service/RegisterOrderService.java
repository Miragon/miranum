package io.miragon.miranum.examples.pizzaorder.application.service;

import io.miragon.miranum.connect.worker.api.Worker;
import io.miragon.miranum.examples.pizzaorder.application.port.in.OrderCommand;
import io.miragon.miranum.examples.pizzaorder.application.port.in.RegisterOrderUseCase;
import lombok.extern.java.Log;

@Log
public class RegisterOrderService implements RegisterOrderUseCase {
    @Override
    @Worker(type = "registerOrder")
    public OrderCommand registerOrder(OrderCommand orderCommand) {
        log.info("order registered");
        return orderCommand;
    }
}