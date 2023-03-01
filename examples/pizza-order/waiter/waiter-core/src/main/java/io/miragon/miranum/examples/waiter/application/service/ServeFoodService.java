package io.miragon.miranum.examples.waiter.application.service;

import io.miragon.miranum.examples.waiter.application.port.in.ServeFoodCommand;
import io.miragon.miranum.examples.waiter.application.port.in.ServeFoodUseCase;
import io.miragon.miranum.examples.waiter.domain.Food;
import io.miragon.miranum.examples.waiter.domain.Waiter;
import lombok.AllArgsConstructor;

import java.util.stream.Collectors;

@AllArgsConstructor
public class ServeFoodService implements ServeFoodUseCase {

    private final Waiter waiter;

    @Override
    public void serveFood(ServeFoodCommand serveFoodCommand) {
        var foodList = serveFoodCommand.getFood().stream().map(Food::new).collect(Collectors.toList());
        waiter.serveFood(foodList);
    }
}
