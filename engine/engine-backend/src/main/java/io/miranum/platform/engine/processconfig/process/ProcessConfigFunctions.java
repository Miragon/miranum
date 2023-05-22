package io.miranum.platform.engine.processconfig.process;

import io.miranum.platform.engine.processconfig.domain.model.ProcessConfig;
import io.miranum.platform.engine.processconfig.domain.service.ProcessConfigService;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.impl.context.BpmnExecutionContext;
import org.camunda.bpm.engine.impl.context.Context;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Process Config functions for modelling.
 */
@Component("processconfig")
@RequiredArgsConstructor
public class ProcessConfigFunctions {

    private final ProcessConfigService processConfigService;


    /**
     * Get process config entry by key.
     *
     * @param key Key of the process config entry
     * @return Config entry
     */
    public String get(final String key) {
        final Optional<ProcessConfig> processConfig = this.getProcessConfigForCurrentProcess();
        return processConfig.map(config -> config.getConfig(key)).orElse(null);
    }

    /**
     * Get process config entry by key.
     *
     * @param configKey  Key of the process config entry
     * @param processKey Key of the process
     * @return Config entry
     */
    public Optional<String> get(final String configKey, final String processKey) {
        Optional<String> configValue = Optional.ofNullable(this.get(configKey));
        if (configValue.isEmpty()) {
            configValue = this.processConfigService.getProcessConfig(processKey)
                    .map(processConfig -> processConfig.getConfig(configKey));
        }
        return configValue;
    }

    /**
     * Get the guid of a Statusdokument.
     *
     * @return guid
     */
    public String getStatusDokument() {
        final ProcessConfig processConfig = this.getProcessConfigForCurrentProcess().orElseThrow();
        return processConfig.getStatusDokument();
    }

    /**
     * Get the whole processconfig for the current process.
     *
     * @return processconfig
     */
    private Optional<ProcessConfig> getProcessConfigForCurrentProcess() {
        final BpmnExecutionContext context = Context.getBpmnExecutionContext();
        if (context == null) {
            return Optional.empty();
        }
        final String currentProcess = context.getExecution().getProcessDefinition().getKey();
        return this.processConfigService.getProcessConfig(currentProcess);
    }
}
