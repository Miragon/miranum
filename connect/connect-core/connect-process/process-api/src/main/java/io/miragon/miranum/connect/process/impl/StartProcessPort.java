package io.miragon.miranum.connect.process.impl;

import io.miragon.miranum.connect.process.api.ProcessStartingException;
import io.miragon.miranum.connect.process.api.StartProcessCommand;

public interface StartProcessPort {

    void startProcess(StartProcessCommand startProcessCommand) throws ProcessStartingException;
}
