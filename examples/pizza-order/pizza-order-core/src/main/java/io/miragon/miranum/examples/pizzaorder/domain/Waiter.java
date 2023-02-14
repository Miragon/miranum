package io.miragon.miranum.examples.pizzaorder.domain;

import lombok.Setter;
import lombok.ToString;
import lombok.extern.java.Log;

@ToString
@Setter
@Log
public class Waiter {

    public void serveDrinks() {
        log.info("Serve drinks");
    }

    public void servePizza() {
        log.info("Serve pizza");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("Pizza served");
    }

    public void issueCheck() {
        log.info("Issue check");
    }
}