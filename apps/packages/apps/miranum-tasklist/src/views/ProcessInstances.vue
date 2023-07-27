<template>
  <app-view-layout>
    <div>
      <v-flex>
        <h1>Aktuelle Vorgänge</h1>
      </v-flex>
      <v-flex class="d-flex justify-space-between align-center searchField">
        <!-- input.native to prevent this issue: https://github.com/vuetifyjs/vuetify/issues/4679 -->
        <search-field
          :on-filter-change="onFilterChanged"
        />
        <div class="d-flex align-center">
          <v-btn
            aria-label="Vorgänge aktualisieren"
            style="padding-left: 13px;"
            large
            text
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
      <v-flex class="mt-10">
        <v-flex class="tableHeader">
          <v-flex class="headerTitel">
            Vorgang
          </v-flex>
          <v-flex
            class="headerTitel"
            style="max-width: 145px"
          >
            Status
          </v-flex>
          <v-flex
            class="headerTitel"
            style="max-width: 100px"
          >
            Erstellt am
          </v-flex>
        </v-flex>
        <hr style="margin: 5px 0 0 0">
      </v-flex>
      <v-list>
        <template v-for="item in data?.content || []">
          <process-instance-item
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
import ProcessInstanceItem from "@/components/process/ProcessInstanceItem.vue";
import {defineComponent, watch} from "vue";
import {useGetPaginationData} from "../middleware/paginationData";
import {useGetProcessInstances} from "../middleware/processInstances/processInstancesMiddleware";
import AppPaginationFooter from "../components/UI/AppPaginationFooter.vue";
import SearchField from "../components/common/SearchField.vue";

export default defineComponent({
    components: {
      SearchField,
      AppPaginationFooter, ProcessInstanceItem, AppToast, AppViewLayout
    },
    props: [],
    setup: () => {
      const {searchQuery, setSearchQuery, page, size, setSize, setPage} = useGetPaginationData();

      const {isLoading, data, error: errorMessage, refetch} = useGetProcessInstances(page, size, searchQuery);

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
  }
);

</script>

<style scoped>

.tableHeader {
  display: flex;
  margin: 0.5rem 50px 0 12px;

}

.headerTitel {
  margin: 0 5px;
  font-size: 0.9rem;
  font-weight: bold;
}

.searchField {
  margin: 1rem 0 1rem 0;
}
</style>
