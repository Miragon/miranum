package io.miragon.miranum.platform.tasklist.application.service;

import io.holunda.camunda.bpm.data.factory.VariableFactory;
import io.miragon.miranum.platform.engine.application.port.out.process.MiranumProcessDefinitionPort;
import io.miragon.miranum.platform.engine.domain.process.MiranumProcessDefinition;
import io.miragon.miranum.platform.tasklist.application.port.in.TaskInfoUseCase;
import io.miragon.miranum.platform.tasklist.application.port.out.engine.TaskOutPort;
import io.miragon.miranum.platform.tasklist.domain.TaskInfo;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.task.IdentityLink;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

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
        final String candidateGroups = task.getCandidates().stream()
                .filter(candidate -> candidate.getType().equals("candidate") || candidate.getGroupId() != null)
                .map(IdentityLink::getGroupId)
                // filter out empty strings and make sure that null values are actually null
                .collect(Collectors.collectingAndThen(Collectors.joining(","), result -> result.isBlank() || result.equals("null") ? null : result));
        final String candidateUsers = task.getCandidates().stream()
                .filter(candidate -> candidate.getType().equals("candidate") || candidate.getUserId() != null)
                .map(IdentityLink::getUserId)
                // filter out empty strings and make sure that null values are actually null
                .collect(Collectors.collectingAndThen(Collectors.joining(","), result -> result.isBlank() || result.equals("null") ? null : result));

        final TaskInfo taskInfo = TaskInfo.builder()
                .id(task.getId())
                .description(TASK_DESCRIPTION_VARIABLE.from(task).getOrDefault(""))
                .definitionName(miranumProcessDefinition.getName())
                .instanceId(task.getProcessInstanceId())
                .assignee(task.getAssignee())
                .candidateGroups(candidateGroups)
                .candidateUsers(candidateUsers)
                .form(TASK_SCHEMA_VARIABLE.from(task).getOrDefault(""))
                .build();
        this.taskOutPort.createTaskInfo(taskInfo);
    }

    @Override
    public void deleteTaskInfo(String taskId) {
        this.taskOutPort.deleteTaskInfo(taskId);
    }
}
