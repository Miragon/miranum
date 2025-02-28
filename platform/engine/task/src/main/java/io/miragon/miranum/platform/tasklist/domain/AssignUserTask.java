package io.miragon.miranum.platform.tasklist.domain;

import lombok.Builder;

@Builder
public record AssignUserTask(
    String taskId,
    String assignee
) { }
