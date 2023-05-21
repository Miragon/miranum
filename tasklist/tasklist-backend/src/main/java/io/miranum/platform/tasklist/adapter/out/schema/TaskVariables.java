package io.miranum.platform.tasklist.adapter.out.schema;

import io.holunda.camunda.bpm.data.factory.VariableFactory;

import static io.holunda.camunda.bpm.data.CamundaBpmData.stringVariable;

/**
 * Definition of task variables.
 */
public class TaskVariables {
    public static final VariableFactory<String> TASK_SCHEMA_KEY = stringVariable("app_task_schema_key");
}
