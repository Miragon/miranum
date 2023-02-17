package io.miragon.miranum.examples.pizzaorder.waiter.application.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.miragon.miranum.examples.pizzaorder.waiter.application.port.in.PlaceOrderInCommand;
import io.miragon.miranum.examples.pizzaorder.waiter.application.port.in.PlaceOrderUseCase;
import io.miragon.miranum.examples.pizzaorder.waiter.application.port.out.PlaceOrderOutCommand;
import io.miragon.miranum.examples.pizzaorder.waiter.application.port.out.PlaceOrderPort;
import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class PlaceOrderService implements PlaceOrderUseCase {

    private final PlaceOrderPort placeOrderPort;

    @Override
    public void placeOrder(PlaceOrderInCommand placeOrderInCommand) {
        var objectMapper = new ObjectMapper();
        Map<String, Object> variables = objectMapper.convertValue(placeOrderInCommand, new TypeReference<>() {});
        var placeOrderOutCommand = new PlaceOrderOutCommand("OrderPizza_C8", variables); ;
        placeOrderPort.placeOrder(placeOrderOutCommand);
    }
}