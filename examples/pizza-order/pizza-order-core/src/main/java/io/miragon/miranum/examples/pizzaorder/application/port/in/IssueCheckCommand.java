package io.miragon.miranum.examples.pizzaorder.application.port.in;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IssueCheckCommand {

    private String name;
}
