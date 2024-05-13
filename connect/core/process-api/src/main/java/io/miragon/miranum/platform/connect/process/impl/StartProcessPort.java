package io.miragon.miranum.platform.connect.process.impl;

import io.miragon.miranum.platform.connect.process.api.ProcessStartingException;
import io.miragon.miranum.platform.connect.process.api.StartProcessCommand;

public interface StartProcessPort {

    void startProcess(StartProcessCommand startProcessCommand) throws ProcessStartingException;
}
