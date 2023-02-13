package io.miragon.miranum.examples.pizzaorder.application.port.in;

public interface RegisterOrderUseCase {

    OrderCommand registerOrder(OrderCommand orderCommand);
}
