package io.miragon.miranum.platform.tasklist;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "miranum.tasklist")
@Getter
@Setter
public class TaskProperties {

    private boolean notificationsEnabled = false;
    private String customFieldsPrefix = "miranum_task_";

}
