package io.miranum.platform.connect.task.api;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompleteTaskCommand {

    private String taskId;

    private Map<String, Object> variables;

}
