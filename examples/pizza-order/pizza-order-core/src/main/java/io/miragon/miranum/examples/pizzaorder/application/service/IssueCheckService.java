package io.miragon.miranum.examples.pizzaorder.application.service;

import io.miragon.miranum.connect.worker.api.Worker;
import io.miragon.miranum.examples.pizzaorder.application.port.in.IssueCheckUseCase;
import io.miragon.miranum.examples.pizzaorder.application.port.in.OrderCommand;
import io.miragon.miranum.examples.pizzaorder.domain.Waiter;
import lombok.extern.java.Log;

@Log
public class IssueCheckService implements IssueCheckUseCase {
    @Override
    @Worker(type = "issueCheck")
    public void issueCheck(OrderCommand orderCommand) {
        var waiter = new Waiter();
        waiter.issueCheck();
        log.info("Dinner successful!");
    }
}
