package io.miragon.miranum.connect.task.impl.domain;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class TaskAuthorities {

    private String id;
    private String type;
    private String value;

}
