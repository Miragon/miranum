package io.miragon.miranum.examples.waiter.domain;

import lombok.Setter;
import lombok.ToString;
import lombok.extern.java.Log;

import java.util.List;

@ToString
@Setter
@Log
public class Waiter {

    public void serveDrinks(List<Drink> drinkList) {
        log.info("Start serving drinks.");
        try {
            for (var drink : drinkList) {
                log.info(String.format("Serving %s", drink.getName()));
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("Drinks served");
    }

    public void serveFood(List<Food> foodList) {
        log.info("Start serving food.");
        try {
            for (var food : foodList) {
                log.info(String.format("Serving %s", food.getName()));
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("Food served");
    }

    public void issueCheck(List<OrderElement> order) {
        log.info("Issue check");
    }

    public void reassureGuest(String guestName) {
        log.info("Reassuring guest " + guestName + "...");
    }
}