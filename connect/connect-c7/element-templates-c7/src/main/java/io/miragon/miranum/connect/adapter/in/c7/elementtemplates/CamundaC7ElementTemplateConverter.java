package io.miragon.miranum.connect.adapter.in.c7.elementtemplates;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.miragon.miranum.connect.c7.elementtemplates.gen.CamundaC7ElementTemplate;

public class CamundaC7ElementTemplateConverter {

    public static String toJsonString(CamundaC7ElementTemplate elementTemplate) {
        var objectMapper = new ObjectMapper();
        var json = "";
        try {
            json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(elementTemplate);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Could not generate json string!", e);
        }
        return json;
    }
}