package io.miragon.miranum.platform.tasklist.adapter.in.task;

import io.miragon.miranum.platform.tasklist.application.port.in.TaskInfoUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class TaskInfoListener {

    private final TaskInfoUseCase taskInfoInPort;

    @EventListener
    public void taskInfoListeners(final DelegateTask delegateTask) {
        switch (delegateTask.getEventName()) {
            case "create":
                log.debug("TaskInfo Listener: {}, Event: {}", delegateTask.getName(), delegateTask.getEventName());
                this.taskInfoInPort.createTask(delegateTask);
                break;
            case "assignment":
                log.debug("TaskInfo Listener: {}, Event: {}", delegateTask.getName(), delegateTask.getEventName());
                this.taskInfoInPort.assignTask(delegateTask.getId(), delegateTask.getAssignee());
                break;
            case "complete":
            case "delete":
                log.debug("TaskInfo Listener: {}, Event: {}", delegateTask.getName(), delegateTask.getEventName());
                this.taskInfoInPort.deleteTask(delegateTask.getId());
                break;
        }
    }
}
