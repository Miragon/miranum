package io.miragon.miranum.platform.example.shared.sso.webapp;

import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.persistence.GenericManagerFactory;
import org.camunda.bpm.spring.boot.starter.configuration.CamundaProcessEngineConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * Camunda ENGINE configuration.
 * Sets the identity provider to read-only.
 */
@Configuration
public class ReadOnlyIdentityProviderConfiguration implements CamundaProcessEngineConfiguration {
    @Override
    public void preInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
        processEngineConfiguration.setIdentityProviderSessionFactory(
                new GenericManagerFactory(OAuthIdentityServiceProvider.class)
        );
    }
}
