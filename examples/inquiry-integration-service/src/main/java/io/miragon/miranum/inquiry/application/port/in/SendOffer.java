package io.miragon.miranum.inquiry.application.port.in;

import io.miragon.miranum.inquiry.application.port.in.model.SendCommand;

public interface SendOffer {
    void handle(SendCommand command);
}
