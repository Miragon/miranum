package io.miragon.miranum.connect.binder.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Method;

@Getter
@AllArgsConstructor
public class UseCaseInfo {

    private String type;

    private Object instance;

    private Method method;

    private Class<?> inputType[];

    private Class<?> outputType;

}
