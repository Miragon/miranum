package io.miranum.platform.engine.adapter.in.engine;

import io.miranum.platform.engine.application.port.in.processconfig.ProcessConfigQuery;
import io.miranum.platform.engine.domain.process.ProcessConfig;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.impl.context.BpmnExecutionContext;
import org.camunda.bpm.engine.impl.context.Context;
import org.springframework.stereotype.Component;

/**
 * Process Config functions for modelling.
 */
@Component("processconfig")
@RequiredArgsConstructor
public class ProcessConfigFunctions {

    private final ProcessConfigQuery processConfigQuery;


    /**
     * Get process config entry by key.
     *
     * @param key Key of the process config entry
     * @return Config entry
     */
    public String get(final String key) {
        final ProcessConfig processConfig = this.getProcessConfigForCurrentProcess();
        return processConfig.getConfig(key);
    }

    /**
     * Get process config entry by key.
     *
     * @param configKey  Key of the process config entry
     * @param processKey Key of the process
     * @return Config entry
     */
    public String get(final String configKey, final String processKey) {
        final ProcessConfig processConfig = this.processConfigQuery.getByRef(processKey);
        return processConfig.getConfig(configKey);
    }


    /**
     * Get the whole processconfig for the current process.
     *
     * @return processconfig
     */
    private ProcessConfig getProcessConfigForCurrentProcess() {
        final BpmnExecutionContext context = Context.getBpmnExecutionContext();
        if (context == null) {
            return null;
        }
        final String currentProcess = context.getExecution().getProcessDefinition().getKey();
        return this.processConfigQuery.getByRef(currentProcess);
    }
}
