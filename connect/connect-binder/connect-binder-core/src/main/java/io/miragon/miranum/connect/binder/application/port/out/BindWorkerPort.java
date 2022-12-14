package io.miragon.miranum.connect.binder.application.port.out;

import io.miragon.miranum.connect.binder.domain.WorkerInfo;

public interface BindWorkerPort {

    void bind(WorkerInfo useCaseInfo);

}
