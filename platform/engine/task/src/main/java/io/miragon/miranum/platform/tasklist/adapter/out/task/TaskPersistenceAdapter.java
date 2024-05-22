package io.miragon.miranum.platform.tasklist.adapter.out.task;

import io.miragon.miranum.platform.tasklist.adapter.out.task.taskinfo.TaskInfoRepository;
import io.miragon.miranum.platform.tasklist.application.port.out.engine.TaskOutPort;
import io.miragon.miranum.platform.tasklist.domain.Task;
import io.miragon.miranum.platform.tasklist.domain.TaskInfo;
import io.miragon.miranum.platform.tasklist.exception.TaskAccessDeniedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.camunda.bpm.engine.TaskService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class TaskPersistenceAdapter implements TaskOutPort {

    private final TaskService taskService;
    private final TaskInfoRepository taskInfoRepository;
    private final TaskMapper taskMapper;

    @Override
    public List<Task> getTasksForUser(String user) {
        final List<TaskInfo> taskInfos = this.taskMapper.mapToTaskInfos(this.taskInfoRepository.findByAssignee(user));
        return this.getUserTasks(taskInfos);
    }

    @Override
    public List<Task> getTasksForUserGroup(String user, String group) {
        final List<TaskInfo> taskInfos = this.taskMapper.mapToTaskInfos(this.taskInfoRepository.findByCandidateGroups(group));
        return this.getUserTasks(taskInfos);
    }

    @Override
    public Task getTask(String taskId) {
        final TaskInfo taskInfo = this.taskInfoRepository.findById(taskId)
                .map(this.taskMapper::mapToTaskInfo)
                .orElseThrow(() -> new TaskAccessDeniedException("Task with id " + taskId + " is not found"));
        return this.getUserTask(taskInfo);
    }

    @Override
    public Map<String, Object> getTaskData(String user, String taskId) {
        // TODO: Do we need this method?
        throw new NotImplementedException("Not implemented");
    }

    @Override
    public void createTaskInfo(final TaskInfo task) {
        this.taskInfoRepository.save(this.taskMapper.mapToTaskInfoEntity(task));
    }

    @Override
    public void updateTaskInfo(final TaskInfo task) {
        this.taskInfoRepository.save(this.taskMapper.mapToTaskInfoEntity(task));
    }

    @Override
    public void deleteTaskInfo(String taskId) {
        this.taskInfoRepository.deleteById(taskId);
    }

    private Task getUserTask(final TaskInfo taskInfo) {
        final org.camunda.bpm.engine.task.Task camundaTask = this.taskService.createTaskQuery()
                .taskId(taskInfo.getId())
                .singleResult();
        return this.taskMapper.mapToTask(camundaTask, taskInfo);
    }

    private List<Task> getUserTasks(final List<TaskInfo> tasks) {
        if (tasks.isEmpty()) {
            return List.of();
        }

        final List<String> userTaskIds = tasks.stream()
                .map(TaskInfo::getId)
                .toList();
        final Map<String, org.camunda.bpm.engine.task.Task> camundaTasks = this.taskService.createTaskQuery()
                .taskIdIn(String.valueOf(userTaskIds))
                .list()
                .stream()
                .collect(Collectors.toMap(org.camunda.bpm.engine.task.Task::getId, task -> task));

        return tasks.stream()
                .map(task -> this.taskMapper.mapToTask(camundaTasks.get(task.getId()), task))
                .toList();
    }
}
