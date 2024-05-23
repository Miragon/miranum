package io.miragon.miranum.platform.tasklist.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TaskInfo {

    private String id;
    private String description;
    private String definitionName;
    private String instanceId;
    private String assignee;
    private List<TaskAuthorities> authorities;
    private String form;

    public List<String> getCandidateUsers() {
        return authorities.stream()
                .filter(authority -> authority.getType().equals("user"))
                .map(TaskAuthorities::getValue)
                .toList();
    }

    public List<String> getCandidateGroups() {
        return authorities.stream()
                .filter(authority -> authority.getType().equals("group"))
                .map(TaskAuthorities::getValue)
                .toList();
    }

}
