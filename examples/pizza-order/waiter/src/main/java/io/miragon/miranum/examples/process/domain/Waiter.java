package io.miragon.miranum.examples.process.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.java.Log;

@ToString
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Log
public class Waiter {
    private String id;

    public void deliverDrinks() {
        log.info("Delivering drinks");
    }

    public void deliverPizza() {
        log.info("Deliver pizza");
    }

    public void issueCheck() {
        log.info("Deliver pizza");
    }
}
