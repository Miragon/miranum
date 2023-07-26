package io.miranum.platform.tasklist.adapter.in.rest.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.holunda.polyflow.view.Task;
import io.miranum.platform.tasklist.adapter.in.rest.model.*;
import io.miranum.platform.tasklist.domain.JsonSchema;
import io.miranum.platform.tasklist.domain.PageOfTasksWithSchema;
import io.miranum.platform.tasklist.domain.TaskSchemaType;
import io.miranum.platform.tasklist.domain.legacy.Form;
import lombok.val;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.lang.NonNull;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = DateMapper.class)
public interface TaskMapper {

  /**
   * Mapper for Task TO, used in list operations.
   * @param task task from polyflow.
   * @param schemaRef schema reference.
   * @return Task TO.
   */
  @Mapping(target = "processName", source = "task.sourceReference.name")
  @Mapping(target = "schemaRef", source = "schemaRef")
  @Mapping(target = "schemaType", source = "schemaType")
  TaskTO to(Task task, String schemaRef, @NonNull TaskSchemaType schemaType);

  @Mapping(target = "processName", source = "task.sourceReference.name")
  @Mapping(target = "processInstanceId", source = "task.sourceReference.instanceId")
  @Mapping(target = "variables", source = "task.payload")
  @Mapping(target = "schemaRef", source = "schemaRef")
  @Mapping(target = "cancelable", source = "cancelable")
  @Mapping(target = "schemaType", source = "schemaType")
  TaskWithDetailsTO toWithDetails(Task task, String schemaRef, Boolean cancelable, @NonNull TaskSchemaType schemaType);

  @Mapping(target = "schemaId", source = "id")
  @Mapping(target = "schemaJson", source = "schema")
  TaskCombinedSchemaTO to(JsonSchema schema);


  @Mapping(target = "id", source = "task.id")
  @Mapping(target = "processName", source = "task.sourceReference.name")
  @Mapping(target = "processInstanceId", source = "task.sourceReference.instanceId")
  @Mapping(target = "variables", source = "task.payload")
  @Mapping(target = "createTime", source = "task.createTime")
  @Mapping(target = "description", source = "task.description")
  @Mapping(target = "name", source = "task.name")
  @Mapping(target = "assignee", source = "task.assignee")
  @Mapping(target = "followUpDate", source = "task.followUpDate")
  @Mapping(target = "schema", source = "schema")
  @Mapping(target = "cancelable", source = "cancelable")
  @Mapping(target = "schemaType", source = "schemaType")
  TaskWithSchemaTO toWithSchema(@Nonnull Task task, @Nonnull Map<String, Object> schema, @NonNull Boolean cancelable, @NonNull TaskSchemaType schemaType);


  @Mapping(target = "id", source = "task.id")
  @Mapping(target = "processName", source = "task.sourceReference.name")
  @Mapping(target = "processInstanceId", source = "task.sourceReference.instanceId")
  @Mapping(target = "variables", source = "task.payload")
  @Mapping(target = "createTime", source = "task.createTime")
  @Mapping(target = "description", source = "task.description")
  @Mapping(target = "name", source = "task.name")
  @Mapping(target = "assignee", source = "task.assignee")
  @Mapping(target = "followUpDate", source = "task.followUpDate")
  @Mapping(target = "schema", source = "form")
  @Mapping(target = "cancelable", source = "cancelable")
  @Mapping(target = "schemaType", source = "schemaType")
  TaskWithSchemaTO toWithSchema(@Nonnull Task task, @Nonnull Form form, @NonNull Boolean cancelable, @NonNull TaskSchemaType schemaType);

  default Map<String,Object> map(Form value) {
    val objectMapper = new ObjectMapper();
    val mapType = objectMapper.getTypeFactory().constructMapLikeType(Map.class, String.class, Object.class);
    return objectMapper.convertValue(value, mapType);
  }

  default TaskSchemaTypeTO toSchemaTO(TaskSchemaType taskSchemaType) {
    switch (taskSchemaType) {
      case SCHEMA_BASED:
        return TaskSchemaTypeTO.SCHEMA_BASED;
      default:
        return null;
    }
  }

  default PageOfTasksTO toSchemaTO(PageOfTasksWithSchema domain) {
    var pagingAndSorting = domain.getPagingAndSorting();
    var totalPages = Double.valueOf(Math.ceil(domain.getTotalElementsCount() / pagingAndSorting.getPageSize().doubleValue())).intValue();
    var tasks = domain.getTasks().stream().map(taskWithSchema -> this.to(
            taskWithSchema.getTask(),
            taskWithSchema.getSchemaRef(),
            taskWithSchema.getSchemaType()
    )).collect(Collectors.toList());
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