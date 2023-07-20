package io.miranum.platform.tasklist.adapter.out.schema;

import io.holunda.polyflow.view.Task;
import io.muenchendigital.digiwf.json.serialization.JsonSerializationService;
import io.muenchendigital.digiwf.json.validation.DigiWFValidationException;
import io.muenchendigital.digiwf.json.validation.JsonSchemaValidator;
import io.miranum.platform.tasklist.application.port.out.schema.JsonSchemaValidationPort;
import io.miranum.platform.tasklist.domain.JsonSchema;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class JsonSchemaValidationAdapter implements JsonSchemaValidationPort {
    private final JsonSerializationService serializationService;
    private final JsonSchemaValidator jsonSchemaValidator;
    private final EngineDataMapper engineDataMapper;

    @Override
    public Map<String, Object> validateAndSerialize(JsonSchema schema, Task task, Map<String, Object> variables) throws DigiWFValidationException {

        val filteredData = this.serializationService.filter(schema.asMap(), variables, true);

        this.jsonSchemaValidator.validate(schema.asMap(), filteredData.toMap());

        val taskData = this.engineDataMapper.mapToData(task.getPayload());
        val targetData = this.serializationService.deserializeData(schema.asMap(), taskData);
        val serializedData = this.serializationService.merge(filteredData, new JSONObject(targetData));
        val defaultValue = this.serializationService.initialize(new JSONObject(schema.getSchema()).toString());
        val serializedDataWithDefaultValues = this.serializationService.merge(new JSONObject(serializedData), defaultValue);

        return this.engineDataMapper.mapObjectsToVariables(serializedDataWithDefaultValues);
    }

    @Override
    public Map<String, Object> filterVariables(Map<String, Object> data, JsonSchema schema) {
        return this.engineDataMapper.mapToData(this.serializationService.deserializeData(schema.asMap(), data));
    }
}
