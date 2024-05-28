package io.miragon.miranum.connect.task.api.command;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignUserTaskCommand {

        private String taskId;

        private String assignee;
}
