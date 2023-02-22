package io.miragon.miranum.examples.waiter.application.service;

import io.miragon.miranum.examples.waiter.application.port.in.IssueCheckCommand;
import io.miragon.miranum.examples.waiter.application.port.in.IssueCheckUseCase;
import io.miragon.miranum.examples.waiter.domain.Drink;
import io.miragon.miranum.examples.waiter.domain.Food;
import io.miragon.miranum.examples.waiter.domain.Waiter;
import lombok.AllArgsConstructor;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
public class IssueCheckService implements IssueCheckUseCase {

    private final Waiter waiter;

    @Override
    public void issueCheck(IssueCheckCommand issueCheckCommand) {
        var drinksStream = issueCheckCommand.getDrinks().stream().map(Drink::new);
        var foodStream = issueCheckCommand.getFood().stream().map(Food::new);
        var order = Stream.concat(drinksStream, foodStream).collect(Collectors.toList());
        waiter.issueCheck(order);
    }
}