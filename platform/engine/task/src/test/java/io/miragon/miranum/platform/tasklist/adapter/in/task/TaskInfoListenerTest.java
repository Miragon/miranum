package io.miragon.miranum.platform.tasklist.adapter.in.task;

import io.miragon.miranum.platform.tasklist.TaskProperties;
import io.miragon.miranum.platform.tasklist.application.port.in.TaskInfoUseCase;
import io.miragon.miranum.platform.tasklist.application.port.in.TaskNotificationUseCase;
import io.miragon.miranum.platform.tasklist.application.service.TaskInfoService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;

class TaskInfoListenerTest {

    private final TaskInfoUseCase taskInfoService = mock(TaskInfoService.class);
    private final TaskNotificationUseCase taskNotificationUseCase = mock(TaskNotificationUseCase.class);
    private final TaskProperties taskProperties = mock(TaskProperties.class);
    private final TaskListener taskInfoListener = new TaskListener(taskInfoService, taskNotificationUseCase, taskProperties);

    private final DelegateTask delegateTask = mock(DelegateTask.class);

    @BeforeEach
    void setUp() {
        when(delegateTask.getId()).thenReturn("task123");
        when(delegateTask.getProcessDefinitionId()).thenReturn("processDef123");
        when(delegateTask.getProcessInstanceId()).thenReturn("instance123");
        when(delegateTask.getAssignee()).thenReturn("user123");
        when(delegateTask.getCandidates()).thenReturn(Set.of());
        when(taskProperties.isNotificationsEnabled()).thenReturn(false);
    }

    @Test
    void testCreateTask() {
        when(delegateTask.getEventName()).thenReturn("create");

        taskInfoListener.taskListeners(delegateTask);

        verify(taskInfoService).createTask(delegateTask);
        verify(taskNotificationUseCase, never()).notifyUsers(delegateTask);
    }

    @Test
    void testAssignTask() {
        when(delegateTask.getEventName()).thenReturn("assignment");

        taskInfoListener.taskListeners(delegateTask);

        verify(taskInfoService).assignTask(delegateTask.getId(), delegateTask.getAssignee());
    }

    @Test
    void testDeleteTask() {
        when(delegateTask.getEventName()).thenReturn("complete");

        taskInfoListener.taskListeners(delegateTask);

        verify(taskInfoService).deleteTask(delegateTask.getId());
    }

    @Test
    void testTaskNotifiesUsers() {
        List.of("create", "assignment", "complete", "delete").forEach(eventName -> {
            when(delegateTask.getEventName()).thenReturn(eventName);
            when(taskProperties.isNotificationsEnabled()).thenReturn(true);

            taskInfoListener.taskListeners(delegateTask);
        });
        verify(taskNotificationUseCase, times(4)).notifyUsers(eq(delegateTask));
    }

}
