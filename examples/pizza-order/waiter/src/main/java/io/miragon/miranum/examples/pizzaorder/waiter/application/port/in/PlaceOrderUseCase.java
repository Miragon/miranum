package io.miragon.miranum.examples.pizzaorder.waiter.application.port.in;

public interface PlaceOrderUseCase {

    void placeOrder(PlaceOrderInCommand placeOrderInCommand);
}
