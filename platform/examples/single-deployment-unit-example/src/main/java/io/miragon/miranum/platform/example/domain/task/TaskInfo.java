package io.miragon.miranum.platform.example.domain.task;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskInfo {

    private String id;
    private String description;
    private String definitionName;
    private String instanceId;
    private String assignee;
    private String form;

}
