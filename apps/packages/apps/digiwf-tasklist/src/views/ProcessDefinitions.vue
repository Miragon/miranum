<template>
  <app-view-layout>
    <div>
      <v-flex>
        <h1>Vorgänge</h1>
      </v-flex>
      <v-flex class="d-flex justify-space-between align-center searchField">
        <!-- input.native to prevent this issue: https://github.com/vuetifyjs/vuetify/issues/4679 -->

        <search-field
          :on-filter-change="onFilterChanged"
        />
        <div class="d-flex align-center">
          <v-btn
            aria-label="Vorgänge aktualisieren"
            text
            style="padding-left: 13px;"
            large
            color="primary"
            @click="refetch"
          >
            <div style="min-width: 30px">
              <v-progress-circular
                v-if="isLoading"
                :size="25"
                width="2"
                color="primary"
                indeterminate
              />
              <v-icon
                v-else
              >
                mdi-refresh
              </v-icon>
            </div>
            Aktualisieren
          </v-btn>
        </div>
      </v-flex>
      <v-flex v-if="errorMessage">
        <AppToast
          :message="errorMessage"
          type="error"
        />
      </v-flex>

      <v-list>
        <template v-for="item in data?.content || []">
          <process-definition-item
            :key="item.key"
            :item="item"
            :search-string="searchQuery || ''"
          />
        </template>
      </v-list>

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
    </div>
  </app-view-layout>
</template>

<script lang="ts">
import AppToast from "@/components/UI/AppToast.vue";
import AppViewLayout from "@/components/UI/AppViewLayout.vue";
import ProcessDefinitionItem from "@/components/process/ProcessDefinitionItem.vue";
import {defineComponent, watch} from "vue";
import {useGetPaginationData} from "../middleware/paginationData";
import SearchField from "../components/common/SearchField.vue";
import {useGetProcessDefinitions} from "../middleware/processDefinitions/processDefinitionMiddleware";
import AppPaginationFooter from "../components/UI/AppPaginationFooter.vue";

export default defineComponent({
  components: {
    AppPaginationFooter,
    SearchField, ProcessDefinitionItem, AppToast, AppViewLayout
  },
  props: [],
  setup: () => {

    const {searchQuery, setSearchQuery, page, size, setSize, setPage} = useGetPaginationData();

    const {isLoading, data, error: errorMessage, refetch} = useGetProcessDefinitions(page, size, searchQuery);

    watch(page, (newPage) => {
      setPage(newPage);
      refetch();
    });
    watch(size, (newSize) => {
      setSize(newSize);
      refetch();
    });

    const onFilterChanged = (value: string) => {
      setSearchQuery(value);
      refetch();
    };

    return {
      data,
      isLoading,
      searchQuery,
      errorMessage,
      onFilterChanged,
      refetch,
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
          setPage(page.value - 1);
          refetch();
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
    };
  }
});
</script>

<style scoped>
.searchField {
  margin: 1rem 0 1rem 0;
}
</style>
