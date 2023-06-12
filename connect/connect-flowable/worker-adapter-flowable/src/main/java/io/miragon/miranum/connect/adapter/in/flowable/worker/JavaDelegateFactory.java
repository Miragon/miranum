package io.miragon.miranum.connect.adapter.in.flowable.worker;

import io.miragon.miranum.connect.worker.api.WorkerExecuteApi;
import io.miragon.miranum.connect.worker.impl.WorkerExecutor;
import lombok.AllArgsConstructor;
import org.flowable.engine.delegate.JavaDelegate;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@AllArgsConstructor
public class JavaDelegateFactory {

    private final WorkerExecuteApi workerExecuteApi;

    public JavaDelegate createDelegate(WorkerExecutor executor) {
        return execution -> {
            try {
                var result = executor.execute(execution.getVariables());
                workerExecuteApi.execute(executor, execution.getVariables());
                execution.setVariables((Map<String, Object>) result);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        };
    }
}