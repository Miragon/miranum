package io.miragon.miranum.platform.tasklist.application.service;

import io.holunda.camunda.bpm.data.factory.VariableFactory;
import io.miragon.miranum.platform.engine.application.port.out.process.MiranumProcessDefinitionPort;
import io.miragon.miranum.platform.engine.domain.process.MiranumProcessDefinition;
import io.miragon.miranum.platform.tasklist.TaskProperties;
import io.miragon.miranum.platform.tasklist.application.port.in.TaskInfoUseCase;
import io.miragon.miranum.platform.tasklist.application.port.out.task.TaskOutPort;
import io.miragon.miranum.platform.tasklist.domain.TaskAuthorities;
import io.miragon.miranum.platform.tasklist.domain.TaskCustomFields;
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
    private final TaskProperties properties;

    public static final String TASK_DESCRIPTION = "miranum_task_description";
    public static final String TASK_FORM = "miranum_task_form";
    public static final VariableFactory<String> TASK_DESCRIPTION_VARIABLE = stringVariable(TASK_DESCRIPTION);
    public static final VariableFactory<String> TASK_SCHEMA_VARIABLE = stringVariable(TASK_FORM);

    @Override
    public void createTask(DelegateTask task) {
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

        // get custom fields
        final List<String> customFieldsVariables = task.getVariableNames()
                .stream()
                .toList()
                .stream()
                .filter(variable -> variable.startsWith(properties.getCustomFieldsPrefix()) && !variable.equals(TASK_DESCRIPTION) && !variable.equals(TASK_FORM))
                .toList();
        for (final String variableName : customFieldsVariables) {
            final String variableValue = stringVariable(variableName).from(task).getLocalOrNull();
            if (variableValue != null) {
                TaskCustomFields.builder()
                        .key(variableName)
                        .value(variableValue)
                        .build();
            }
        }

        final List<TaskCustomFields> customFields = customFieldsVariables.stream()
                .map(variable -> TaskCustomFields.builder()
                        .key(variable)
                        .value(stringVariable(variable).from(task).getOrDefault(""))
                        .build())
                .toList();

        final TaskInfo taskInfo = TaskInfo.builder()
            .id(task.getId())
            .description(TASK_DESCRIPTION_VARIABLE.from(task).getOrDefault(""))
            .definitionName(miranumProcessDefinition.getName())
            .instanceId(task.getProcessInstanceId())
            .assignee(task.getAssignee())
            .authorities(authorities)
            .formKey(TASK_SCHEMA_VARIABLE.from(task).getOrDefault(""))
            .customFields(customFields)
            .build();
        this.taskOutPort.createTask(taskInfo);
    }

    @Override
    public void assignTask(final String taskId, final String assignee) {
        this.taskOutPort.assignTask(taskId, assignee);
    }

    @Override
    public void deleteTask(String taskId) {
        this.taskOutPort.deleteTask(taskId);
    }
}
