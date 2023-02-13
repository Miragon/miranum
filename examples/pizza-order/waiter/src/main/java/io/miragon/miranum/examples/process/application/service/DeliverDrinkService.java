package io.miragon.miranum.examples.process.application.service;

import io.miragon.miranum.examples.process.application.port.in.DeliverDrinksCommand;
import io.miragon.miranum.examples.process.application.port.in.DeliverDrinksUseCase;
import io.miragon.miranum.examples.process.domain.Waiter;
import lombok.extern.java.Log;

@Log
public class DeliverDrinkService implements DeliverDrinksUseCase {

    @Override
    public void deliverDrinks(DeliverDrinksCommand deliverDrinksCommand) {
        var waiter = new Waiter();
        waiter.deliverDrinks();
    }
}
