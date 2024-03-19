package io.miranum.platform.tasklist.domain;

import lombok.Data;

import java.util.List;

@Data
public class Task {

    private String id;
    private String name;
    private String description;

    private String assignee;
    private String candidateGroups;
    private String candidateUsers;

    private String schemaKey;
    private Boolean cancelable;
    private List<String> filesPaths;
    private List<String> filesPathsReadOnly;

}
