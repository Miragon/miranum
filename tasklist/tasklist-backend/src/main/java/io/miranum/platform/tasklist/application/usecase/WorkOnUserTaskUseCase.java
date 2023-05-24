package io.miranum.platform.tasklist.application.usecase;

import io.holunda.polyflow.view.Task;
import io.miranum.platform.tasklist.application.port.in.WorkOnUserTask;
import io.miranum.platform.tasklist.application.port.out.engine.TaskCommandPort;
import io.miranum.platform.tasklist.application.port.out.polyflow.TaskNotFoundException;
import io.miranum.platform.tasklist.application.port.out.polyflow.TaskQueryPort;
import io.miranum.platform.tasklist.application.port.out.schema.JsonSchemaNotFoundException;
import io.miranum.platform.tasklist.application.port.out.schema.JsonSchemaPort;
import io.miranum.platform.tasklist.application.port.out.schema.JsonSchemaValidationPort;
import io.miranum.platform.tasklist.application.port.out.schema.TaskSchemaRefResolverPort;
import io.miranum.platform.tasklist.application.port.out.security.CurrentUserPort;
import io.miranum.platform.tasklist.domain.JsonSchema;
import io.miranum.platform.tasklist.domain.TaskWithSchema;
import io.miranum.platform.tasklist.domain.TaskWithSchemaRef;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class WorkOnUserTaskUseCase implements WorkOnUserTask {
    private final TaskQueryPort taskQueryPort;
    private final CurrentUserPort currentUserPort;
    private final TaskSchemaRefResolverPort taskSchemaRefResolverPort;
    private final JsonSchemaPort jsonSchemaPort;
    private final TaskCommandPort taskCommandPort;
    private final JsonSchemaValidationPort jsonSchemaValidationPort;

    @Override
    public JsonSchema loadSchema(String schemaId) throws JsonSchemaNotFoundException {
        return jsonSchemaPort.getByRef(schemaId);
    }

    @Override
    public TaskWithSchemaRef loadUserTask(String taskId) throws TaskNotFoundException {
        val task = getTaskForUser(taskId);
        val schemaRef = taskSchemaRefResolverPort.apply(task);
        return new TaskWithSchemaRef(task, schemaRef);
    }

    @Override
    public TaskWithSchema loadUserTaskWithSchema(String taskId) throws TaskNotFoundException, JsonSchemaNotFoundException {
        val task = getTaskForUser(taskId);
        val schemaRef = taskSchemaRefResolverPort.apply(task);
        val schema = jsonSchemaPort.getByRef(schemaRef);
        return new TaskWithSchema(task, schema);
    }


    @Override
    public void completeUserTask(String taskId, Map<String, Object> payload) throws TaskNotFoundException {
        var task = getTaskForUser(taskId);
        val schemaRef = taskSchemaRefResolverPort.apply(task);
        val schema = jsonSchemaPort.getByRef(schemaRef);
        val variables = jsonSchemaValidationPort.validateAndSerialize(schema, task, payload);
        taskCommandPort.completeTask(taskId, variables);
    }

    @Override
    public void saveUserTask(String taskId, Map<String, Object> payload) throws TaskNotFoundException {
        var task = getTaskForUser(taskId);
        val schemaRef = taskSchemaRefResolverPort.apply(task);
        val schema = jsonSchemaPort.getByRef(schemaRef);
        val variables = jsonSchemaValidationPort.validateAndSerialize(schema, task, payload);
        taskCommandPort.saveUserTask(taskId, variables);
    }

    @Override
    public void assignUserTask(String taskId, String assignee) throws TaskNotFoundException {
        val task = getTaskForUser(taskId);
        if (!assignee.equals(task.getAssignee())) {
            taskCommandPort.assignUserTask(taskId, assignee);
        }
    }

    @Override
    public void unassignUserTask(String taskId) throws TaskNotFoundException {
        val task = getTaskForUser(taskId);
        if (task.getAssignee() != null) {
            taskCommandPort.unassignUserTask(taskId);
        }
    }

    @Override
    public void deferUserTask(String taskId, OffsetDateTime followUpDate) throws TaskNotFoundException {
        val task = getTaskForUser(taskId);
        taskCommandPort.deferUserTask(taskId, followUpDate.toInstant());
    }

    @Override
    public void undeferUserTask(String taskId) throws TaskNotFoundException {
        val task = getTaskForUser(taskId);
        if (task.getFollowUpDate() != null) {
            taskCommandPort.undeferUserTask(taskId);
        }
    }

    private Task getTaskForUser(String taskId) {
        val currentUser = currentUserPort.getCurrentUser();
        return taskQueryPort.getTaskByIdForCurrentUser(currentUser, taskId);
    }
}
