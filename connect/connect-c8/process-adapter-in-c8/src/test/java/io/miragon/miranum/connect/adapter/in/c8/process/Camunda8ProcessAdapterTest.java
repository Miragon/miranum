package io.miragon.miranum.connect.adapter.in.c8.process;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.ZeebeFuture;
import io.camunda.zeebe.client.api.command.CreateProcessInstanceCommandStep1;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import io.miragon.miranum.connect.process.application.port.in.StartProcessCommand;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.concurrent.CompletionStage;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

class Camunda8ProcessAdapterTest {

    private final ZeebeClient zeebeClient = Mockito.mock(ZeebeClient.class);
    private final Camunda8ProcessAdapter adapter = new Camunda8ProcessAdapter(zeebeClient);

    @Test
    public void testStartProcessSuccess() {
        var startProcessCommand = new StartProcessCommand(null, Collections.emptyMap());
        var step1 = Mockito.mock(CreateProcessInstanceCommandStep1.class);
        var step2 = Mockito.mock(CreateProcessInstanceCommandStep1.CreateProcessInstanceCommandStep2.class);
        var step3 = Mockito.mock(CreateProcessInstanceCommandStep1.CreateProcessInstanceCommandStep3.class);
        var future = (ZeebeFuture<ProcessInstanceEvent>) Mockito.mock(ZeebeFuture.class);
        var completionStageMock = (CompletionStage<ProcessInstanceEvent>) Mockito.mock(CompletionStage.class);

        given(zeebeClient.newCreateInstanceCommand()).willReturn(step1);
        given(step1.bpmnProcessId(any())).willReturn(step2);
        given(step2.latestVersion()).willReturn(step3);
        given(step3.variables(startProcessCommand.getVariables())).willReturn(step3);
        given(step3.send()).willReturn(future);
        given(future.whenComplete(any())).willReturn(completionStageMock);

        adapter.startProcess(startProcessCommand);
    }
}