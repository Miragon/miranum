package io.miranum.platform.tasklist.application.service;

import io.miranum.platform.tasklist.application.port.in.WorkOnUserTaskUseCase;
import io.miranum.platform.tasklist.application.port.out.cancellation.CancellationFlagOutPort;
import io.miranum.platform.tasklist.application.port.out.engine.TaskCommandPort;
import io.miranum.platform.tasklist.application.port.out.polyflow.TaskNotFoundException;
import io.miranum.platform.tasklist.application.port.out.polyflow.TaskOutPort;
import io.miranum.platform.tasklist.application.port.out.schema.JsonSchemaPort;
import io.miranum.platform.tasklist.application.port.out.schema.JsonSchemaValidationPort;
import io.miranum.platform.tasklist.application.port.out.schema.TaskSchemaRefResolverPort;
import io.miranum.platform.tasklist.application.port.out.security.CurrentUserPort;
import io.miranum.platform.tasklist.domain.JsonSchema;
import io.miranum.platform.tasklist.domain.JsonSchemaValidationException;
import io.miranum.platform.tasklist.domain.Task;
import io.muenchendigital.digiwf.json.validation.DigiWFValidationException;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class WorkOnUserTaskService implements WorkOnUserTaskUseCase {
    private final TaskOutPort taskQueryPort;
    private final CurrentUserPort currentUserPort;
    private final TaskSchemaRefResolverPort taskSchemaRefResolverPort;
    private final JsonSchemaPort jsonSchemaPort;
    private final TaskCommandPort taskCommandPort;
    private final JsonSchemaValidationPort jsonSchemaValidationPort;
    private final CancellationFlagOutPort cancellationFlagOutPort;


    @Override
    public void completeUserTask(String user, String taskId, Map<String, Object> payload) {

    }

    @Override
    public void saveUserTask(String user, String taskId, Map<String, Object> payload) {

    }

    @Override
    public void assignUserTask(String user, String taskId, String assignee) {

    }

    @Override
    public void unassignUserTask(String user, String taskId) {

    }

    @Override
    public void cancelUserTask(String taskId) throws TaskNotFoundException {
        val task = getTaskForUser(taskId);
        if (cancellationFlagOutPort.apply(task)) {
            taskCommandPort.cancelUserTask(taskId);
        } else {
            throw new IllegalArgumentException("Task " + taskId + " can not be cancelled.");
        }
    }

    private Task getTaskForUser(String taskId) {
        val currentUser = currentUserPort.getCurrentUser();
        return taskQueryPort.getTaskByIdForCurrentUser(currentUser, taskId);
    }

    private void filterSchemaBased(Task task, JsonSchema schema) {
        val filteredPayload = this.jsonSchemaValidationPort.filterVariables(task.getPayload(), schema);
        task.getPayload().clear();
        task.getPayload().putAll(filteredPayload);
    }


    private void saveSchemaBasedUserTask(String taskId, Task task, Map<String, Object> payload) {
        val schemaRef = taskSchemaRefResolverPort.apply(task);
        val schema = jsonSchemaPort.getSchemaById(schemaRef);
        try {
            val variables = jsonSchemaValidationPort.validateAndSerialize(schema, task, payload);
            taskCommandPort.saveUserTask(taskId, variables);
        } catch (DigiWFValidationException exception) {
            throw new JsonSchemaValidationException("json schema validation failed"); // todo extract field information
        }
    }

    private void completeSchemaBasedUserTask(String taskId, Task task, Map<String, Object> payload) {
        val schemaRef = taskSchemaRefResolverPort.apply(task);
        val schema = jsonSchemaPort.getSchemaById(schemaRef);
        try {
            val variables = jsonSchemaValidationPort.validateAndSerialize(schema, task, payload);
            taskCommandPort.completeUserTask(taskId, variables);
        } catch (DigiWFValidationException exception) {
            throw new JsonSchemaValidationException("json schema validation failed"); // todo extract field information
        }
    }
}
