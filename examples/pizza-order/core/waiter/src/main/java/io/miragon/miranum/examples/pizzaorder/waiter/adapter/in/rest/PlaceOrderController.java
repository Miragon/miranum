package io.miragon.miranum.examples.pizzaorder.waiter.adapter.in.rest;

import io.miragon.miranum.examples.pizzaorder.waiter.application.port.in.PlaceOrderInCommand;
import io.miragon.miranum.examples.pizzaorder.waiter.application.port.in.PlaceOrderUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/place-order")
@AllArgsConstructor
public class PlaceOrderController {

    private final PlaceOrderUseCase placeOrderUseCase;

    @PostMapping
    public ResponseEntity<String> placeOrder(@RequestBody PlaceOrderRequestDto placeOrderRequestDto) {
        var placeOrderInCommand = new PlaceOrderInCommand(
                placeOrderRequestDto.getName(),
                placeOrderRequestDto.getEmail(),
                placeOrderRequestDto.getFood(),
                placeOrderRequestDto.getDrinks());
        placeOrderUseCase.placeOrder(placeOrderInCommand);
        // TODO: Should return OrderObject
        return ResponseEntity.ok("successful");
    }
}