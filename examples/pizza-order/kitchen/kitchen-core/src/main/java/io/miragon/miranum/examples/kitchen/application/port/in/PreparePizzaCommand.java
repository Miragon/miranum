package io.miragon.miranum.examples.kitchen.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PreparePizzaCommand {

    private String email;
    private List<String> food;
}
