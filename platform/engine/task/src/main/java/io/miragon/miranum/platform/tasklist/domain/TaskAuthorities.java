package io.miragon.miranum.platform.tasklist.domain;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class TaskAuthorities {

    private String id;
    private String type;
    private String value;

}
