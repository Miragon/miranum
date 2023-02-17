package io.miragon.miranum.examples.pizzaorder.waiter.adapter.out;

import io.miragon.miranum.connect.process.api.ProcessApi;
import io.miragon.miranum.connect.process.api.StartProcessCommand;
import io.miragon.miranum.examples.pizzaorder.waiter.application.port.out.PlaceOrderOutCommand;
import io.miragon.miranum.examples.pizzaorder.waiter.application.port.out.PlaceOrderPort;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProcessAdapter implements PlaceOrderPort {

    private final ProcessApi processApi;

    @Override
    public void placeOrder(PlaceOrderOutCommand placeOrderOutCommand) {
        var startProcessCommand = new StartProcessCommand(placeOrderOutCommand.getProcessKey(), placeOrderOutCommand.getVariables());
        processApi.startProcess(startProcessCommand);
    }
}
