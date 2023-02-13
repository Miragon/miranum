package io.miragon.miranum.examples.process.application.service;

import io.miragon.miranum.examples.process.application.port.in.IssueCheckCommand;
import io.miragon.miranum.examples.process.application.port.in.IssueCheckUseCase;
import io.miragon.miranum.examples.process.domain.Waiter;

public class IssueCheckService implements IssueCheckUseCase {
    @Override
    public void issueCheck(IssueCheckCommand issueCheckCommand) {
        var waiter = new Waiter();
        waiter.issueCheck();

    }
}
