package io.miragon.miranum.platform.tasklist.adapter.out.task;

import io.miragon.miranum.platform.tasklist.adapter.out.task.taskinfo.TaskAuthorityEntity;
import io.miragon.miranum.platform.tasklist.adapter.out.task.taskinfo.TaskCustomFieldEntity;
import io.miragon.miranum.platform.tasklist.adapter.out.task.taskinfo.TaskInfoEntity;
import io.miragon.miranum.platform.tasklist.domain.Task;
import io.miragon.miranum.platform.tasklist.domain.TaskAuthorities;
import io.miragon.miranum.platform.tasklist.domain.TaskCustomFields;
import io.miragon.miranum.platform.tasklist.domain.TaskInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TaskMapperTest {

    private TaskMapper taskMapper;

    @BeforeEach
    void setUp() {
        taskMapper = Mappers.getMapper(TaskMapper.class);
    }

    @Test
    void testMapToTask() {
        final String taskId = UUID.randomUUID().toString();
        final org.camunda.bpm.engine.task.Task camundaTask = mock(org.camunda.bpm.engine.task.Task.class);
        when(camundaTask.getId()).thenReturn(taskId);
        when(camundaTask.getName()).thenReturn("testTask");

        final TaskInfo taskInfo = TaskInfo.builder()
                .id(taskId)
                .description("description")
                .definitionName("definitionName")
                .instanceId("instanceId")
                .assignee("assignee")
                .formKey("formKey")
                .customFields(List.of(TaskCustomFields.builder().key("key").value("value").build()))
                .build();

        final Task task = taskMapper.mapToTask(camundaTask, taskInfo);

        assertThat(task)
                .hasFieldOrPropertyWithValue("id", taskId)
                .hasFieldOrPropertyWithValue("name", "testTask")
                .hasFieldOrPropertyWithValue("description", "description")
                .hasFieldOrPropertyWithValue("processName", "definitionName")
                .hasFieldOrPropertyWithValue("processInstanceId", "instanceId")
                .hasFieldOrPropertyWithValue("assignee", "assignee")
                .hasFieldOrPropertyWithValue("formKey", "formKey")
                .hasFieldOrPropertyWithValue("customFields", Map.of("key", "value"));
    }

    @Test
    void testMapToTaskInfos() {
        final TaskInfoEntity taskInfoEntity = TaskInfoEntity.builder()
                .id(UUID.randomUUID().toString())
                .description("description")
                .definitionName("definitionName")
                .instanceId("instanceId")
                .assignee("assignee")
                .formKey("formKey")
                .authorities(List.of(TaskAuthorityEntity.builder().id(UUID.randomUUID().toString()).type("type").value("value").build()))
                .customFields(List.of(TaskCustomFieldEntity.builder().id(UUID.randomUUID().toString()).key("key").value("value").build()))
                .build();

        final List<TaskInfo> taskInfos = taskMapper.mapToTaskInfos(List.of(taskInfoEntity));

        assertThat(taskInfos)
                .hasSize(1)
                .first()
                .hasFieldOrPropertyWithValue("id", taskInfoEntity.getId())
                .hasFieldOrPropertyWithValue("description", "description")
                .hasFieldOrPropertyWithValue("definitionName", "definitionName")
                .hasFieldOrPropertyWithValue("instanceId", "instanceId")
                .hasFieldOrPropertyWithValue("assignee", "assignee")
                .hasFieldOrPropertyWithValue("formKey", "formKey")
                .hasFieldOrPropertyWithValue("authorities", List.of(TaskAuthorities.builder().id(taskInfoEntity.getAuthorities().get(0).getId()).type("type").value("value").build()))
                .hasFieldOrPropertyWithValue("customFields", List.of(TaskCustomFields.builder().id(taskInfoEntity.getCustomFields().get(0).getId()).key("key").value("value").build()));
    }

    @Test
    void testMapToTaskInfo() {
        final TaskInfoEntity taskInfoEntity = TaskInfoEntity.builder()
                .id(UUID.randomUUID().toString())
                .description("description")
                .definitionName("definitionName")
                .instanceId("instanceId")
                .assignee("assignee")
                .formKey("formKey")
                .authorities(List.of(TaskAuthorityEntity.builder().id(UUID.randomUUID().toString()).type("type").value("value").build()))
                .customFields(List.of(TaskCustomFieldEntity.builder().id(UUID.randomUUID().toString()).key("key").value("value").build()))
                .build();

        final TaskInfo taskInfo = taskMapper.mapToTaskInfo(taskInfoEntity);

        assertThat(taskInfo)
                .hasFieldOrPropertyWithValue("id", taskInfoEntity.getId())
                .hasFieldOrPropertyWithValue("description", "description")
                .hasFieldOrPropertyWithValue("definitionName", "definitionName")
                .hasFieldOrPropertyWithValue("instanceId", "instanceId")
                .hasFieldOrPropertyWithValue("assignee", "assignee")
                .hasFieldOrPropertyWithValue("formKey", "formKey")
                .hasFieldOrPropertyWithValue("authorities", List.of(TaskAuthorities.builder().id(taskInfoEntity.getAuthorities().get(0).getId()).type("type").value("value").build()))
                .hasFieldOrPropertyWithValue("customFields", List.of(TaskCustomFields.builder().id(taskInfoEntity.getCustomFields().get(0).getId()).key("key").value("value").build()));
    }

    @Test
    void testMapToTaskInfoEntity() {
        final TaskInfo taskInfo = TaskInfo.builder()
                .id(UUID.randomUUID().toString())
                .description("description")
                .definitionName("definitionName")
                .instanceId("instanceId")
                .assignee("assignee")
                .formKey("formKey")
                .authorities(List.of(TaskAuthorities.builder().id(UUID.randomUUID().toString()).type("type").value("value").build()))
                .customFields(List.of(TaskCustomFields.builder().id(UUID.randomUUID().toString()).key("key").value("value").build()))
                .build();

        final TaskInfoEntity taskInfoEntity = taskMapper.mapToTaskInfoEntity(taskInfo);

        assertThat(taskInfoEntity)
                .hasFieldOrPropertyWithValue("id", taskInfo.getId())
                .hasFieldOrPropertyWithValue("description", "description")
                .hasFieldOrPropertyWithValue("definitionName", "definitionName")
                .hasFieldOrPropertyWithValue("instanceId", "instanceId")
                .hasFieldOrPropertyWithValue("assignee", "assignee")
                .hasFieldOrPropertyWithValue("formKey", "formKey");

        assertThat(taskInfoEntity.getAuthorities())
                .hasSize(1)
                .first()
                .hasFieldOrPropertyWithValue("id", taskInfo.getAuthorities().get(0).getId())
                .hasFieldOrPropertyWithValue("type", "type")
                .hasFieldOrPropertyWithValue("value", "value")
                .hasFieldOrPropertyWithValue("taskInfo", taskInfoEntity);
        assertThat(taskInfoEntity.getCustomFields())
                .hasSize(1)
                .first()
                .hasFieldOrPropertyWithValue("id", taskInfo.getCustomFields().get(0).getId())
                .hasFieldOrPropertyWithValue("key", "key")
                .hasFieldOrPropertyWithValue("value", "value")
                .hasFieldOrPropertyWithValue("taskInfo", taskInfoEntity);
    }


}
