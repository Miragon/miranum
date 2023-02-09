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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class Camunda7ProcessAdapterTest {

    @Mock
    private ProcessDefinitionApi processDefinitionApi;
    @Mock
    private Camunda7BaseVariableValueMapper baseVariableMapper;
    @InjectMocks
    private Camunda7ProcessAdapter processAdapter;

    @Test
    public void startProcess_withValidCommand_startsProcessInstance() throws ApiException {
        var processKey = "processKey";
        Map<String, Object> variables = Map.of("var1", "value1", "var2", "value2");
        var processInstanceId = "processInstanceId";

        var startProcessCommand = new StartProcessCommand(processKey, variables);
        var variableValueDtos = Map.of("var1", new VariableValueDto(), "var2", new VariableValueDto());
        var processInstance = new ProcessInstanceWithVariablesDto()
                .id(processInstanceId)
                .variables(variableValueDtos);

        when(baseVariableMapper.map(variables)).thenReturn(variableValueDtos);
        when(processDefinitionApi.startProcessInstanceByKey(processKey, new StartProcessInstanceDto().variables(variableValueDtos)))
                .thenReturn(processInstance);

        processAdapter.startProcess(startProcessCommand);
        verify(processDefinitionApi).startProcessInstanceByKey(processKey, new StartProcessInstanceDto().variables(variableValueDtos));
    }

    @Test
    void startProcess_withApiException_throwsProcessStartingException() throws ApiException {
        var processKey = "processKey";
        Map<String, Object> variables = Map.of("var1", "value1", "var2", "value2");

        var startProcessCommand = new StartProcessCommand(processKey, variables);
        var variableValueDtos = Map.of("var1", new VariableValueDto(), "var2", new VariableValueDto());

        when(baseVariableMapper.map(variables)).thenReturn(variableValueDtos);
        when(processDefinitionApi.startProcessInstanceByKey(processKey, new StartProcessInstanceDto().variables(variableValueDtos)))
                .thenThrow(ApiException.class);

        assertThrows(ProcessStartingException.class, () -> processAdapter.startProcess(startProcessCommand));
    }
}