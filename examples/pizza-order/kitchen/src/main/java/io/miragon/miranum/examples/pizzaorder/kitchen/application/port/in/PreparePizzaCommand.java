package io.miragon.miranum.examples.pizzaorder.kitchen.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PreparePizzaCommand {

    private List<String> food;
}
