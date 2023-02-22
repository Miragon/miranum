package io.miragon.miranum.examples.waiter.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReassureGuestCommand {

    private String name;
}