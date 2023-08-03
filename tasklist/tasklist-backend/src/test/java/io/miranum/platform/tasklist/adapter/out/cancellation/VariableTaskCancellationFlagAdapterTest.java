package io.miranum.platform.tasklist.adapter.out.cancellation;

import com.google.common.collect.Sets;
import io.holunda.polyflow.view.auth.User;
import io.miranum.platform.tasklist.application.port.out.cancellation.CancellationFlagOutPort;
import lombok.val;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static io.miranum.platform.tasklist.application.usecase.TestFixtures.generateTask;
import static org.assertj.core.api.Assertions.assertThat;

class VariableTaskCancellationFlagAdapterTest {

    private final CancellationFlagOutPort port = new VariableTaskCancellationFlagAdapter();
    private final User user = new User("0123456789", Sets.newHashSet("group1", "group2"));

    @Test
    public void allows_to_cancel_task() {
        val task0 = generateTask("task_0", Collections.emptySet(), Collections.emptySet(), user.getUsername(), null, true);
        assertThat(port.apply(task0)).isTrue();
    }

    @Test
    public void disallows_to_cancel_task() {
        val task0 = generateTask("task_0", Collections.emptySet(), Collections.emptySet(), user.getUsername(), null, false);
        assertThat(port.apply(task0)).isFalse();
    }

    @Test
    public void disallows_to_cancel_task_on_missing() {
        val task0 = generateTask("task_0", Collections.emptySet(), Collections.emptySet(), user.getUsername(), null, null);
        assertThat(port.apply(task0)).isFalse();
    }

}