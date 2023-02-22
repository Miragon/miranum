package io.miragon.miranum.examples.kitchen.domain;

import lombok.extern.java.Log;

import java.util.List;
import java.util.Random;

@Log
public class Kitchen {

    public void makePizza(List<Pizza> pizzaList) {
        var random = new Random();
        try {
            log.info("Started making pizza...");
            for (var pizza : pizzaList) {
                log.info(String.format("Preparing pizza %s", pizza.getName()));
                var timeToPrepareInSeconds = 1 + Math.abs(random.nextInt()) % 10;
                Thread.sleep(timeToPrepareInSeconds * 1000);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("Pizza is done!");
    }
}