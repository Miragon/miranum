package io.miranum.platform.tasklist.application.usecase;

import io.miranum.platform.tasklist.application.port.in.RetrieveTasksForUser;
import io.miranum.platform.tasklist.application.port.out.polyflow.TaskQueryPort;
import io.miranum.platform.tasklist.application.port.out.schema.TaskSchemaRefResolverPort;
import io.miranum.platform.tasklist.application.port.out.security.CurrentUserPort;
import io.miranum.platform.tasklist.domain.PageOfTasks;
import io.miranum.platform.tasklist.domain.PageOfTasksWithSchema;
import io.miranum.platform.tasklist.domain.PagingAndSorting;
import io.miranum.platform.tasklist.domain.TaskWithSchemaRef;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RetrieveTasksForUserUseCase implements RetrieveTasksForUser {

    private final TaskQueryPort taskQueryPort;
    private final CurrentUserPort currentUserPort;
    private final TaskSchemaRefResolverPort taskSchemaRefResolverPort;

    @Override
    public PageOfTasksWithSchema getUnassignedTasksForCurrentUserGroup(String query, PagingAndSorting pagingAndSorting) {
        var currentUser = currentUserPort.getCurrentUser();
        var result = taskQueryPort.getTasksForCurrentUserGroup(currentUser, query, false, pagingAndSorting);
        return enrichWithSchema(result);
    }

    @Override
    public PageOfTasksWithSchema getAssignedTasksForCurrentUserGroup(String query, PagingAndSorting pagingAndSorting) {
        var currentUser = currentUserPort.getCurrentUser();
        var result = taskQueryPort.getTasksForCurrentUserGroup(currentUser, query, true, pagingAndSorting);
        return enrichWithSchema(result);
    }

    @Override
    public PageOfTasksWithSchema getTasksForCurrentUser(String query, LocalDate followUp, PagingAndSorting pagingAndSorting) {
        var currentUser = currentUserPort.getCurrentUser();
        var result = taskQueryPort.getTasksForCurrentUser(currentUser, query, followUp, pagingAndSorting);
        return enrichWithSchema(result);
    }

    private PageOfTasksWithSchema enrichWithSchema(PageOfTasks result) {
        return new PageOfTasksWithSchema(
                result.getTasks().stream().map(task -> new TaskWithSchemaRef(task, taskSchemaRefResolverPort.apply(task))).collect(Collectors.toList()),
                result.getTotalElementsCount(),
                result.getPagingAndSorting()
        );
    }
}
