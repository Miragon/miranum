package io.miragon.miranum.platform.application;

import io.miragon.miranum.platform.application.port.in.StartProcessInstanceInPort;
import io.miragon.miranum.platform.application.port.out.StartProcessInstanceOutPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class StartProcessUseCase implements StartProcessInstanceInPort {
    private final StartProcessInstanceOutPort startProcessInstanceOutPort;

    @Override
    public void startInstance(String definitionKey, Map<String, Object> variables, String userId, List<String> groups) {
        startProcessInstanceOutPort.startProcessInstance(definitionKey, variables);
    }

}
