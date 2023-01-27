package io.miragon.miranum.connect.binder.job.application.port.out;

import io.miragon.miranum.connect.binder.job.application.port.in.FetchAndLockJobCommand;
import io.miragon.miranum.connect.binder.job.domain.Job;

public interface FetchAndLockJobPort {

    Job fetchAndLock(FetchAndLockJobCommand command);
}
