package io.miragon.miranum.examples.waiter.application.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.miragon.miranum.examples.waiter.application.port.in.PlaceOrderInCommand;
import io.miragon.miranum.examples.waiter.application.port.in.PlaceOrderUseCase;
import io.miragon.miranum.examples.waiter.application.port.out.PlaceOrderOutCommand;
import io.miragon.miranum.examples.waiter.application.port.out.PlaceOrderPort;

import java.util.Map;

public class PlaceOrderService implements PlaceOrderUseCase {

    private final PlaceOrderPort placeOrderPort;
    private final String processId = "PizzaOrder";

    public PlaceOrderService(PlaceOrderPort placeOrderPort) {
        this.placeOrderPort = placeOrderPort;
    }

    @Override
    public void placeOrder(PlaceOrderInCommand placeOrderInCommand) {
        var objectMapper = new ObjectMapper();
        Map<String, Object> variables = objectMapper.convertValue(placeOrderInCommand, new TypeReference<>() {
        });
        var placeOrderOutCommand = new PlaceOrderOutCommand(processId, variables);
        placeOrderPort.placeOrder(placeOrderOutCommand);
    }
}