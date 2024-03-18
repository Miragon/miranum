package io.miranum.platform.tasklist.domain;

import lombok.Data;

import java.util.Map;

@Data
public class TaskWithSchema {

    private final Task task;

    private final boolean cancelable;

    private final Map<String, Object> data;

    private final JsonSchema schema;
}
