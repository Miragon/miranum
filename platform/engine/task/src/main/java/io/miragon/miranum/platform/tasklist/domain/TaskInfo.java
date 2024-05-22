package io.miragon.miranum.platform.tasklist.domain;

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
    private String candidateUsers;
    private String candidateGroups;
    private String form;

}
