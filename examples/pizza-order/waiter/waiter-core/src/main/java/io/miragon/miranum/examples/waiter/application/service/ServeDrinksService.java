package io.miragon.miranum.examples.waiter.application.service;

import io.miragon.miranum.examples.waiter.application.port.in.ServeDrinksCommand;
import io.miragon.miranum.examples.waiter.application.port.in.ServeDrinksUseCase;
import io.miragon.miranum.examples.waiter.domain.Drink;
import io.miragon.miranum.examples.waiter.domain.Waiter;
import lombok.AllArgsConstructor;

import java.util.stream.Collectors;

@AllArgsConstructor
public class ServeDrinksService implements ServeDrinksUseCase {

    private final Waiter waiter;

    @Override
    public void serveDrinks(ServeDrinksCommand serveDrinksCommand) {
        var drinkList = serveDrinksCommand.getDrinks().stream().map(Drink::new).collect(Collectors.toList());
        waiter.serveDrinks(drinkList);
    }
}
