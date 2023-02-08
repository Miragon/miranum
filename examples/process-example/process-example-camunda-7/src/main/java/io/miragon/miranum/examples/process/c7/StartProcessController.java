package io.miragon.miranum.examples.process.c7;

import lombok.AllArgsConstructor;
import org.camunda.community.rest.client.api.ProcessDefinitionApi;
import org.camunda.community.rest.client.dto.ProcessInstanceWithVariablesDto;
import org.camunda.community.rest.client.dto.StartProcessInstanceDto;
import org.camunda.community.rest.client.dto.VariableValueDto;
import org.camunda.community.rest.client.invoker.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/process")
@AllArgsConstructor
public class StartProcessController {

    private final ProcessDefinitionApi processDefinitionApi;

    @PutMapping("/start")
    public ResponseEntity<String> triggerProcessStart(@RequestBody StartProcessRequestDto startProcessRequestDto) {
        try {

            Map<String, VariableValueDto> variables = new HashMap<>();
            variables.put("name", new VariableValueDto().value("name123").type("string"));
            variables.put("key", new VariableValueDto().value("key123").type("string"));

            ProcessInstanceWithVariablesDto processInstance = this.processDefinitionApi.
                    startProcessInstanceByKey(startProcessRequestDto.getProcessKey(),
                            new StartProcessInstanceDto().variables(variables));

            return ResponseEntity.status(HttpStatus.OK).body("Started process with id " + processInstance.getId());
        } catch (ApiException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
