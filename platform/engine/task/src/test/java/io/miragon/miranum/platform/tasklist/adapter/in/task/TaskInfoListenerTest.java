package io.miragon.miranum.platform.tasklist.adapter.in.task;

import io.miragon.miranum.platform.tasklist.application.port.in.TaskInfoUseCase;
import io.miragon.miranum.platform.tasklist.application.service.TaskInfoService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.mockito.Mockito.*;

class TaskInfoListenerTest {

    private final TaskInfoUseCase taskInfoService = mock(TaskInfoService.class);
    private final TaskInfoListener taskInfoListener = new TaskInfoListener(taskInfoService);

    private final DelegateTask delegateTask = mock(DelegateTask.class);

    @BeforeEach
    void setUp() {
        when(delegateTask.getId()).thenReturn("task123");
        when(delegateTask.getProcessDefinitionId()).thenReturn("processDef123");
        when(delegateTask.getProcessInstanceId()).thenReturn("instance123");
        when(delegateTask.getAssignee()).thenReturn("user123");
        when(delegateTask.getCandidates()).thenReturn(Set.of());
    }

    @Test
    void testCreateTask() throws Exception {
        when(delegateTask.getEventName()).thenReturn("create");
        taskInfoListener.taskInfoListeners(delegateTask);
        verify(taskInfoService).createTask(delegateTask);
    }

    @Test
    void testAssignTask() throws Exception {
        when(delegateTask.getEventName()).thenReturn("assignment");
        taskInfoListener.taskInfoListeners(delegateTask);
        verify(taskInfoService).assignTask(delegateTask.getId(), delegateTask.getAssignee());
    }

    @Test
    void testDeleteTask() throws Exception {
        when(delegateTask.getEventName()).thenReturn("complete");
        taskInfoListener.taskInfoListeners(delegateTask);
        verify(taskInfoService).deleteTask(delegateTask.getId());
    }

}
