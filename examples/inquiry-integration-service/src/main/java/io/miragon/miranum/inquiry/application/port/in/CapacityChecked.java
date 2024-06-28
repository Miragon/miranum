package io.miragon.miranum.inquiry.application.port.in;

import io.miragon.miranum.inquiry.application.port.in.model.CapacityCheckedCommand;

public interface CapacityChecked {

    void handle(CapacityCheckedCommand command);

}
