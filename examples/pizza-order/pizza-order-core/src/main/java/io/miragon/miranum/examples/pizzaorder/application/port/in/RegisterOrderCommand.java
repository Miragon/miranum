package io.miragon.miranum.examples.pizzaorder.application.port.in;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RegisterOrderCommand {
    private List<String> drinks;
    private List<String> pizza;
}
