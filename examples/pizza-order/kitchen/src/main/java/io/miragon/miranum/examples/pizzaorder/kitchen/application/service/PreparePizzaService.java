package io.miragon.miranum.examples.pizzaorder.kitchen.application.service;

import io.miragon.miranum.examples.pizzaorder.kitchen.application.port.in.PreparePizzaCommand;
import io.miragon.miranum.examples.pizzaorder.kitchen.application.port.in.PreparePizzaUseCase;
import io.miragon.miranum.examples.pizzaorder.kitchen.domain.Kitchen;
import io.miragon.miranum.examples.pizzaorder.kitchen.domain.Pizza;
import lombok.AllArgsConstructor;

import java.util.stream.Collectors;

@AllArgsConstructor
public class PreparePizzaService implements PreparePizzaUseCase {

    private final Kitchen kitchen;

    @Override
    public void preparePizza(PreparePizzaCommand preparePizzaCommand) {
        var pizzaList = preparePizzaCommand.getFood().stream().map(Pizza::new).collect(Collectors.toList());
        kitchen.makePizza(pizzaList);
    }
}