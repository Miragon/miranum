package io.miragon.miraum.fitconnect.integration.authority.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.miragon.miranum.connect.process.api.StartProcessCommand;

import java.util.Map;

public class ProcessCommandFactory {

    public static StartProcessCommand create(String jsonString, String processKey) throws JsonProcessingException {
        var objectMapper = new ObjectMapper();
        Map<String, Object> payloadAsMap = objectMapper.readValue(jsonString, Map.class);

        return new StartProcessCommand(processKey, payloadAsMap);
    }
}
