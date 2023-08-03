package io.muenchendigital.digiwf.spring.security.postprocessor;

import org.springframework.boot.context.config.ConfigFileApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * This post processor is registered in spring.factories to be called by the "real" ConfigFileApplicationListener to register the properties from
 * {@code application-polyflow-starter.yaml} in the environment. We prefer this method over adding a configuration annotated with @{@link
 * org.springframework.context.annotation.PropertySource} because the latter registers its properties too late for @{@link
 * org.springframework.boot.autoconfigure.condition.ConditionalOnProperty} annotations to use them.
 */
@Order(Ordered.LOWEST_PRECEDENCE - 1)
public final class DefaultPropertiesEnvironmentPostProcessor extends ConfigFileApplicationListener {
    public DefaultPropertiesEnvironmentPostProcessor() {
        setSearchNames("application-spring-security-starter");
        setOrder(Ordered.LOWEST_PRECEDENCE - 1);
    }
}
