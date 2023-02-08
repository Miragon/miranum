package io.miragon.miranum.connect.process.application.port.out;

import io.miragon.miranum.connect.process.application.port.in.StartProcessCommand;

public interface StartProcessPort {

    void startProcess(StartProcessCommand startProcessCommand);

}
