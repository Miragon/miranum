package io.miragon.miranum.platform.tasklist.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

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

    private Map<String, String> customFields;

}
