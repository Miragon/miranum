package io.miragon.miranum.examples.pizzaorder.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class OrderCommand {

    private List<String> drinks;
    private List<String> pizza;
}