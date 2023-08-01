package io.miranum.platform.tasklist.connector.enricher;

import io.holunda.camunda.taskpool.api.task.TaskIdentityWithPayloadAndCorrelations;
import io.holunda.polyflow.taskpool.collector.task.TaskVariableLoader;
import io.holunda.polyflow.taskpool.collector.task.VariablesEnricher;
import io.holunda.polyflow.taskpool.collector.task.enricher.ProcessVariablesCorrelator;
import io.holunda.polyflow.taskpool.collector.task.enricher.ProcessVariablesFilter;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.spin.impl.json.jackson.JacksonJsonNode;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.Map;

import static java.util.Map.entry;

@Component
@RequiredArgsConstructor
public class CustomTaskCommandEnricher implements VariablesEnricher {
    private final ProcessVariablesFilter processVariablesFilter;
    private final ProcessVariablesCorrelator processVariablesCorrelator;
    private final TaskVariableLoader taskVariableLoader;

    @NotNull
    @Override
    public <T extends TaskIdentityWithPayloadAndCorrelations> T enrich(@NotNull T command) {

        // load variables typed
        VariableMap variablesTyped = taskVariableLoader.getTypeVariables(command);
        variablesTyped = mapToData(variablesTyped);

        // Payload enrichment
        command.getPayload().putAll(
                processVariablesFilter.filterVariables(
                        command.getSourceReference().getDefinitionKey(),
                        command.getTaskDefinitionKey(),
                        variablesTyped
                )
        );

        // Correlations
        command.getCorrelations().putAll(
                processVariablesCorrelator.correlateVariables(
                        command.getSourceReference().getDefinitionKey(),
                        command.getTaskDefinitionKey(),
                        variablesTyped
                )
        );

        // Mark as enriched
        command.setEnriched(true);
        return command;
    }

    private VariableMap mapToData(final VariableMap variables) {
        final VariableMap data = Variables.createVariables();
        variables.forEach((key, value) -> {
            // JSON
            if (value instanceof JacksonJsonNode) {
                val jacksonJsonNode = (JacksonJsonNode) value;
                data.putValue(key, Map.ofEntries(
                        entry("type", "json"),
                        entry("value", jacksonJsonNode.toString())));
            } else {
                data.putValue(key, value);
            }
        });
        return data;
    }

}

