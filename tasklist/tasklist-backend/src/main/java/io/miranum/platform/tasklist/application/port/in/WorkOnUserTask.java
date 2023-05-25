package io.miranum.platform.tasklist.application.port.in;


import io.miranum.platform.tasklist.application.port.out.polyflow.TaskNotFoundException;
import io.miranum.platform.tasklist.application.port.out.schema.JsonSchemaNotFoundException;
import io.miranum.platform.tasklist.domain.JsonSchema;
import io.miranum.platform.tasklist.domain.TaskWithSchema;
import io.miranum.platform.tasklist.domain.TaskWithSchemaRef;

import java.time.OffsetDateTime;
import java.util.Map;

/**
 * Use case describing all operations a user can perform on an individual user task.
 */
public interface WorkOnUserTask {

    /**
     * Loads a schema by id.
     *
     * @param schemaId schema id.
     * @return schema.
     * @throws JsonSchemaNotFoundException if schema is not available or access is not permitted.
     */
    JsonSchema loadSchema(String schemaId) throws JsonSchemaNotFoundException;

    /**
     * Loads a user task by id.
     *
     * @param taskId task id.
     * @return Task with schema reference.
     * @throws TaskNotFoundException if task is not available or access is not permitted.
     */
    TaskWithSchemaRef loadUserTask(String taskId) throws TaskNotFoundException;

    /**
     * Loads a user task with schema by id.
     *
     * @param taskId task id.
     * @return Task with schema.
     * @throws TaskNotFoundException       if task is not available or access is not permitted.
     * @throws JsonSchemaNotFoundException if schema is not available or access is not permitted.
     */
    TaskWithSchema loadUserTaskWithSchema(String taskId) throws TaskNotFoundException, JsonSchemaNotFoundException;

    /**
     * Completes user task.
     *
     * @param taskId  task id.
     * @param payload process variables to pass to the process during completion.
     * @throws TaskNotFoundException       if task is not available or access is not permitted.
     * @throws JsonSchemaNotFoundException if schema is not available or access is not permitted.
     */
    void completeUserTask(String taskId, Map<String, Object> payload) throws TaskNotFoundException, JsonSchemaNotFoundException;

    /**
     * Save variables for the user task.
     *
     * @param taskId  task id.
     * @param payload process variables to pass to the process during completion.
     * @throws TaskNotFoundException       if task is not available or access is not permitted.
     * @throws JsonSchemaNotFoundException if schema is not available or access is not permitted.
     */
    void saveUserTask(String taskId, Map<String, Object> payload) throws TaskNotFoundException, JsonSchemaNotFoundException;

    /**
     * Assigns a user task to another user.
     *
     * @param taskId   task id.
     * @param assignee new assignee username.
     * @throws TaskNotFoundException if task is not available or access is not permitted.
     */
    void assignUserTask(String taskId, String assignee) throws TaskNotFoundException;

    /**
     * Clears assignment of a user task.
     *
     * @param taskId task id.
     * @throws TaskNotFoundException if task is not available or access is not permitted.
     */
    void unassignUserTask(String taskId) throws TaskNotFoundException;

    /**
     * Defers a user task.
     *
     * @param taskId       task id.
     * @param followUpDate date until the user task is deferred.
     * @throws TaskNotFoundException if task is not available or access is not permitted.
     */
    void deferUserTask(String taskId, OffsetDateTime followUpDate) throws TaskNotFoundException;

    /**
     * Clears the user task follow-up date.
     *
     * @param taskId task id.
     * @throws TaskNotFoundException if task is not available or access is not permitted.
     */
    void undeferUserTask(String taskId) throws TaskNotFoundException;
}
