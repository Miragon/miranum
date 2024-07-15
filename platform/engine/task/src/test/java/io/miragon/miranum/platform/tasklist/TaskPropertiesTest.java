package io.miragon.miranum.platform.tasklist;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TaskPropertiesTest {

    @Test
    void testTaskPropertiesDefaultValues() {
        final TaskProperties taskProperties = new TaskProperties();
        assertThat(taskProperties.getCustomFieldsPrefix()).isEqualTo("miranum_task_");
    }

}
