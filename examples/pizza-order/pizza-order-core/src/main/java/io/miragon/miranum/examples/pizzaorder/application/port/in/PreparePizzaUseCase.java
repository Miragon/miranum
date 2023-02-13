package io.miragon.miranum.examples.pizzaorder.application.port.in;

public interface PreparePizzaUseCase {

    OrderCommand preparePizza(OrderCommand orderCommand);
}
