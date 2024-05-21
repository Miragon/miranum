package io.miragon.miranum.platform.example.adapter.out.task;

import io.miragon.miranum.platform.example.application.in.task.TaskInfoInPort;
import io.miragon.miranum.platform.example.domain.task.TaskInfo;
import io.miragon.miranum.platform.tasklist.application.port.out.engine.TaskCommandPort;
import io.miragon.miranum.platform.tasklist.application.port.out.engine.TaskOutPort;
import io.miragon.miranum.platform.tasklist.domain.Task;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.NotImplementedException;
import org.camunda.bpm.engine.TaskService;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class Camunda7TaskAdapter implements TaskOutPort, TaskCommandPort {

    public static final String DEFAULT_TASK_CANCELLATION_ERROR = "default_error_code";

    private final TaskService taskService;
    private final TaskMapper taskMapper;
    private final TaskInfoInPort taskInfoInPort;

    @Override
    public List<Task> getTasksForUser(String user) {
        final List<org.camunda.bpm.engine.task.Task> tasks = this.taskService.createTaskQuery()
                .taskAssignee(user)
                .list();
        return this.getUserTasks(tasks);
    }

    @Override
    public List<Task> getTasksForUserGroup(String user, String group) {
        final List<org.camunda.bpm.engine.task.Task> tasks = this.taskService.createTaskQuery()
                .taskCandidateGroup(group)
                .includeAssignedTasks()
                .list();
        // Set the candidate group for each task
        return this.getUserTasks(tasks)
                .stream()
                .peek(task -> task.setCandidateGroups(group))
                .toList();
    }

    @Override
    public Task getTask(String user, String taskId) {
        final org.camunda.bpm.engine.task.Task task = this.taskService.createTaskQuery()
                .taskAssignee(user)
                .taskId(taskId)
                .singleResult();
        if (task == null) {
            throw new RuntimeException("Task with id " + taskId + " is not found or not assigned to user " + user);
        }
        return this.getUserTask(task);
    }

    @Override
    public Map<String, Object> getTaskData(String user, String taskId) {
        // TODO: Do we need this method?
        throw new NotImplementedException("Not implemented");
    }

    @Override
    public void completeUserTask(String taskId, Map<String, Object> variables) {
        taskService.complete(taskId, variables);
    }

    @Override
    public void saveUserTask(String taskId, Map<String, Object> payload) {
        // setVariables does not trigger update event
        taskService.setVariables(taskId, payload);
        // fetch task and save it to trigger update event
        val task = taskService.createTaskQuery().taskId(taskId).singleResult();
        taskService.saveTask(task);
    }

    @Override
    public void assignUserTask(String taskId, String assignee) {
        taskService.setAssignee(taskId, assignee);
    }

    @Override
    public void unassignUserTask(String taskId) {
        taskService.setAssignee(taskId, null);
    }

    @Override
    public void deferUserTask(String taskId, Instant followUpDate) {
        var task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task != null) {
            task.setFollowUpDate(Date.from(followUpDate.truncatedTo(ChronoUnit.DAYS)));
            taskService.saveTask(task);
        }
    }

    @Override
    public void undeferUserTask(String taskId) {
        var task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task != null) {
            task.setFollowUpDate(null);
            taskService.saveTask(task);
        }
    }

    @Override
    public void cancelUserTask(String taskId) {
        taskService.handleBpmnError(taskId, DEFAULT_TASK_CANCELLATION_ERROR);
    }

    private Task getUserTask(final org.camunda.bpm.engine.task.Task task) {
        final TaskInfo taskInfo = this.taskInfoInPort.getTaskInfo(task.getId());
        return this.taskMapper.mapToTask(task, taskInfo);
    }

    private List<Task> getUserTasks(final List<org.camunda.bpm.engine.task.Task> tasks) {
        if (tasks.isEmpty()) {
            return List.of();
        }

        final List<String> userTaskIds = tasks.stream()
                .map(org.camunda.bpm.engine.task.Task::getId)
                .toList();
        final Map<String, TaskInfo> taskInfos = this.taskInfoInPort.getTaskInfos(userTaskIds)
                .stream()
                .collect(Collectors.toMap(TaskInfo::getId, taskInfo -> taskInfo));

        // If a camunda task does not exist in the dwf task info table log an error
        userTaskIds.stream()
                .filter(taskId -> !taskInfos.containsKey(taskId))
                .forEach(taskId -> log.error("Task with id {} is missing in TaskInfo database table", taskId));

        return tasks.stream()
                .filter(task -> taskInfos.containsKey(task.getId()))
                .map(task -> this.taskMapper.mapToTask(task, taskInfos.get(task.getId())))
                .toList();
    }
}
