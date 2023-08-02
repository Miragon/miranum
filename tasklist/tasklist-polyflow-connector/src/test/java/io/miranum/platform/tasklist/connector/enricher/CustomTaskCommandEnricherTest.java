package io.miranum.platform.tasklist.connector.enricher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.holunda.camunda.taskpool.api.task.SourceReference;
import io.holunda.camunda.taskpool.api.task.TaskIdentityWithPayloadAndCorrelations;
import io.holunda.polyflow.taskpool.collector.task.TaskVariableLoader;
import io.holunda.polyflow.taskpool.collector.task.enricher.ProcessVariablesCorrelator;
import io.holunda.polyflow.taskpool.collector.task.enricher.ProcessVariablesFilter;
import lombok.val;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.spin.plugin.variable.SpinValues;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CustomTaskCommandEnricherTest {

    private final ProcessVariablesFilter processVariablesFilter = Mockito.mock(ProcessVariablesFilter.class);
    private final ProcessVariablesCorrelator processVariablesCorrelator = Mockito.mock(ProcessVariablesCorrelator.class);
    private final TaskVariableLoader taskVariableLoader = Mockito.mock(TaskVariableLoader.class);
    private final CustomTaskCommandEnricher customTaskCommandEnricher = new CustomTaskCommandEnricher(processVariablesFilter, processVariablesCorrelator, taskVariableLoader);

    private final TaskIdentityWithPayloadAndCorrelations task = Mockito.mock(TaskIdentityWithPayloadAndCorrelations.class);
    private final VariableMap payload = Mockito.mock(VariableMap.class);

    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setup_default() {
        val sourceReference = Mockito.mock(SourceReference.class);

        when(sourceReference.getDefinitionKey()).thenReturn("sourceReferenceDefinitionKey");
        when(task.getSourceReference()).thenReturn(sourceReference);
        when(task.getTaskDefinitionKey()).thenReturn("taskDefinitionKey");
        when(processVariablesCorrelator.correlateVariables(any(), any(), any())).thenReturn(Variables.createVariables());
        when(task.getPayload()).thenReturn(payload);
        when(task.getCorrelations()).thenReturn(Variables.createVariables());
    }

    @Test
    public void enrich_task() throws JsonProcessingException {
        val variables = generateVariables();
        when(taskVariableLoader.getTypeVariables(task)).thenReturn(variables);
        when(processVariablesFilter.filterVariables(any(), any(), any())).thenAnswer(i -> i.getArguments()[2]);

        customTaskCommandEnricher.enrich(task);

        verify(task).setEnriched(true);
        verify(payload).putAll(generatePayloadVariables());
    }

    private VariableMap generateVariables() throws JsonProcessingException {

        val variables = Variables.createVariables();
        variables.putValue("string", "test");
        variables.putValue("number", 1);
        variables.putValue("boolean", true);
        variables.putValue("list", SpinValues.jsonValue(
                mapper.writeValueAsString(List.of("a", "b", "c")))
        );
        variables.putValue("object", SpinValues.jsonValue(
                mapper.writeValueAsString(Map.of("a", "1", "b", "2"))
        ));
        return variables;
    }

    private VariableMap generatePayloadVariables() throws JsonProcessingException {
        val variables = Variables.createVariables();
        variables.putValue("string", "test");
        variables.putValue("number", 1);
        variables.putValue("boolean", true);
        variables.putValue("list", Map.of("type", "json", "value", mapper.writeValueAsString(List.of("a", "b", "c"))));
        variables.putValue("object", Map.of("type", "json", "value", mapper.writeValueAsString(Map.of("a", "1", "b", "2"))));
        return variables;
    }

}
