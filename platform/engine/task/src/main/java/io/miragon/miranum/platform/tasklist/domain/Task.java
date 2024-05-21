package io.miragon.miranum.platform.tasklist.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Task {

    private String id;
    private String name;
    private String description;

    private String processName;
    private String processInstanceId;

    private String assignee;
    private String candidateGroups;
    private String candidateUsers;

    private String form;

}
