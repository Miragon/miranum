package io.miranum.platform.connect.worker.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

@Getter
@AllArgsConstructor
public class WorkerExecutor {

    private String type;

    private Long timeout;

    private Object instance;

    private Method method;

    private Class<?> inputType;

    private Class<?> outputType;

    public boolean hasInputType() {
        return Objects.nonNull(this.inputType);
    }

    /**
     * Executes the worker.
     *
     * @param data data to be processed
     * @return result of the worker
     * @throws InvocationTargetException if the underlying method throws an exception.
     * @throws IllegalAccessException    if this method object is enforcing Java language access control and the underlying method is inaccessible.
     */
    public Object execute(final Object data) throws InvocationTargetException, IllegalAccessException {
        return hasInputType() ?
                this.getMethod().invoke(this.instance, data) :
                this.getMethod().invoke(this.instance);
    }
}