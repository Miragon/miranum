package io.miragon.miranum.platform.tasklist.application.service;

import io.holunda.camunda.bpm.data.factory.VariableFactory;
import io.miragon.miranum.platform.engine.application.port.out.process.MiranumProcessDefinitionPort;
import io.miragon.miranum.platform.engine.domain.process.MiranumProcessDefinition;
import io.miragon.miranum.platform.tasklist.application.port.in.TaskInfoUseCase;
import io.miragon.miranum.platform.tasklist.application.port.out.engine.TaskOutPort;
import io.miragon.miranum.platform.tasklist.domain.TaskAuthorities;
import io.miragon.miranum.platform.tasklist.domain.TaskInfo;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.springframework.stereotype.Component;

import java.util.List;

import static io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable;

@Component
@RequiredArgsConstructor
public class TaskInfoService implements TaskInfoUseCase {

    private final TaskOutPort taskOutPort;
    private final MiranumProcessDefinitionPort miranumProcessDefinitionPort;

    public static final String TASK_DESCRIPTION = "app_task_description";
    public static final String TASK_SCHEMA = "app_task_form";
    public static final VariableFactory<String> TASK_DESCRIPTION_VARIABLE = stringVariable(TASK_DESCRIPTION);
    public static final VariableFactory<String> TASK_SCHEMA_VARIABLE = stringVariable(TASK_SCHEMA);

    @Override
    public void createTaskInfo(DelegateTask task) {
        final MiranumProcessDefinition miranumProcessDefinition = this.miranumProcessDefinitionPort.getProcessDefinitionById(task.getProcessDefinitionId());
        // get candidate groups and users
        final List<TaskAuthorities> authorities = task.getCandidates().stream()
            .filter(candidate -> candidate.getType().equals("candidate") || candidate.getGroupId() != null)
            .map(candidate -> {
                if (candidate.getGroupId() != null) {
                    return TaskAuthorities.builder()
                        .type("group")
                        .value(candidate.getGroupId())
                        .build();
                } else if (candidate.getUserId() != null) {
                    return TaskAuthorities.builder()
                            .type("user")
                            .value(candidate.getUserId())
                            .build();
                }
                return null;
            })
            .toList();

        final TaskInfo taskInfo = TaskInfo.builder()
            .id(task.getId())
            .description(TASK_DESCRIPTION_VARIABLE.from(task).getOrDefault(""))
            .definitionName(miranumProcessDefinition.getName())
            .instanceId(task.getProcessInstanceId())
            .assignee(task.getAssignee())
            .authorities(authorities)
            .formKey(TASK_SCHEMA_VARIABLE.from(task).getOrDefault(""))
            .build();
        this.taskOutPort.createTaskInfo(taskInfo);
    }

    @Override
    public void deleteTaskInfo(String taskId) {
        this.taskOutPort.deleteTaskInfo(taskId);
    }
}
