package io.miragon.miranum.examples.waiter.application.service;

import io.miragon.miranum.examples.waiter.application.port.in.ReassureGuestCommand;
import io.miragon.miranum.examples.waiter.application.port.in.ReassureGuestUseCase;
import io.miragon.miranum.examples.waiter.domain.Waiter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ReassureGuestService implements ReassureGuestUseCase {

   private final Waiter waiter;

    @Override
    public void reassureGuest(ReassureGuestCommand reassureGuestCommand) {
        waiter.reassureGuest(reassureGuestCommand.getName());
    }
}