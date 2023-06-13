package io.miragon.miranum.connect.adapter.in.flowable.worker;

import lombok.AllArgsConstructor;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

@AllArgsConstructor
public class JavaDelegateRegistrator {

    private final ConfigurableListableBeanFactory beanFactory;

    public void register(JavaDelegate delegate, String name) {
        beanFactory.registerSingleton(name, delegate);
    }
}