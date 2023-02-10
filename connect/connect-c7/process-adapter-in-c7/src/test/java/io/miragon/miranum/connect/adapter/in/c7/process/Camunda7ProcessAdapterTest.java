package io.miragon.miranum.connect.adapter.in.c7.process;

import io.miragon.miranum.connect.c7.utils.Camunda7BaseVariableValueMapper;
import io.miragon.miranum.connect.process.application.port.in.StartProcessCommand;
import io.miragon.miranum.connect.process.domain.ProcessStartingException;
import org.camunda.community.rest.client.api.ProcessDefinitionApi;
import org.camunda.community.rest.client.dto.ProcessInstanceWithVariablesDto;
import org.camunda.community.rest.client.dto.StartProcessInstanceDto;
import org.camunda.community.rest.client.dto.VariableValueDto;
import org.camunda.community.rest.client.invoker.ApiException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class Camunda7ProcessAdapterTest {

    private static final String KEY_1 = "var1";
    private static final String KEY_2 = "var2";
    private static final String VALUE_1 = "value1";
    private static final String VALUE_2 = "value2";
    private static final String PROCESS_KEY = "processKey";

    private final Map<String, Object> variables = Map.ofEntries(
            entry(KEY_1, VALUE_1),
            entry(KEY_2, VALUE_2)
    );

    private final Map<String, VariableValueDto> variableValueDtos = Map.ofEntries(
            entry(KEY_1, new VariableValueDto().value(VALUE_1)),
            entry(KEY_2, new VariableValueDto().value(VALUE_2))
    );

    @Mock
    private ProcessDefinitionApi processDefinitionApi;
    @Mock
    private Camunda7BaseVariableValueMapper baseVariableMapper;
    @InjectMocks
    private Camunda7ProcessAdapter processAdapter;

    @Test
    public void startProcess_withValidCommand_startsProcessInstance() throws ApiException {
        var processInstanceId = "processInstanceId";

        var startProcessCommand = new StartProcessCommand(PROCESS_KEY, this.variables);
        var processInstance = new ProcessInstanceWithVariablesDto()
                .id(processInstanceId)
                .variables(variableValueDtos);

        when(baseVariableMapper.map(variables)).thenReturn(variableValueDtos);
        when(processDefinitionApi.startProcessInstanceByKey(PROCESS_KEY, new StartProcessInstanceDto().variables(variableValueDtos)))
                .thenReturn(processInstance);

        processAdapter.startProcess(startProcessCommand);
        verify(processDefinitionApi).startProcessInstanceByKey(PROCESS_KEY, new StartProcessInstanceDto().variables(variableValueDtos));
    }

    @Test
    void startProcess_withApiException_throwsProcessStartingException() throws ApiException {
        var startProcessCommand = new StartProcessCommand(PROCESS_KEY, variables);

        when(baseVariableMapper.map(variables)).thenReturn(variableValueDtos);
        when(processDefinitionApi.startProcessInstanceByKey(PROCESS_KEY, new StartProcessInstanceDto().variables(variableValueDtos)))
                .thenThrow(ApiException.class);

        assertThrows(ProcessStartingException.class, () -> processAdapter.startProcess(startProcessCommand));
    }
}