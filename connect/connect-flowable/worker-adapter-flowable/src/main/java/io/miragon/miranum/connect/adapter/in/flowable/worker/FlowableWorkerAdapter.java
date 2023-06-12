package io.miragon.miranum.connect.adapter.in.flowable.worker;

import io.miragon.miranum.connect.worker.api.WorkerSubscription;
import io.miragon.miranum.connect.worker.impl.WorkerExecutor;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FlowableWorkerAdapter implements WorkerSubscription {

    private final JavaDelegateRegistrator javaDelegateRegistrator;

    private final JavaDelegateFactory javaDelegateFactory;

    @Override
    public void subscribe(WorkerExecutor executor) {
        var delegate = javaDelegateFactory.createDelegate(executor);
        javaDelegateRegistrator.register(delegate, executor.getType());
    }
}