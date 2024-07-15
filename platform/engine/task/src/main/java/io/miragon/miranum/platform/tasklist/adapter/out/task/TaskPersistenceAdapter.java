package io.miragon.miranum.platform.tasklist.adapter.out.task;

import io.miragon.miranum.platform.tasklist.adapter.out.task.taskinfo.TaskInfoEntity;
import io.miragon.miranum.platform.tasklist.adapter.out.task.taskinfo.TaskInfoRepository;
import io.miragon.miranum.platform.tasklist.application.port.out.task.TaskOutPort;
import io.miragon.miranum.platform.tasklist.domain.Task;
import io.miragon.miranum.platform.tasklist.domain.TaskInfo;
import io.miragon.miranum.platform.tasklist.exception.TaskNotFoundException;
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
        final List<TaskInfo> taskInfos = this.taskMapper.mapToTaskInfos(
                this.taskInfoRepository.findByAssigneeOrAuthorities_typeAndAuthorities_value(user, "user", user)
        );
        return this.getUserTasks(taskInfos);
    }

    @Override
    public List<Task> getTasksForUserGroup(String user, String group) {
        final List<TaskInfo> taskInfos = this.taskMapper.mapToTaskInfos(
                this.taskInfoRepository.findByAuthorities_typeAndAuthorities_value("group", group)
        );
        return this.getUserTasks(taskInfos);
    }

    @Override
    public Task getTask(String taskId) throws TaskNotFoundException {
        final TaskInfo taskInfo = this.taskInfoRepository.findById(taskId)
                .map(this.taskMapper::mapToTaskInfo)
                .orElseThrow(() -> new TaskNotFoundException("Task with id " + taskId + " is not found"));
        return this.getUserTask(taskInfo);
    }

    @Override
    public Map<String, Object> getTaskData(String user, String taskId) {
        throw new NotImplementedException("Not implemented");
    }

    @Override
    public void createTask(final TaskInfo task) {
        final TaskInfoEntity taskInfoEntity = this.taskMapper.mapToTaskInfoEntity(task);
        this.taskInfoRepository.save(taskInfoEntity);
    }

    @Override
    public void assignTask(final String taskId, final String assignee) {
        // Note: TaskAuthorityEntities are not updated here
        final TaskInfoEntity task = this.taskInfoRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task with id " + taskId + " is not found"));
        task.setAssignee(assignee);
        this.taskInfoRepository.save(task);
    }

    @Override
    public void deleteTask(String taskId) {
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
                // this is a hack to call the camunda api correctly
                .taskIdIn(userTaskIds.toArray(new String[0]))
                .list()
                .stream()
                .collect(Collectors.toMap(org.camunda.bpm.engine.task.Task::getId, task -> task));

        return tasks.stream()
                .map(task -> this.taskMapper.mapToTask(camundaTasks.get(task.getId()), task))
                .toList();
    }
}
