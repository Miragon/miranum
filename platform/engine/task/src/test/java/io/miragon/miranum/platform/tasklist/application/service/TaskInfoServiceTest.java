package io.miragon.miranum.platform.tasklist.application.service;

import io.miragon.miranum.platform.engine.application.port.out.process.MiranumProcessDefinitionPort;
import io.miragon.miranum.platform.engine.domain.process.MiranumProcessDefinition;
import io.miragon.miranum.platform.tasklist.application.port.in.TaskInfoUseCase;
import io.miragon.miranum.platform.tasklist.application.port.out.task.TaskOutPort;
import io.miragon.miranum.platform.tasklist.domain.TaskInfo;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class TaskInfoServiceTest {

    private final TaskOutPort taskOutPort = mock(TaskOutPort.class);
    private final MiranumProcessDefinitionPort miranumProcessDefinitionPort = mock(MiranumProcessDefinitionPort.class);

    private final TaskInfoUseCase taskInfoService = new TaskInfoService(taskOutPort, miranumProcessDefinitionPort);

    @Test
    void testCreateTask() {
        // Arrange
        final DelegateTask mockTask = mock(DelegateTask.class);
        when(mockTask.getId()).thenReturn("task123");
        when(mockTask.getProcessDefinitionId()).thenReturn("processDef123");
        when(mockTask.getProcessInstanceId()).thenReturn("instance123");
        when(mockTask.getAssignee()).thenReturn("user123");
        when(mockTask.getCandidates()).thenReturn(Set.of());

        MiranumProcessDefinition mockDefinition = MiranumProcessDefinition.builder()
                .key("processDef123")
                .name("Process Name")
                .build();
        when(miranumProcessDefinitionPort.getProcessDefinitionById("processDef123")).thenReturn(mockDefinition);

        // Act
        taskInfoService.createTask(mockTask);

        // Assert
        final ArgumentCaptor<TaskInfo> taskInfoCaptor = ArgumentCaptor.forClass(TaskInfo.class);
        verify(taskOutPort).createTask(taskInfoCaptor.capture());
        assertThat(taskInfoCaptor.getValue().getId()).isEqualTo("task123");
        assertThat(taskInfoCaptor.getValue().getDescription()).isBlank();
        assertThat(taskInfoCaptor.getValue().getDefinitionName()).isEqualTo("Process Name");
        assertThat(taskInfoCaptor.getValue().getInstanceId()).isEqualTo("instance123");
        assertThat(taskInfoCaptor.getValue().getAssignee()).isEqualTo("user123");
        assertThat(taskInfoCaptor.getValue().getCandidateUsers()).isEmpty();
        assertThat(taskInfoCaptor.getValue().getCandidateGroups()).isEmpty();
        assertThat(taskInfoCaptor.getValue().getFormKey()).isBlank();
    }

    @Test
    void testAssignTask() {
        final String taskId = "12345";
        final String assignee = "user1";
        taskInfoService.assignTask(taskId, assignee);
        verify(taskOutPort).assignTask(taskId, assignee);
    }

    @Test
    void testDeleteTask() {
        final String taskId = "12345";
        taskInfoService.deleteTask(taskId);
        verify(taskOutPort).deleteTask(taskId);
    }

}
