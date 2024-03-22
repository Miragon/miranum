package io.miranum.platform.connect.elementtemplate.c8;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.miranum.platform.connect.elementtemplate.c8.schema.CamundaC8ElementTemplate;

public class CamundaC8ElementTemplateConverter {

    private static final String $SCHEMA = "https://unpkg.com/@camunda/zeebe-element-templates-json-schema@0.8.0/resources/schema.json";

    public static String toJsonString(CamundaC8ElementTemplate elementTemplate) {
        var mapper = JsonMapper.builder()
                .configure(MapperFeature.SORT_CREATOR_PROPERTIES_FIRST, true)
                .addMixIn(CamundaC8ElementTemplate.class, SchemaMixin.class)
                .build();
        var objectWriter = mapper.writerFor(CamundaC8ElementTemplate.class)
                .withAttribute("$schema", $SCHEMA);
        String json;
        try {
            json = objectWriter.withDefaultPrettyPrinter().writeValueAsString(elementTemplate);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Could not generate json string!", e);
        }
        return json;
    }
}
