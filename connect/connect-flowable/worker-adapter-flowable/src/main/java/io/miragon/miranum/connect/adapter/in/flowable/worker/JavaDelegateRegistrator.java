package io.miragon.miranum.connect.adapter.in.flowable.worker;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

@AllArgsConstructor
public class JavaDelegateRegistrator {

    private final ConfigurableListableBeanFactory beanFactory;

    public void register(Object obj, String name) {
        beanFactory.registerSingleton(name, obj);
    }
}