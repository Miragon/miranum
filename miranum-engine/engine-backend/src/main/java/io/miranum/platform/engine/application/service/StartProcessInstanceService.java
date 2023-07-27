package io.miranum.platform.engine.application.service;

import io.miranum.platform.engine.adapter.in.engine.ProcessConstants;
import io.miranum.platform.engine.adapter.out.engine.MiranumEngineDataMapper;
import io.miranum.platform.engine.application.port.in.process.StartProcessInstanceUseCase;
import io.miranum.platform.engine.application.port.out.process.MiranumProcessDefinitionPort;
import io.miranum.platform.engine.application.port.out.process.MiranumProcessInstancePort;
import io.miranum.platform.engine.application.port.out.process.StartProcessInstancePort;
import io.miranum.platform.engine.domain.process.MiranumProcessDefinitionWithSchema;
import io.miranum.platform.engine.domain.process.MiranumProcessInstance;
import io.muenchendigital.digiwf.json.serialization.JsonSerializationService;
import io.muenchendigital.digiwf.json.validation.JsonSchemaValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Service to query service definitions.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class StartProcessInstanceService implements StartProcessInstanceUseCase {

    private final MiranumProcessDefinitionPort miranumProcessDefinitionPort;
    private final StartProcessInstancePort startProcessInstancePort;
    private final MiranumProcessInstancePort miranumProcessInstancePort;

    private final MiranumEngineDataMapper engineDataMapper;
    private final JsonSchemaValidator validationService;
    private final JsonSerializationService serializationService;

    @Override
    public void startInstance(final String definitionKey, final Map<String, Object> variables, final String userId, final List<String> groups) {
        if (!this.miranumProcessDefinitionPort.allowedToStartDefinition(userId, groups, definitionKey)) {
            throw new RuntimeException(String.format("Service definition not accessible: %s", definitionKey));
        }

        final MiranumProcessDefinitionWithSchema processDefinition = this.miranumProcessDefinitionPort.getProcessDefinitionWithSchema(definitionKey);


        //TODO initialize variables
        final Map<String, Object> serializedVariables = this.serializeVariables(processDefinition, variables);


        //add other serializer
        variables.put(ProcessConstants.PROCESS_STARTER_OF_INSTANCE, userId);
        variables.put(ProcessConstants.PROCESS_STATUS, "started");

        final MiranumProcessInstance processInstance = this.startProcessInstancePort.startProcessInstance(processDefinition.getName(), processDefinition.getKey(), serializedVariables);

        this.miranumProcessInstancePort.save(processInstance);
        this.miranumProcessInstancePort.authorizeServiceInstance(processInstance.getId(), userId);

        log.info("process instance for key {} started: {}", processDefinition.getKey(), processInstance.getId());
    }


    private Map<String, Object> serializeVariables(final MiranumProcessDefinitionWithSchema definition, final Map<String, Object> variables) {
        final Map<String, Object> serializedVariables;
        serializedVariables = this.initalizeData(definition, variables);
        return serializedVariables;
    }

    private Map<String, Object> initalizeData(final MiranumProcessDefinitionWithSchema definition, final Map<String, Object> variables) {
        //1. filter readonly data
        final JSONObject filteredData = this.serializationService.filter(definition.getJsonSchema().asMap(), variables, true);
        //2. validate data
        this.validationService.validate(definition.getJsonSchema().asMap(), filteredData.toMap());
        //3. simulate previous data for merging and removing JSON.null values
        final JSONObject previousData = this.serializationService.initialize(new JSONObject(definition.getJsonSchema()).toString());
        final Map<String, Object> clearedData = this.serializationService.merge(filteredData, previousData);
        //4. merge with default values
        final JSONObject defaultValue = this.serializationService.initialize(new JSONObject(definition.getJsonSchema()).toString());
        final Map<String, Object> serializedData = this.serializationService.merge(new JSONObject(clearedData), defaultValue);
        //5. map to engine data and return
        return this.engineDataMapper.mapObjectsToVariables(serializedData);
    }


}
