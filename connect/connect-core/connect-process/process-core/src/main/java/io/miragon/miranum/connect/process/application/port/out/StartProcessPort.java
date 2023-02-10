package io.miragon.miranum.connect.process.application.port.out;

import io.miragon.miranum.connect.process.application.port.in.StartProcessCommand;
import io.miragon.miranum.connect.process.domain.ProcessStartingException;

public interface StartProcessPort {

    void startProcess(StartProcessCommand startProcessCommand) throws ProcessStartingException;
}
