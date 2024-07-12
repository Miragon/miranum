package io.miragon.miranum.platform.tasklist.domain;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class TaskCustomFields {

    private String id;
    private String key;
    private String value;

}
