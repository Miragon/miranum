package io.miranum.platform.tasklist.application.usecase;

import com.google.common.collect.Sets;
import io.holunda.camunda.bpm.data.CamundaBpmData;
import io.holunda.camunda.bpm.data.factory.VariableFactory;
import io.holunda.camunda.taskpool.api.task.ProcessReference;
import io.holunda.camunda.taskpool.api.task.TaskCreatedEngineEvent;
import io.holunda.camunda.taskpool.api.task.TaskDeletedEngineEvent;
import io.holunda.polyflow.view.Task;
import io.miranum.platform.tasklist.adapter.out.schema.TaskVariables;
import io.miranum.platform.tasklist.domain.JsonSchema;
import io.miranum.platform.tasklist.domain.TaskSchemaType;
import lombok.SneakyThrows;
import lombok.val;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.*;

import static io.holunda.camunda.bpm.data.CamundaBpmData.*;
import static io.miranum.platform.tasklist.adapter.out.schema.TaskVariables.TASK_CANCELABLE;
import static org.camunda.bpm.engine.variable.Variables.createVariables;

public class TestFixtures {

    public static final VariableFactory<String> STRING_VAL = stringVariable("string_val");
    public static final VariableFactory<Integer> INTEGER_VAL = intVariable("int_val");

    private static final Instant createTime = Instant.now();
    private static final String instanceId = UUID.randomUUID().toString();
    public static VariableMap variables = CamundaBpmData.builder()
            .set(TaskVariables.TASK_SCHEMA_KEY, "schema-1")
            .set(TaskVariables.TASK_SCHEMA_TYPE, TaskSchemaType.SCHEMA_BASED)
            .build();


    public static JsonSchema generateSchema(String schemaId) {
        return new JsonSchema(schemaId, "{\"type\": \"object\"}");
    }

    public static Task generateTask(String taskId, Set<String> candidateUsers, Set<String> candidateGroups, String assignee, Instant followUpDate) {
        return generateTask(taskId, candidateUsers, candidateGroups, assignee, followUpDate, false);
    }

    public static Task generateTask(String taskId, Set<String> candidateUsers, Set<String> candidateGroups, String assignee, Instant followUpDate, Boolean cancelable) {
        return generateTask(taskId, candidateUsers, candidateGroups, assignee, followUpDate, cancelable, variables);
    }


    public static Task generateTask(String taskId, Set<String> candidateUsers, Set<String> candidateGroups, String assignee, Instant followUpDate, Boolean cancelable, VariableMap variables) {
        try {
            Thread.sleep(1);
        } catch (InterruptedException ignored) {

        }

        val vars = Variables.createVariables();
        vars.putAll(variables);
        val varsWriter = writer(vars);
        if (cancelable != null) {
            varsWriter.set(TASK_CANCELABLE, cancelable);
        }

        val reference = new ProcessReference(instanceId, instanceId, "def:1", "def", "Sample process", "app1", null);
        return new Task(
                taskId,
                reference,
                "task_def_1",
                varsWriter.variables(),
                createVariables(),
                null,
                "Task Name",
                null,
                null,
                50,
                createTime,
                candidateUsers,
                candidateGroups,
                assignee,
                null,
                null,
                followUpDate,
                false
        );
    }

    public static List<Task> generateTasks(int count, Set<String> candidateUsers, Set<String> candidateGroups, String assignee, VariableMap variables) {
        val tasks = new ArrayList<Task>();
        for (int i = 0; i < count; i++) {
            tasks.add(generateTask("task_" + i, candidateUsers, candidateGroups, assignee, null, false, variables));
        }
        return tasks;
    }

    public static List<Task> generateTasks(int count, Set<String> candidateUsers, Set<String> candidateGroups, String assignee) {
        return generateTasks(count, candidateUsers, candidateGroups, assignee, variables);
    }

    public static TaskCreatedEngineEvent createEvent(Task task) {
        return new TaskCreatedEngineEvent(
                task.getId(),
                task.getSourceReference(),
                task.getTaskDefinitionKey(),
                task.getPayload(),
                task.getCorrelations(),
                task.getBusinessKey(),
                task.getName(),
                task.getDescription(),
                task.getFormKey(),
                task.getPriority(),
                task.getCreateTime() == null ? null : Date.from(task.getCreateTime()),
                task.getCandidateUsers(),
                task.getCandidateGroups(),
                task.getAssignee(),
                task.getOwner(),
                task.getDueDate() == null ? null : Date.from(task.getDueDate()),
                task.getFollowUpDate() == null ? null : Date.from(task.getFollowUpDate())
        );
    }

    public static TaskDeletedEngineEvent deleteEvent(Task task) {
        return new TaskDeletedEngineEvent(
                task.getId(),
                task.getSourceReference(),
                task.getTaskDefinitionKey(),
                task.getPayload(),
                task.getCorrelations(),
                task.getBusinessKey(),
                task.getName(),
                task.getDescription(),
                task.getFormKey(),
                task.getPriority(),
                task.getCreateTime() == null ? null : Date.from(task.getCreateTime()),
                task.getCandidateUsers(),
                task.getCandidateGroups(),
                task.getAssignee(),
                task.getOwner(),
                task.getDueDate() == null ? null : Date.from(task.getDueDate()),
                task.getFollowUpDate() == null ? null : Date.from(task.getFollowUpDate()),
                "reason"
        );
    }

    public static TaskCreatedEngineEvent createEvent(String taskId, String assignee) {
        val task = generateTask(taskId, Sets.newHashSet(), Sets.newHashSet(), assignee, null);
        return createEvent(task);
    }

    @SneakyThrows
    public static String getJsonFromFile(String relativeFilename) {
        return Files.readString(Path.of("src", "test", "resources", "files", relativeFilename));
    }
}
