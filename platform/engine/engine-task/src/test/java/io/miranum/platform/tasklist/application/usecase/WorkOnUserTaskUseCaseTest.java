package io.miranum.platform.tasklist.application.usecase;

import com.google.common.collect.Sets;
import io.holunda.camunda.bpm.data.CamundaBpmData;
import io.holunda.polyflow.view.auth.User;
import io.miranum.platform.tasklist.adapter.out.schema.VariableTaskSchemaResolverAdapter;
import io.miranum.platform.tasklist.adapter.out.schema.VariableTaskSchemaTypeResolverAdapter;
import io.miranum.platform.tasklist.application.port.in.WorkOnUserTask;
import io.miranum.platform.tasklist.application.port.out.cancellation.CancellationFlagOutPort;
import io.miranum.platform.tasklist.application.port.out.engine.TaskCommandPort;
import io.miranum.platform.tasklist.application.port.out.polyflow.TaskNotFoundException;
import io.miranum.platform.tasklist.application.port.out.polyflow.TaskQueryPort;
import io.miranum.platform.tasklist.application.port.out.schema.JsonSchemaPort;
import io.miranum.platform.tasklist.application.port.out.schema.JsonSchemaValidationPort;
import io.miranum.platform.tasklist.application.port.out.schema.TaskSchemaRefResolverPort;
import io.miranum.platform.tasklist.application.port.out.schema.TaskSchemaTypeResolverPort;
import io.miranum.platform.tasklist.application.port.out.security.CurrentUserPort;
import io.miranum.platform.tasklist.domain.TaskSchemaType;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Collections;

