package io.miragon.miranum.connect.worker.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.miragon.miranum.connect.worker.api.WorkerExecuteApi;
import io.miragon.miranum.connect.worker.api.WorkerInterceptor;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Implementation of the worker execute api.
 * Follows the observer pattern to register and execute Workers {@see https://en.wikipedia.org/wiki/Observer_pattern#Java}.
 */
@RequiredArgsConstructor
public class WorkerExecuteApiImpl implements WorkerExecuteApi {

    private final List<WorkerExecutor> workerExecutors = new ArrayList<>();
    private final List<WorkerInterceptor> interceptors;

    /**
     * Registers a Worker.
     *
     * @param WorkerExecutor Worker to be registered
     */
    @Override
    public void register(final WorkerExecutor WorkerExecutor) {
        this.workerExecutors.add(WorkerExecutor);
    }

    /**
     * Executes a Worker.
     * It iterates through all registered Workers and executes the first one that matches the given type.
     *
     * @param type type of the Worker
     * @param data data to be processed
     * @return result of the Worker
     */
    @Override
    public Object execute(final String type, final Object data) {
        final Optional<WorkerExecutor> foundWorker = this.workerExecutors.stream()
                .filter(Worker -> Worker.getType().equals(type))
                .findFirst();

        if (foundWorker.isEmpty()) {
            final String errorMessage = String.format("No Worker for type %s exists", type);
            throw new RuntimeException(errorMessage);
        }

        try {
            final Object in = this.mapInput(foundWorker.get().getInputType(), data);
            final WorkerExecutor workerExecutor = foundWorker.get();
            this.interceptors.forEach(obj -> obj.intercept(in, workerExecutor));
            return this.mapOutput(workerExecutor.execute(in));
        } catch (final IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (final InvocationTargetException e) {
            // Unwrap the exception wrapped by the InvocationTargetException
            throw (RuntimeException) e.getTargetException();
        }
    }

    /**
     * Helper function to map the input data to the expected type.
     * @param inputType
     * @param object
     * @return
     */
    private Object mapInput(final Class<?> inputType, final Object object) {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.convertValue(object, inputType);
    }

    /**
     * Helper function to map the output data to a map.
     * @param output
     * @return
     */
    private Map<String, Object> mapOutput(final Object output) {
        if (Objects.isNull(output)) {
            return new HashMap<>();
        }

        final ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(output, new TypeReference<>() {});
    }


}
