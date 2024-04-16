package io.miragon.miranum.platform.connect.process.process.impl;

import io.miragon.miranum.platform.connect.process.process.api.StartProcessCommand;
import io.miragon.miranum.platform.connect.process.process.api.ProcessStartingException;

public interface StartProcessPort {

    void startProcess(StartProcessCommand startProcessCommand) throws ProcessStartingException;
}
