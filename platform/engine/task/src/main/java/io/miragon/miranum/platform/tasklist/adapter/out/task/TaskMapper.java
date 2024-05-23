package io.miragon.miranum.platform.tasklist.adapter.out.task;

import io.miragon.miranum.platform.tasklist.adapter.out.task.taskinfo.TaskInfoEntity;
import io.miragon.miranum.platform.tasklist.domain.Task;
import io.miragon.miranum.platform.tasklist.domain.TaskAuthorities;
import io.miragon.miranum.platform.tasklist.domain.TaskInfo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface TaskMapper {

    default Task mapToTask(final org.camunda.bpm.engine.task.Task camundaTask, final TaskInfo taskInfo) {
        return Task.builder()
                .id(camundaTask.getId())
                .name(camundaTask.getName())
                .description(taskInfo.getDescription())
                .processName(taskInfo.getDefinitionName() != null ? taskInfo.getDefinitionName() : "")
                .processInstanceId(taskInfo.getInstanceId())
                .assignee(camundaTask.getAssignee())
                .form(taskInfo.getForm())
                .candidateGroups(taskInfo.getCandidateGroups())
                .candidateUsers(taskInfo.getCandidateUsers())
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
                .form(entity.getForm())
                .authorities(entity.getAuthorities().stream()
                    .map(authority -> TaskAuthorities.builder()
                            .id(authority.getId())
                            .type(authority.getType())
                            .value(authority.getValue())
                            .build())
                    .toList())
                .build();
    }

    default TaskInfoEntity mapToTaskInfoEntity(final TaskInfo taskInfo) {
        return TaskInfoEntity.builder()
                .id(taskInfo.getId())
                .description(taskInfo.getDescription())
                .definitionName(taskInfo.getDefinitionName())
                .instanceId(taskInfo.getInstanceId())
                .assignee(taskInfo.getAssignee())
                .form(taskInfo.getForm())
                .build();
    }

}
