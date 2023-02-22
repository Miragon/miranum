package io.miragon.miranum.examples.waiter.adapter.in.rest.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PlaceOrderResponseDto {

    private String name;
    private String email;
    private List<String> drinks;
    private List<String> food;
}