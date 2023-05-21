package io.miranum.platform.tasklist.adapter.in.rest.mapper;

import io.holunda.polyflow.view.Task;
import io.miranum.platform.tasklist.adapter.in.rest.model.*;
import io.miranum.platform.tasklist.domain.JsonSchema;
import io.miranum.platform.tasklist.domain.PageOfTasksWithSchema;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import javax.annotation.Nonnull;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = DateMapper.class)
public interface TaskMapper {

    @Mapping(target = "processName", source = "task.sourceReference.name")
    @Mapping(target = "schemaRef", source = "schemaRef")
    TaskTO to(Task task, String schemaRef);

    @Mapping(target = "processName", source = "task.sourceReference.name")
    @Mapping(target = "processInstanceId", source = "task.sourceReference.instanceId")
    @Mapping(target = "variables", source = "task.payload")
    @Mapping(target = "schemaRef", source = "schemaRef")
    TaskWithDetailsTO toWithDetails(Task task, String schemaRef);

    @Mapping(target = "schemaId", source = "id")
    @Mapping(target = "schemaJson", source = "schema")
    TaskCombinedSchemaTO to(JsonSchema schema);

    @Mapping(target = "id", source = "task.id")
    @Mapping(target = "processName", source = "task.sourceReference.name")
    @Mapping(target = "processInstanceId", source = "task.sourceReference.instanceId")
    @Mapping(target = "variables", source = "task.payload")
    @Mapping(target = "schema", expression = "java(schema.asMap())")
    TaskWithSchemaTO toWithSchema(@Nonnull Task task, @Nonnull JsonSchema schema);

    default PageOfTasksTO to(PageOfTasksWithSchema domain) {
        var pagingAndSorting = domain.getPagingAndSorting();
        var totalPages = Double.valueOf(Math.ceil(domain.getTotalElementsCount() / pagingAndSorting.getPageSize().doubleValue())).intValue();
        var tasks = domain.getTasks().stream().map(taskWithSchema -> this.to(taskWithSchema.getTask(), taskWithSchema.getSchemaRef())).collect(Collectors.toList());
        var empty = domain.getTotalElementsCount() == 0;
        var sortRequested = pagingAndSorting.getSort() != null;
        return new PageOfTasksTO()
                .pageable(new PageOfTasksPageableTO()
                        .paged(true)
                        .unpaged(false)
                        .pageNumber(pagingAndSorting.getPageIndex())
                        .pageSize(pagingAndSorting.getPageSize())
                        .sort(new PageOfTasksPageableSortTO()
                                .sorted(sortRequested)
                                .unsorted(!sortRequested)
                                .empty(empty)
                        )
                )
                .page(pagingAndSorting.getPageIndex())
                .content(tasks)
                .size(pagingAndSorting.getPageSize())
                .numberOfElements(tasks.size())
                .totalPages(totalPages)
                .totalElements(domain.getTotalElementsCount())
                .empty(empty)
                .first(pagingAndSorting.getPageIndex() == 0)
                .last(pagingAndSorting.getPageIndex() == totalPages - 1);
    }
}
