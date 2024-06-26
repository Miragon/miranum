package io.miragon.miranum.inquiry.application.port.in;

import io.miragon.miranum.inquiry.application.port.in.model.OfferCreatedCommand;

public interface OfferCreated {

    void handle(OfferCreatedCommand command);

}
