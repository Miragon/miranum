package io.miragon.miranum.examples.waiter.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PlaceOrderInCommand {

    private String name;
    private String email;
    private List<String> food;
    private List<String> drinks;
}