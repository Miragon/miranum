package io.miragon.miranum.platform.tasklist.adapter.out.task;

import io.miragon.miranum.platform.tasklist.adapter.out.task.taskinfo.TaskAuthorityEntity;
import io.miragon.miranum.platform.tasklist.adapter.out.task.taskinfo.TaskCustomFieldEntity;
import io.miragon.miranum.platform.tasklist.adapter.out.task.taskinfo.TaskInfoEntity;
import io.miragon.miranum.platform.tasklist.domain.Task;
import io.miragon.miranum.platform.tasklist.domain.TaskAuthorities;
import io.miragon.miranum.platform.tasklist.domain.TaskCustomFields;
import io.miragon.miranum.platform.tasklist.domain.TaskInfo;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface TaskMapper {

    default Task mapToTask(final org.camunda.bpm.engine.task.Task camundaTask, final TaskInfo taskInfo) {
        return Task.builder()
                .id(camundaTask.getId())
                .name(camundaTask.getName())
                .description(taskInfo.getDescription())
                .processName(taskInfo.getDefinitionName() != null ? taskInfo.getDefinitionName() : "")
                .processInstanceId(taskInfo.getInstanceId())
                .assignee(taskInfo.getAssignee())
                .formKey(taskInfo.getFormKey())
                .candidateGroups(taskInfo.getCandidateGroups())
                .candidateUsers(taskInfo.getCandidateUsers())
                .customFields(taskInfo.getCustomFields().stream()
                        .collect(Collectors.toMap(TaskCustomFields::getKey, TaskCustomFields::getValue)))
                .build();
    }

    default List<TaskInfo> mapToTaskInfos(final List<TaskInfoEntity> entity) {
        return entity.stream()
                .map(this::mapToTaskInfo)
                .toList();
    }

    default TaskInfo mapToTaskInfo(final TaskInfoEntity entity) {
        return TaskInfo.builder()
                .id(entity.getId())
                .description(entity.getDescription())
                .definitionName(entity.getDefinitionName())
                .instanceId(entity.getInstanceId())
                .assignee(entity.getAssignee())
                .formKey(entity.getFormKey())
                .authorities(entity.getAuthorities().stream()
                    .map(authority -> TaskAuthorities.builder()
                            .id(authority.getId())
                            .type(authority.getType())
                            .value(authority.getValue())
                            .build())
                    .toList())
                .customFields(entity.getCustomFields().stream()
                    .map(customField -> TaskCustomFields.builder()
                            .id(customField.getId())
                            .key(customField.getKey())
                            .value(customField.getValue())
                            .build())
                    .toList())
                .build();
    }

    default TaskInfoEntity mapToTaskInfoEntity(final TaskInfo taskInfo) {
        final TaskInfoEntity taskInfoEntity = TaskInfoEntity.builder()
                .id(taskInfo.getId())
                .description(taskInfo.getDescription())
                .definitionName(taskInfo.getDefinitionName())
                .instanceId(taskInfo.getInstanceId())
                .assignee(taskInfo.getAssignee())
                .formKey(taskInfo.getFormKey())
                .build();
        taskInfoEntity.setAuthorities(taskInfo.getAuthorities().stream()
                .map(authority -> TaskAuthorityEntity.builder()
                        .id(authority.getId())
                        .type(authority.getType())
                        .value(authority.getValue())
                        .taskInfo(taskInfoEntity)
                        .build())
                .toList());
        taskInfoEntity.setCustomFields(taskInfo.getCustomFields().stream()
                .map(customField -> TaskCustomFieldEntity.builder()
                        .id(customField.getId())
                        .key(customField.getKey())
                        .value(customField.getValue())
                        .taskInfo(taskInfoEntity)
                        .build())
                .toList());
        return taskInfoEntity;
    }

}
