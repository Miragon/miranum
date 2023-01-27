package io.miragon.miranum.connect.binder.worker.application.port.out;

import io.miragon.miranum.connect.binder.worker.domain.WorkerInfo;

public interface BindWorkerPort {

    void bind(WorkerInfo useCaseInfo);

}
