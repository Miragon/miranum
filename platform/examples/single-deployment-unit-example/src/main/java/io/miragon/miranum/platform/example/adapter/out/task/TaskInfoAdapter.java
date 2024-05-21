package io.miragon.miranum.platform.example.adapter.out.task;

import io.miragon.miranum.platform.example.adapter.out.task.taskinfo.TaskInfoRepository;
import io.miragon.miranum.platform.example.application.out.task.TaskInfoOutPort;
import io.miragon.miranum.platform.example.domain.task.TaskInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TaskInfoAdapter implements TaskInfoOutPort {

    private final TaskInfoRepository taskInfoRepository;
    private final TaskMapper taskMapper;

    @Override
    public List<TaskInfo> getTaskInfos(List<String> taskIds) {
        return this.taskMapper.mapToTaskInfos(this.taskInfoRepository.findAllById(taskIds));
    }

    @Override
    public Optional<TaskInfo> getTaskInfo(String taskId) {
        return this.taskInfoRepository.findById(taskId).map(this.taskMapper::mapToTaskInfo);
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

}
