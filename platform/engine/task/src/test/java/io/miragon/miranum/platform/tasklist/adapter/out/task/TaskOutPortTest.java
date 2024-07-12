package io.miragon.miranum.platform.tasklist.adapter.out.task;

import io.miragon.miranum.platform.tasklist.adapter.out.task.taskinfo.TaskAuthorityEntity;
import io.miragon.miranum.platform.tasklist.adapter.out.task.taskinfo.TaskCustomFieldEntity;
import io.miragon.miranum.platform.tasklist.adapter.out.task.taskinfo.TaskInfoEntity;
import io.miragon.miranum.platform.tasklist.adapter.out.task.taskinfo.TaskInfoRepository;
import io.miragon.miranum.platform.tasklist.application.port.out.task.TaskOutPort;
import io.miragon.miranum.platform.tasklist.domain.Task;
import io.miragon.miranum.platform.tasklist.domain.TaskAuthorities;
import io.miragon.miranum.platform.tasklist.domain.TaskCustomFields;
import io.miragon.miranum.platform.tasklist.domain.TaskInfo;
import io.miragon.miranum.platform.tasklist.exception.TaskNotFoundException;
import org.apache.commons.lang3.NotImplementedException;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.TaskQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TaskOutPortTest {

    private final TaskService taskService = mock(TaskService.class);
    private final TaskInfoRepository taskInfoRepository = mock(TaskInfoRepository.class);

    private TaskMapper taskMapper = Mappers.getMapper(TaskMapper.class);

    private final TaskOutPort taskOutPort = new TaskPersistenceAdapter(taskService, taskInfoRepository, taskMapper);

    private final String user = "user1";
    private final String group = "group1";

    private final TaskQuery taskQuery = mock(TaskQuery.class);
    private String taskId;
    private String instanceId;

    @BeforeEach
    void setUp() {
        taskId = UUID.randomUUID().toString();
        instanceId = UUID.randomUUID().toString();

        final org.camunda.bpm.engine.task.Task camundaTask = mock(org.camunda.bpm.engine.task.Task.class);
        when(camundaTask.getId()).thenReturn(taskId);
        when(camundaTask.getName()).thenReturn("testTask");

        when(taskService.createTaskQuery()).thenReturn(taskQuery);
        when(taskQuery.taskIdIn(any())).thenReturn(taskQuery);
        when(taskQuery.list()).thenReturn(List.of(camundaTask));

        when(taskQuery.taskId(taskId)).thenReturn(taskQuery);
        when(taskQuery.singleResult()).thenReturn(camundaTask);
    }

    @Test
    void testGetTasksForUser() {
        final TaskInfoEntity taskInfoEntity = TaskInfoEntity.builder()
                .id(taskId)
                .description("description")
                .definitionName("definitionName")
                .instanceId(instanceId)
                .formKey("formKey")
                .authorities(List.of(TaskAuthorityEntity.builder()
                        .id(UUID.randomUUID().toString())
                        .type("user")
                        .value(user)
                        .build()))
                .build();
        when(taskInfoRepository.findByAssigneeOrAuthorities_typeAndAuthorities_value(user, "user", user))
                .thenReturn(List.of(taskInfoEntity));

        List<Task> tasks = this.taskOutPort.getTasksForUser(user);

        assertThat(tasks)
                .hasSize(1)
                .first()
                .hasFieldOrPropertyWithValue("id", taskId)
                .hasFieldOrPropertyWithValue("name", "testTask")
                .hasFieldOrPropertyWithValue("description", "description")
                .hasFieldOrPropertyWithValue("processName", "definitionName")
                .hasFieldOrPropertyWithValue("processInstanceId", instanceId)
                .hasFieldOrPropertyWithValue("assignee", null)
                .hasFieldOrPropertyWithValue("candidateUsers", List.of(user))
                .hasFieldOrPropertyWithValue("formKey", "formKey");

        // assignee
        taskInfoEntity.setAssignee(user);
        when(taskInfoRepository.findByAssigneeOrAuthorities_typeAndAuthorities_value(user, "user", user))
                .thenReturn(List.of(taskInfoEntity));

        tasks = this.taskOutPort.getTasksForUser(user);

        assertThat(tasks)
                .hasSize(1)
                .first()
                .hasFieldOrPropertyWithValue("id", taskId)
                .hasFieldOrPropertyWithValue("name", "testTask")
                .hasFieldOrPropertyWithValue("description", "description")
                .hasFieldOrPropertyWithValue("processName", "definitionName")
                .hasFieldOrPropertyWithValue("processInstanceId", instanceId)
                .hasFieldOrPropertyWithValue("assignee", user)
                .hasFieldOrPropertyWithValue("candidateUsers", List.of(user))
                .hasFieldOrPropertyWithValue("formKey", "formKey")
                .hasFieldOrPropertyWithValue("customFields", Map.of());
    }

    @Test
    void testGetTasksForUserGroup() {
        final TaskInfoEntity taskInfoEntity = TaskInfoEntity.builder()
                .id(taskId)
                .description("description")
                .definitionName("definitionName")
                .instanceId(instanceId)
                .formKey("formKey")
                .authorities(List.of(TaskAuthorityEntity.builder()
                        .id(UUID.randomUUID().toString())
                        .type("group")
                        .value(group)
                        .build()))
                .build();
        when(taskInfoRepository.findByAuthorities_typeAndAuthorities_value("group", group))
                .thenReturn(List.of(taskInfoEntity));

        final List<Task> tasks = this.taskOutPort.getTasksForUserGroup(user, group);

        assertThat(tasks)
                .hasSize(1)
                .first()
                .hasFieldOrPropertyWithValue("id", taskId)
                .hasFieldOrPropertyWithValue("name", "testTask")
                .hasFieldOrPropertyWithValue("description", "description")
                .hasFieldOrPropertyWithValue("processName", "definitionName")
                .hasFieldOrPropertyWithValue("processInstanceId", instanceId)
                .hasFieldOrPropertyWithValue("assignee", null)
                .hasFieldOrPropertyWithValue("candidateGroups", List.of(group))
                .hasFieldOrPropertyWithValue("formKey", "formKey")
                .hasFieldOrPropertyWithValue("customFields", Map.of());
    }

    @Test
    void testGetTaskById() {
        final TaskInfoEntity taskInfoEntity = TaskInfoEntity.builder()
                .id(taskId)
                .description("description")
                .definitionName("definitionName")
                .instanceId(instanceId)
                .formKey("formKey")
                .authorities(List.of(TaskAuthorityEntity.builder()
                        .id(UUID.randomUUID().toString())
                        .type("group")
                        .value(group)
                        .build()))
                .build();
        taskInfoEntity.setCustomFields(List.of(TaskCustomFieldEntity.builder()
                .id(UUID.randomUUID().toString())
                .key("key")
                .value("value")
                .taskInfo(taskInfoEntity)
                .build()));
        when(taskInfoRepository.findById(taskId))
                .thenReturn(Optional.ofNullable(taskInfoEntity));

        final Task task = this.taskOutPort.getTask(taskId);

        assertThat(task)
                .hasFieldOrPropertyWithValue("id", taskId)
                .hasFieldOrPropertyWithValue("name", "testTask")
                .hasFieldOrPropertyWithValue("description", "description")
                .hasFieldOrPropertyWithValue("processName", "definitionName")
                .hasFieldOrPropertyWithValue("processInstanceId", instanceId)
                .hasFieldOrPropertyWithValue("assignee", null)
                .hasFieldOrPropertyWithValue("candidateGroups", List.of(group))
                .hasFieldOrPropertyWithValue("formKey", "formKey")
                .hasFieldOrPropertyWithValue("customFields", Map.of("key", "value"));
    }

    @Test
    void testGetTasksById_ThrowsTaskNotFoundException() {
        when(taskInfoRepository.findById(taskId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> this.taskOutPort.getTask(taskId))
            .isInstanceOf(TaskNotFoundException.class)
            .hasMessage("Task with id " + taskId + " is not found");
    }

    @Test
    void testGetTaskData() {
        assertThatThrownBy(() -> taskOutPort.getTaskData(user, taskId))
            .isInstanceOf(NotImplementedException.class)
            .hasMessage("Not implemented");
    }

    @Test
    void testCreateTask() {
        final TaskInfo taskInfo = TaskInfo.builder()
                .id(taskId)
                .description("description")
                .definitionName("definitionName")
                .instanceId(instanceId)
                .formKey("formKey")
                .authorities(List.of(TaskAuthorities.builder()
                        .id(UUID.randomUUID().toString())
                        .type("group")
                        .value(group)
                        .build()))
                .customFields(List.of(TaskCustomFields.builder()
                        .key("key")
                        .value("value")
                        .build()))
                .build();

        taskOutPort.createTask(taskInfo);

        final ArgumentCaptor<TaskInfoEntity> captor = ArgumentCaptor.forClass(TaskInfoEntity.class);
        verify(taskInfoRepository).save(captor.capture());

        final TaskInfoEntity capturedEntity = captor.getValue();
        assertThat(capturedEntity)
                .hasFieldOrPropertyWithValue("id", taskId)
                .hasFieldOrPropertyWithValue("description", "description")
                .hasFieldOrPropertyWithValue("definitionName", "definitionName")
                .hasFieldOrPropertyWithValue("instanceId", instanceId)
                .hasFieldOrPropertyWithValue("assignee", null)
                .hasFieldOrPropertyWithValue("formKey", "formKey");
        assertThat(capturedEntity.getAuthorities())
                .hasSize(1)
                .first()
                .hasFieldOrProperty("id")
                .hasFieldOrPropertyWithValue("type", "group")
                .hasFieldOrPropertyWithValue("value", group);
        assertThat(capturedEntity.getCustomFields())
                .hasSize(1)
                .first()
                .hasFieldOrProperty("id")
                .hasFieldOrPropertyWithValue("key", "key")
                .hasFieldOrPropertyWithValue("value", "value");
    }

    @Test
    void testAssignTask() {
        final TaskInfoEntity taskInfoEntity = TaskInfoEntity.builder()
                .id(taskId)
                .description("description")
                .definitionName("definitionName")
                .instanceId(instanceId)
                .formKey("formKey")
                .build();
        when(taskInfoRepository.findById(taskId))
                .thenReturn(Optional.ofNullable(taskInfoEntity));

        taskOutPort.assignTask(taskId, user);

        final ArgumentCaptor<TaskInfoEntity> captor = ArgumentCaptor.forClass(TaskInfoEntity.class);
        verify(taskInfoRepository).save(captor.capture());

        final TaskInfoEntity capturedEntity = captor.getValue();
        assertThat(capturedEntity)
                .hasFieldOrPropertyWithValue("id", taskId)
                .hasFieldOrPropertyWithValue("description", "description")
                .hasFieldOrPropertyWithValue("definitionName", "definitionName")
                .hasFieldOrPropertyWithValue("instanceId", instanceId)
                .hasFieldOrPropertyWithValue("assignee", user)
                .hasFieldOrPropertyWithValue("formKey", "formKey");
    }

    @Test
    void testAssignTask_ThrowsTaskNotFoundException() {
        when(taskInfoRepository.findById(taskId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> this.taskOutPort.assignTask(taskId, user))
                .isInstanceOf(TaskNotFoundException.class)
                .hasMessage("Task with id " + taskId + " is not found");
    }

    @Test
    void testDeleteTask() {
        taskOutPort.deleteTask(taskId);
        verify(taskInfoRepository).deleteById(taskId);
    }

}
