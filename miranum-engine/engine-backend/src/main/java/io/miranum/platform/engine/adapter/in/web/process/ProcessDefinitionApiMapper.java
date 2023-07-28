package io.miranum.platform.engine.adapter.in.web.process;

import io.miranum.platform.engine.domain.jsonschema.JsonSchema;
import io.miranum.platform.engine.domain.process.MiranumProcessDefinition;
import io.miranum.platform.engine.domain.process.MiranumProcessDefinitionWithSchema;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Map;

@Mapper()
public interface ProcessDefinitionApiMapper {

    List<ProcessDefinitionDto> map2TO(List<MiranumProcessDefinition> list);

    ProcessDefinitionWithSchemaDto map2TO(MiranumProcessDefinitionWithSchema obj);

    default Map<String, Object> mapJsonSchema(JsonSchema jsonSchema) {
        return jsonSchema.asMap();
    }

}
