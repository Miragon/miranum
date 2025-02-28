package io.miragon.miranum.platform.tasklist.application.service;

import io.miragon.miranum.platform.engine.api.MiranumProcessDefinition;
import io.miragon.miranum.platform.engine.api.MiranumProcessDefinitionApi;
import io.miragon.miranum.platform.tasklist.TaskProperties;
import io.miragon.miranum.platform.tasklist.application.port.in.TaskInfoUseCase;
import io.miragon.miranum.platform.tasklist.application.port.out.task.TaskOutPort;
import io.miragon.miranum.platform.tasklist.domain.TaskInfo;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class TaskInfoServiceTest {

    private final TaskOutPort taskOutPort = mock(TaskOutPort.class);
    private final MiranumProcessDefinitionApi miranumProcessDefinitionPort = mock(MiranumProcessDefinitionApi.class);
    private final TaskProperties taskProperties = new TaskProperties();

    private final TaskInfoUseCase taskInfoService = new TaskInfoService(taskOutPort, miranumProcessDefinitionPort, taskProperties);

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
        assertThat(taskInfoCaptor.getValue())
                .hasFieldOrPropertyWithValue("id", "task123")
                .hasFieldOrProperty("description")
                .hasFieldOrPropertyWithValue("definitionName", "Process Name")
                .hasFieldOrPropertyWithValue("instanceId", "instance123")
                .hasFieldOrPropertyWithValue("assignee", "user123")
                .hasFieldOrPropertyWithValue("candidateUsers", List.of())
                .hasFieldOrPropertyWithValue("candidateGroups", List.of())
                .hasFieldOrProperty("formKey")
                .hasFieldOrPropertyWithValue("customFields", List.of());
    }

    @Test
    void testCreateTaskForProcessesWithoutAProcessName() {
        // Arrange
        final DelegateTask mockTask = mock(DelegateTask.class);
        when(mockTask.getId()).thenReturn("task123");
        when(mockTask.getProcessDefinitionId()).thenReturn("processDef123");
        when(mockTask.getProcessInstanceId()).thenReturn("instance123");
        when(mockTask.getAssignee()).thenReturn("user123");
        when(mockTask.getCandidates()).thenReturn(Set.of());

        MiranumProcessDefinition mockDefinition = MiranumProcessDefinition.builder()
                .key("processDef123")
                .name(null)
                .build();
        when(miranumProcessDefinitionPort.getProcessDefinitionById("processDef123")).thenReturn(mockDefinition);

        // Act
        taskInfoService.createTask(mockTask);

        // Assert
        final ArgumentCaptor<TaskInfo> taskInfoCaptor = ArgumentCaptor.forClass(TaskInfo.class);
        verify(taskOutPort).createTask(taskInfoCaptor.capture());
        assertThat(taskInfoCaptor.getValue())
                .hasFieldOrPropertyWithValue("definitionName", mockDefinition.getKey());
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
