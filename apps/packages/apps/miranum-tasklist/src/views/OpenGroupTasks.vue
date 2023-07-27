<template>
  <app-view-layout>
    <task-list
      view-name="Offene Gruppenaufgaben"
      description="Hier sehen Sie alle Aufgaben Ihrer Gruppe. Klicken Sie auf bearbeiten, um sich eine Aufgabe zu nehmen."
      :tasks="data?.content || []"
      :is-loading="isLoading"
      :errorMessage="errorMessage"
      :filter="filter"
      @loadTasks="reloadTasks"
      @changeFilter="onFilterChange"

    >
      <template #default="props">
        <group-task-item
          :key="props.item.id"
          :task="props.item"
          :search-string="props.item.searchInput"
          @edit="assignTask(props.item.id)"
        />
        <hr class="hrDivider">
      </template>
    </task-list>
    <AppPaginationFooter
      found-data-text="Vorgänge gefunden"
      :size="pagination.size?.value || 20"
      :on-size-change="pagination.onSizeChange"
      :last-page="pagination.lastPage"
      :last-page-button-disabled="pagination.isLastPageButtonDisabled()"
      :next-page="pagination.nextPage"
      :total-number-of-items="data?.totalElements || 0"
      :next-page-button-disabled="pagination.isNextPageButtonDisabled()"
      :number-of-pages="data?.totalPages || 1"
      :page="pagination.getCurrentPageLabel()"
      :update-items-per-page="pagination.updateItemsPerPage"
    />
  </app-view-layout>
</template>

<style scoped>

</style>

<script lang="ts">
import {defineComponent, watch} from "vue";
import {useRouter} from "vue-router/composables";
import {useAssignTaskMutation, useOpenGroupTasksQuery} from "../middleware/tasks/taskMiddleware";
import {usePageId} from "../middleware/pageId";
import {useGetPaginationData} from "../middleware/paginationData";

export default defineComponent({
  setup() {
    const router = useRouter();
    const pageId = usePageId();
    const {searchQuery, size, page, setSize, setPage, setSearchQuery} = useGetPaginationData();

    const {isLoading, data, error, refetch} = useOpenGroupTasksQuery(page, size, searchQuery);
    const assignMutation = useAssignTaskMutation();

    const reloadTasks = (): void => {
      refetch()
    };

    watch(page, (newPage) => {
      setPage(newPage);
      reloadTasks();
    })
    watch(size, (newSize) => {
      setSize(newSize)
      reloadTasks();
    })

    const assignTask = async (id: string): Promise<void> => {
      assignMutation.mutateAsync(id).then(() => router.push({path: '/task/' + id}))
    }

    return {
      assignTask,
      pageId,
      isLoading,
      errorMessage: error || assignMutation.error,
      data,
      filter: searchQuery,
      reloadTasks,
      pagination: {
        page,
        size,
        onSizeChange: setSize,
        getCurrentPageLabel: () => page.value + 1,
        setPage,
        lastPage: () => {
          if (page.value === 0) {
            return;
          }
          setPage(page.value - 1)
          refetch()
        },
        nextPage: () => {
          const totalPages = data.value?.totalPages;
          if (!totalPages || page.value === totalPages - 1) {
            return;
          }
          setPage(page.value + 1);
          refetch();
        },
        isLastPageButtonDisabled: () => page.value === 0,
        isNextPageButtonDisabled: () => page.value + 1 >= (data.value?.totalPages || 0),
        updateItemsPerPage: setSize
      },
      onFilterChange: (newFilter: string | undefined) => {
        setSearchQuery(newFilter || "");
        reloadTasks();
      },
    }
  }
});
</script>
