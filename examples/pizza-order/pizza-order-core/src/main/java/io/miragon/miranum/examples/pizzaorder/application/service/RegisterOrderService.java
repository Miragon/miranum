package io.miragon.miranum.examples.pizzaorder.application.service;

import io.miragon.miranum.connect.worker.domain.Worker;
import io.miragon.miranum.examples.pizzaorder.application.port.in.RegisterOrderUseCase;
import io.miragon.miranum.examples.pizzaorder.application.port.in.RegisterOrderCommand;
import lombok.extern.java.Log;

@Log
public class RegisterOrderService implements RegisterOrderUseCase {
    @Override
    @Worker(type = "registerOrder")
    public void registerOrder(RegisterOrderCommand registerOrderCommand) {
        log.info("order registered");
    }
}
