package io.miragon.miranum.examples.pizzaorder.waiter.application.port.out;

public interface PlaceOrderPort {

    void placeOrder(PlaceOrderOutCommand placeOrderOutCommand);
}
