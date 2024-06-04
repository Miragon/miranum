package io.miragon.miranum.platform.adapter.out.miranum;

import io.miragon.miranum.connect.process.api.ProcessApi;
import io.miragon.miranum.connect.process.api.StartProcessCommand;
import io.miragon.miranum.platform.application.port.out.StartProcessInstanceOutPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@RequiredArgsConstructor
@Component
public class MiranumProcessAdapter implements StartProcessInstanceOutPort {

    private final ProcessApi processApi;

    @Override
    public void startProcessInstance(String definitionKey, Map<String, Object> variables) {
        processApi.startProcess(StartProcessCommand.builder()
            .processKey(definitionKey)
            .variables(variables)
            .build());
    }
}
