package io.miranum.platform.engine.processdefinition.domain.service;

import io.miranum.platform.engine.adapter.out.engine.MiranumEngineDataMapper;
import io.miranum.platform.engine.processdefinition.domain.model.ServiceDefinitionDetail;
import io.muenchendigital.digiwf.json.serialization.JsonSerializationService;
import io.muenchendigital.digiwf.json.validation.JsonSchemaValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServiceDefinitionDataService {

    private final MiranumEngineDataMapper engineDataMapper;
    private final JsonSchemaValidator validationService;
    private final JsonSerializationService serializationService;

    public Map<String, Object> serializeVariables(final ServiceDefinitionDetail definition, final Map<String, Object> variables) {
        final Map<String, Object> serializedVariables;
        serializedVariables = this.initalizeData(definition, variables);
        return serializedVariables;
    }

    private Map<String, Object> initalizeData(final ServiceDefinitionDetail definition, final Map<String, Object> variables) {
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