import static io.miranum.platform.tasklist.adapter.out.schema.TaskVariables.TASK_SCHEMA_TYPE;
import static io.miranum.platform.tasklist.application.usecase.TestFixtures.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class WorkOnUserTaskUseCaseTest {

    private final TaskQueryPort taskQueryPort = mock(TaskQueryPort.class);
    private final CurrentUserPort currentUserPort = mock(CurrentUserPort.class);
    private final TaskSchemaRefResolverPort taskSchemaRefResolverPort = new VariableTaskSchemaResolverAdapter();
    private final JsonSchemaPort jsonSchemaPort = mock(JsonSchemaPort.class);
    private final TaskCommandPort taskCommandPort = mock(TaskCommandPort.class);
    private final JsonSchemaValidationPort jsonSchemaValidationPort = mock(JsonSchemaValidationPort.class);
    private final CancellationFlagOutPort cancellationFlagOutPort = mock(CancellationFlagOutPort.class);
    private final TaskSchemaTypeResolverPort taskSchemaTypeResolverPort = new VariableTaskSchemaTypeResolverAdapter();

    private final User user = new User("0123456789", Sets.newHashSet("group1", "group2"));
    private final WorkOnUserTask useCase = new WorkOnUserTaskUseCase(
            taskQueryPort,
            currentUserPort,
            taskSchemaRefResolverPort,
            jsonSchemaPort,
            taskCommandPort,
            jsonSchemaValidationPort,
            cancellationFlagOutPort,
            taskSchemaTypeResolverPort
    );

    @BeforeEach
    void setupMocks() {
        when(currentUserPort.getCurrentUser()).thenReturn(user);
        when(cancellationFlagOutPort.apply(any())).thenReturn(true);
    }

    @Test
    void loads_schema() {
        when(jsonSchemaPort.getSchemaById(any())).thenReturn(generateSchema("schema-1"));
        val schema = useCase.loadSchema("schema-1");
        assertThat(schema).isNotNull();
        assertThat(schema.getId()).isEqualTo("schema-1");

        verify(jsonSchemaPort).getSchemaById("schema-1");
        verifyNoMoreInteractions(jsonSchemaPort);

    }


    @Test
    void loads_non_existing_task() {
        when(taskQueryPort.getTaskByIdForCurrentUser(any(), any())).thenThrow(new TaskNotFoundException("not_exist"));
        val exception = assertThrows(TaskNotFoundException.class, () -> useCase.loadUserTask("not_exist"));
        assertThat(exception.getMessage()).isEqualTo("Task with id 'not_exist' could not be found.");
        verify(taskQueryPort).getTaskByIdForCurrentUser(user, "not_exist");
        verifyNoMoreInteractions(taskQueryPort);

    }

    @Test
    void loads_existing_task() {
        when(taskQueryPort.getTaskByIdForCurrentUser(any(), any())).thenReturn(
                generateTask("task_0", Collections.emptySet(), Collections.emptySet(), null, null, true)
        );
        when(jsonSchemaPort.getSchemaById(any())).thenReturn(generateSchema("schema-1"));

        val taskWithSchemaRef = useCase.loadUserTask("task_0");

        assertThat(taskWithSchemaRef.getSchemaRef()).isEqualTo("schema-1");

        verify(taskQueryPort).getTaskByIdForCurrentUser(user, "task_0");
        verify(jsonSchemaPort).getSchemaById("schema-1");
        verify(jsonSchemaValidationPort).filterVariables(any(), any());   //cannot be specific due to clearing of task.payload in filtering
        verifyNoMoreInteractions(taskQueryPort);
        verifyNoMoreInteractions(jsonSchemaPort);
        verifyNoMoreInteractions(jsonSchemaValidationPort);
    }


    @Test
    void loadUserTaskWithSchema() {
        when(taskQueryPort.getTaskByIdForCurrentUser(any(), any())).thenReturn(generateTasks(1, Collections.emptySet(), Collections.emptySet(), user.getUsername()).get(0));
        when(jsonSchemaPort.getSchemaById(any())).thenReturn(generateSchema("schema-1"));
        val taskWithSchema = useCase.loadUserTaskWithSchema("task_0");

        assertThat(taskWithSchema.getTask()).isNotNull();
        assertThat(taskWithSchema.getSchema()).isNotNull();
        assertThat(taskWithSchema.getTask().getId()).isEqualTo("task_0");
        assertThat(taskWithSchema.getSchema().getId()).isEqualTo("schema-1");

        verify(taskQueryPort).getTaskByIdForCurrentUser(user, "task_0");
        verifyNoMoreInteractions(taskQueryPort);

        verify(jsonSchemaPort).getSchemaById("schema-1");
        verifyNoMoreInteractions(jsonSchemaPort);
    }


    @Test
    void completesTask() {
        val schema1 = generateSchema("schema-1");
        val task0 = generateTask("task_0", Collections.emptySet(), Collections.emptySet(), user.getUsername(), null, false,
                CamundaBpmData
                        .builder()
                        .set(TASK_SCHEMA_TYPE, TaskSchemaType.SCHEMA_BASED)
                        .build()
        );

        val payload = CamundaBpmData
                .builder()
                .set(STRING_VAL, "some")
                .set(INTEGER_VAL, 42)
                .build();


        when(taskQueryPort.getTaskByIdForCurrentUser(any(), any())).thenReturn(task0);
        when(jsonSchemaPort.getSchemaById(any())).thenReturn(schema1);
        when(jsonSchemaValidationPort.validateAndSerialize(any(), any(), any())).thenReturn(payload);

        useCase.completeUserTask("task_0", payload);

        verify(taskQueryPort).getTaskByIdForCurrentUser(user, "task_0");
        verifyNoMoreInteractions(taskQueryPort);

        verify(jsonSchemaValidationPort).validateAndSerialize(schema1, task0, payload);
        verifyNoMoreInteractions(jsonSchemaValidationPort);

        verify(taskCommandPort).completeUserTask("task_0", payload);
        verifyNoMoreInteractions(taskCommandPort);
    }

    @Test
    void savesTask() {
        val schema1 = generateSchema("schema-1");
        val task0 = generateTask("task_0", Collections.emptySet(), Collections.emptySet(), user.getUsername(), null, true,
                CamundaBpmData
                        .builder()
                        .set(TASK_SCHEMA_TYPE, TaskSchemaType.SCHEMA_BASED)
                        .build()
        );

        val payload = CamundaBpmData
                .builder()
                .set(STRING_VAL, "some")
                .set(INTEGER_VAL, 42)
                .build();


        when(taskQueryPort.getTaskByIdForCurrentUser(any(), any())).thenReturn(task0);
        when(jsonSchemaPort.getSchemaById(any())).thenReturn(schema1);
        when(jsonSchemaValidationPort.validateAndSerialize(any(), any(), any())).thenReturn(payload);

        useCase.saveUserTask("task_0", payload);

        verify(taskQueryPort).getTaskByIdForCurrentUser(user, "task_0");
        verifyNoMoreInteractions(taskQueryPort);

        verify(jsonSchemaValidationPort).validateAndSerialize(schema1, task0, payload);
        verifyNoMoreInteractions(jsonSchemaValidationPort);

        verify(taskCommandPort).saveUserTask("task_0", payload);
        verifyNoMoreInteractions(taskCommandPort);

    }

    @Test
    void assignsUserTask() {
        when(taskQueryPort.getTaskByIdForCurrentUser(any(), any())).thenReturn(generateTasks(1, Collections.emptySet(), Collections.emptySet(), user.getUsername()).get(0));

        useCase.assignUserTask("task_0", "4711");

        verify(taskQueryPort).getTaskByIdForCurrentUser(user, "task_0");
        verifyNoMoreInteractions(taskQueryPort);

        verify(taskCommandPort).assignUserTask("task_0", "4711");
        verifyNoMoreInteractions(taskCommandPort);
    }

    @Test
    void notAssignsUserTask() {
        when(taskQueryPort.getTaskByIdForCurrentUser(any(), any())).thenReturn(generateTasks(1, Collections.emptySet(), Collections.emptySet(), user.getUsername()).get(0));

        useCase.assignUserTask("task_0", user.getUsername());

        verify(taskQueryPort).getTaskByIdForCurrentUser(user, "task_0");
        verifyNoMoreInteractions(taskQueryPort);

        verifyNoInteractions(taskCommandPort);
    }


    @Test
    void unassignsUserTask() {
        when(taskQueryPort.getTaskByIdForCurrentUser(any(), any())).thenReturn(generateTasks(1, Collections.emptySet(), Collections.emptySet(), user.getUsername()).get(0));

        useCase.unassignUserTask("task_0");

        verify(taskQueryPort).getTaskByIdForCurrentUser(user, "task_0");
        verifyNoMoreInteractions(taskQueryPort);

        verify(taskCommandPort).unassignUserTask("task_0");
        verifyNoMoreInteractions(taskCommandPort);
    }

    @Test
    void deferesUserTask() {
        when(taskQueryPort.getTaskByIdForCurrentUser(any(), any())).thenReturn(generateTasks(1, Collections.emptySet(), Collections.emptySet(), user.getUsername()).get(0));

        val until = OffsetDateTime.now();
        useCase.deferUserTask("task_0", until);

        verify(taskQueryPort).getTaskByIdForCurrentUser(user, "task_0");
        verifyNoMoreInteractions(taskQueryPort);

        verify(taskCommandPort).deferUserTask("task_0", until.toInstant());
        verifyNoMoreInteractions(taskCommandPort);
    }

    @Test
    void undefersUserTask() {
        val until = Instant.now();

        when(taskQueryPort.getTaskByIdForCurrentUser(any(), any())).thenReturn(
                generateTask("task_0", Collections.emptySet(), Collections.emptySet(), user.getUsername(), until)
        );

        useCase.undeferUserTask("task_0");

        verify(taskQueryPort).getTaskByIdForCurrentUser(user, "task_0");
        verifyNoMoreInteractions(taskQueryPort);

        verify(taskCommandPort).undeferUserTask("task_0");
        verifyNoMoreInteractions(taskCommandPort);
    }

    @Test
    void cancelsUserTask() {
        when(taskQueryPort.getTaskByIdForCurrentUser(any(), any())).thenReturn(
                generateTask("task_0", Collections.emptySet(), Collections.emptySet(), user.getUsername(), null, true)
        );
        useCase.cancelUserTask("task_0");

        verify(taskQueryPort).getTaskByIdForCurrentUser(user, "task_0");
        verifyNoMoreInteractions(taskQueryPort);

        verify(taskCommandPort).cancelUserTask("task_0");
        verifyNoMoreInteractions(taskCommandPort);

    }

    @Test
    void failsToCancelUserTask() {
        when(cancellationFlagOutPort.apply(any())).thenReturn(false); // all tasks are not cancellable
        val task = generateTask("task_0", Collections.emptySet(), Collections.emptySet(), user.getUsername(), null, false);
        when(taskQueryPort.getTaskByIdForCurrentUser(any(), any())).thenReturn(task);

        val exception = assertThrows(IllegalArgumentException.class, () -> useCase.cancelUserTask("task_0"));
        assertThat(exception.getMessage()).isEqualTo("Task task_0 can not be cancelled.");

        verify(cancellationFlagOutPort).apply(task);

        verify(taskQueryPort).getTaskByIdForCurrentUser(user, "task_0");
        verifyNoMoreInteractions(taskQueryPort);

        verifyNoInteractions(taskCommandPort);

    }

}
