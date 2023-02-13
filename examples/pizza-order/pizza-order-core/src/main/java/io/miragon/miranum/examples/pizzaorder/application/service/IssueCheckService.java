package io.miragon.miranum.examples.pizzaorder.application.service;

import io.miragon.miranum.connect.worker.domain.Worker;
import io.miragon.miranum.examples.pizzaorder.application.port.in.IssueCheckCommand;
import io.miragon.miranum.examples.pizzaorder.application.port.in.IssueCheckUseCase;
import io.miragon.miranum.examples.pizzaorder.domain.Waiter;

public class IssueCheckService implements IssueCheckUseCase {
    @Override
    @Worker(type = "issueCheck")
    public void issueCheck(IssueCheckCommand issueCheckCommand) {
        var waiter = new Waiter();
        waiter.issueCheck();
    }
}
