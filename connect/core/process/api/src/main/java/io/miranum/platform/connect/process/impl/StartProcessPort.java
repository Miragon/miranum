package io.miranum.platform.connect.process.impl;

import io.miranum.platform.connect.process.api.ProcessStartingException;
import io.miranum.platform.connect.process.api.StartProcessCommand;

public interface StartProcessPort {

    void startProcess(StartProcessCommand startProcessCommand) throws ProcessStartingException;
}
