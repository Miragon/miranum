package io.miragon.miranum.platform.example.application;

import io.holunda.camunda.bpm.data.factory.VariableFactory;
import io.miragon.miranum.platform.engine.application.port.out.process.MiranumProcessDefinitionPort;
import io.miragon.miranum.platform.engine.domain.process.MiranumProcessDefinition;
import io.miragon.miranum.platform.example.application.in.task.TaskInfoInPort;
import io.miragon.miranum.platform.example.application.out.task.TaskInfoOutPort;
import io.miragon.miranum.platform.example.domain.task.TaskInfo;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.springframework.stereotype.Component;

import java.util.List;

import static io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable;

@Component
@RequiredArgsConstructor
public class TaskInfoUseCase implements TaskInfoInPort {

    private final TaskInfoOutPort taskInfoOutPort;
    private final MiranumProcessDefinitionPort miranumProcessDefinitionPort;

    public static final String TASK_DESCRIPTION = "app_task_description";
    public static final String TASK_SCHEMA = "app_task_form";
    public static final VariableFactory<String> TASK_DESCRIPTION_VARIABLE = stringVariable(TASK_DESCRIPTION);
    public static final VariableFactory<String> TASK_SCHEMA_VARIABLE = stringVariable(TASK_SCHEMA);

    @Override
    public TaskInfo getTaskInfo(final String taskId) {
        return this.taskInfoOutPort.getTaskInfo(taskId)
                .orElseThrow(() -> new IllegalArgumentException("TaskInfo with id " + taskId + " not found."));
    }

    @Override
    public List<TaskInfo> getTaskInfos(List<String> taskIds) {
        return this.taskInfoOutPort.getTaskInfos(taskIds);
    }

    @Override
    public void createTaskInfo(DelegateTask task) {
        final MiranumProcessDefinition miranumProcessDefinition = this.miranumProcessDefinitionPort.getProcessDefinitionById(task.getProcessDefinitionId());
        final TaskInfo taskInfo = TaskInfo.builder()
                .id(task.getId())
                .description(TASK_DESCRIPTION_VARIABLE.from(task).getOrDefault(""))
                .definitionName(miranumProcessDefinition.getName())
                .instanceId(task.getProcessInstanceId())
                .assignee(task.getAssignee())
                .form(TASK_SCHEMA_VARIABLE.from(task).getOrDefault(""))
                .build();
        this.taskInfoOutPort.createTaskInfo(taskInfo);
    }

    @Override
    public void updateTaskInfo(String taskId, DelegateTask task) {
        final TaskInfo taskInfo = this.taskInfoOutPort.getTaskInfo(taskId)
                .orElseThrow(() -> new IllegalArgumentException("TaskInfo with id " + taskId + " not found."));;
        taskInfo.setAssignee(task.getAssignee());
        this.taskInfoOutPort.updateTaskInfo(taskInfo);
    }

    @Override
    public void deleteTaskInfo(String taskId) {
        this.taskInfoOutPort.deleteTaskInfo(taskId);
    }
}
