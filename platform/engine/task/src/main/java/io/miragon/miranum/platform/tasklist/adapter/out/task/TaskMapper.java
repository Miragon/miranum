package io.miragon.miranum.platform.tasklist.adapter.out.task;

import io.miragon.miranum.platform.tasklist.adapter.out.task.taskinfo.TaskInfoEntity;
import io.miragon.miranum.platform.tasklist.domain.Task;
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

    List<TaskInfo> mapToTaskInfos(final List<TaskInfoEntity> entity);

    TaskInfo mapToTaskInfo(final TaskInfoEntity entity);

    TaskInfoEntity mapToTaskInfoEntity(final TaskInfo taskInfo);

}
