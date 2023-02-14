package io.miragon.miranum.examples.pizzaorder.application.port.in;

public interface ServePizzaUseCase {

    OrderCommand servePizza(OrderCommand orderCommand);
}