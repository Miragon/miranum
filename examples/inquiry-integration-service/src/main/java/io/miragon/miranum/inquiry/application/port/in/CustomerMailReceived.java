package io.miragon.miranum.inquiry.application.port.in;

import io.miragon.miranum.inquiry.application.port.in.model.NewCustomerMailCommand;

public interface CustomerMailReceived {
    void handle(NewCustomerMailCommand command);
}
