package io.miragon.miranum.connect.binder.job.application.port.out;

import io.miragon.miranum.connect.binder.job.application.port.in.CompleteJobCommand;

public interface CompleteJobPort {

    void completeJob(CompleteJobCommand command);
}
