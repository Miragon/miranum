package io.miragon.miranum.connect.adapter.in.flowable.worker;
import org.flowable.engine.delegate.JavaDelegate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import static org.mockito.Mockito.*;

public class JavaDelegateRegistratorTest {

    private ConfigurableListableBeanFactory beanFactory;
    private JavaDelegateRegistrator registrator;

    @BeforeEach
    public void setUp() {
        beanFactory = mock(ConfigurableListableBeanFactory.class);
        registrator = new JavaDelegateRegistrator(beanFactory);
    }

    @Test
    public void testRegister() {
        JavaDelegate delegate = mock(JavaDelegate.class);
        String name = "testDelegate";

        registrator.register(delegate, name);

        verify(beanFactory, times(1)).registerSingleton(name, delegate);
    }
}