package io.miragon.miranum.platform.tasklist.application.accesscontrol;

import io.miragon.miranum.platform.security.authentication.UserAuthenticationProvider;
import io.miragon.miranum.platform.tasklist.domain.Task;
import io.miragon.miranum.platform.tasklist.exception.TaskAccessDeniedException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserTaskAccessProviderTest {

    private final UserAuthenticationProvider authenticationProvider = mock(UserAuthenticationProvider.class);
    private final UserTaskAccessProviderImpl accessProvider = new UserTaskAccessProviderImpl(authenticationProvider);

    @Test
    void testHasAccess_userIsAssignee() {
        final Task task = Task.builder()
                .id("1")
                .assignee("user1")
                .build();

        final Task result = accessProvider.hasAccess(task, "user1");

        assertThat(result)
                .isNotNull()
                .isEqualTo(task);
    }

    @Test
    void testHasAccess_userIsCandidateUser() {
        final Task task = Task.builder()
                .id("1")
                .candidateUsers(List.of("user1"))
                .build();

        final Task result = accessProvider.hasAccess(task, "user1");

        assertThat(result)
                .isNotNull()
                .isEqualTo(task);
    }

    @Test
    void testHasAccess_userIsCandidateGroup() {
        final Task task = Task.builder()
                .id("1")
                .candidateGroups(List.of("group1"))
                .build();
        when(authenticationProvider.getLoggedInUserRoles()).thenReturn(List.of("group1"));

        final Task result = accessProvider.hasAccess(task, "user1");

        assertThat(result)
                .isNotNull()
                .isEqualTo(task);
    }

    @Test
    void testHasAccess_throwsTaskAccessDeniedException() {
        final Task task = Task.builder()
                .id("1")
                .assignee("user1")
                .candidateUsers(List.of("user1"))
                .candidateGroups(List.of("group1"))
                .build();
        when(authenticationProvider.getLoggedInUserRoles()).thenReturn(List.of("group2"));

        assertThatThrownBy(() -> accessProvider.hasAccess(task, "user2"))
                .isInstanceOf(TaskAccessDeniedException.class)
                .hasMessage("User user2 has no access to task 1");
    }

}
