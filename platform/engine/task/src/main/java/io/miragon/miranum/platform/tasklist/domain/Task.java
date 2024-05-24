package io.miragon.miranum.platform.tasklist.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Task {

    private String id;
    private String name;
    private String description;

    private String processName;
    private String processInstanceId;

    private String assignee;
    private List<String> candidateUsers;
    private List<String> candidateGroups;
    
    private String formKey;

}
