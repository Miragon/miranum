package io.miranum.platform.connect.elementtemplate.c7;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.miranum.platform.connect.c7.elementtemplates.gen.CamundaC7ElementTemplate;

public class CamundaC7ElementTemplateConverter {

    private static final String $SCHEMA = "https://unpkg.com/@camunda/element-templates-json-schema@0.1.0/resources/schema.json";

    public static String toJsonString(CamundaC7ElementTemplate elementTemplate) {
        var mapper = JsonMapper.builder()
                .configure(MapperFeature.SORT_CREATOR_PROPERTIES_FIRST, true)
                .addMixIn(CamundaC7ElementTemplate.class, SchemaMixin.class)
                .build();
        var objectWriter = mapper.writerFor(CamundaC7ElementTemplate.class)
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