package io.miragon.miranum.connect.binder.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Method;

@Getter
@AllArgsConstructor
public class WorkerInfo {

    private String type;

    private Long timeout;

    private Object instance;

    private Method method;

    private Class<?> inputType;

    private Class<?> outputType;

}
