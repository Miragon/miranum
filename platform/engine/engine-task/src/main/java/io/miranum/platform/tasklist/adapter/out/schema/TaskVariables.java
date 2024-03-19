package io.miranum.platform.tasklist.adapter.out.schema;

/**
 * Definition of task variables.
 */
public class TaskVariables {
    /**
     * Task variable containing the JSON schema for the task.
     */
    public static final String TASK_SCHEMA_KEY = "app_task_schema_key";

    /**
     * Flag indicating if the task is cancellable.
     */
    public static final String TASK_CANCELABLE = "app_task_cancelable";

    /**
     * Reflects the task description.
     */
    public static final String TASK_DESCRIPTION = "app_task_description";

    /**
     * Task variable containing the process file context of the task.
     */
    public static final String PROCESS_FILE_CONTEXT = "app_file_context";

    /**
     * Task variable containing the editable paths of the task.
     */
    public static final String FILE_PATHS = "app_file_paths";

    /**
     * Task variable containing the readonly file paths of the task.
     */
    public static final String FILE_PATHS_READONLY = "app_file_paths_readonly";

}
