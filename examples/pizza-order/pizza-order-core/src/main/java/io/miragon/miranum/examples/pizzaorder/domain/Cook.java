package io.miragon.miranum.examples.pizzaorder.domain;

import lombok.extern.java.Log;

import java.util.Random;

@Log
public class Cook {

    public void makePizza() {
        var random = new Random();
        try {
            log.info("Started making pizza...");
            var timeToPrepareInSeconds = random.nextInt() % 10;
            Thread.sleep(timeToPrepareInSeconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("Pizza is done!");
    }
}
