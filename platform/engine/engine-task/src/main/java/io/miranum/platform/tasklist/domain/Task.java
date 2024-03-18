package io.miranum.platform.tasklist.domain;

import lombok.Data;

@Data
public class Task {

    private String id;
    private String name;
    private String description;
    private String assignee;

}
