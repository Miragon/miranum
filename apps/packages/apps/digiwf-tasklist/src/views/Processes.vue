<template>
  <app-view-layout>
    <div>
      <v-flex>
        <h1>Vorgänge</h1>
      </v-flex>
      <v-flex class="d-flex justify-space-between align-center searchField">
        <!-- input.native to prevent this issue: https://github.com/vuetifyjs/vuetify/issues/4679 -->
        <v-combobox
          id="suchfeld"
          v-model="filter"
          :items="persistentFilters.map((f) => f.filterString)"
          clearable
          color="black"
          dense
          flat
          hide-details
          label="Vorgänge durchsuchen"
          outlined
          style="max-width: 500px"
          @input.native="onFilterChanged"
        >
          <template #append>
            <div class="v-input__icon">
              <v-btn
                v-if="isFilterPersistent"
                aria-label="Filter speichern"
                class="v-icon"
                icon
                @click="deletePersistentFilter()"
              >
                <v-icon color="primary"> mdi-star</v-icon>
              </v-btn>
              <v-btn
                v-else-if="filter"
                aria-label="Filter löschen"
                class="v-icon"
                icon
                @click="savePersistentFilter()"
              >
                <v-icon color="primary"> mdi-star-outline</v-icon>
              </v-btn>
            </div>
            <v-icon class="ml-2"> mdi-magnify</v-icon>
          </template>
        </v-combobox>

        <div class="d-flex align-center">
          <v-btn
            aria-label="Vorgänge aktualisieren"
            color="primary"
            large
            style="padding-left: 13px;"
            text
            @click="loadProcesses(true)"
          >
            <div style="min-width: 30px">
              <v-progress-circular
                v-if="isLoading"
                :size="25"
                color="primary"
                indeterminate
                width="2"
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
      <pageable-list
        :items="filteredProcesses"
        found-data-text="Vorgänge gefunden"
        no-data-text="Keine Vorgänge gefunden"
      >
        <template #default="props">
          <template v-for="item in props.items">
            <process-definition-item
              :key="item.key"
              :item="item"
              :search-string="filter || ''"
            />
          </template>
        </template>
      </pageable-list>
    </div>
  </app-view-layout>
</template>

<style scoped>


.searchField {
  margin: 1rem 0 1rem 0;
}
</style>

<script lang="ts">
import {Component, Vue} from 'vue-property-decorator';
import AppToast from "@/components/UI/AppToast.vue";
import TaskItem from "@/components/task/TaskItem.vue";
import AppViewLayout from "@/components/UI/AppViewLayout.vue";
import {FilterTO, SaveFilterTO, ServiceDefinitionTO} from '@miragon/digiwf-engine-api-internal';
import ProcessDefinitionItem from "@/components/process/ProcessDefinitionItem.vue";
import AppPageableList from "@/components/UI/AppPageableList.vue";
import {
  deletePersistentFilterForNonHookCompatibleFunction,
  getPersistentFilterForNonHookCompatibleFunction,
  savePersistentFilterForNonHookCompatibleFunction
} from "../middleware/persistentFilter/persistentFilters";

@Component({
  components: {PageableList: AppPageableList, ProcessDefinitionItem, TaskItem, AppToast, AppViewLayout}
})
export default class Processes extends Vue {

  processDefinitions: ServiceDefinitionTO[] = [];
  isLoading = false;
  filter = "";
  errorMessage = "";
  persistentFilters: FilterTO[] = [];

  get filteredProcesses(): ServiceDefinitionTO[] {
    this.processDefinitions = this.$store.getters['processDefinitions/processDefinitions'];
    if (!this.filter) {
      return this.processDefinitions;
    }
    return this.processDefinitions.filter(task => JSON.stringify(Object.values(task)).toLocaleLowerCase().includes(this.filter.toLocaleLowerCase()));
  }

  get isFilterPersistent(): boolean {
    if (
      !this.filter ||
      this.filter.length == 0 ||
      !this.persistentFilters ||
      this.persistentFilters!.length == 0
    ) {
      return false;
    }
    return (
      this.persistentFilters!.find(
        (fl: FilterTO) => fl.filterString == this.filter
      ) != undefined // can not work
    );
  }

  created(): void {
    this.loadProcesses();
    this.loadFilter();
    this.loadPersistentFilters();
  }

  async loadProcesses(refresh = false): Promise<void> {
    this.processDefinitions = this.$store.getters['processDefinitions/processDefinitions'];
    this.isLoading = true;
    const startTime = new Date().getTime();
    try {
      await this.$store.dispatch('processDefinitions/loadProcessDefinitions', refresh);
      this.errorMessage = "";
    } catch (error) {
      this.errorMessage = error.message;
    }
    setTimeout(() => this.isLoading = false, Math.max(0, 500 - (new Date().getTime() - startTime)));
  }

  loadFilter(): void {
    this.filter = this.$route.query.filter as string ?? "";
    if (!this.filter) {
      this.filter = this.$store.getters["processDefinitions/filter"];
      this.$router.replace({query: {filter: this.filter}});
    }
  }

  onFilterChanged(event: Event) {
    const el = event.target as HTMLInputElement
    this.filter = el.value;
    this.$store.commit('processDefinitions/setFilter', this.filter);
    this.$router.replace({path: "process", query: {filter: el.value}})
  }

  async savePersistentFilter() {
    if (!this.filter) {
      return;
    }
    const request: SaveFilterTO = {
      pageId: "processes",
      filterString: this.filter,
    }
    savePersistentFilterForNonHookCompatibleFunction(request)
  }

  deletePersistentFilter() {
    const id = this.persistentFilters!.find((f: FilterTO) => f.filterString == this.filter)?.id!
    deletePersistentFilterForNonHookCompatibleFunction(id)
  }

  async loadPersistentFilters(): Promise<void> {
    try {
      const filterResponse = await getPersistentFilterForNonHookCompatibleFunction();
      this.persistentFilters = filterResponse.filter((filter: FilterTO) => filter.pageId === "processes");
      this.errorMessage = "";
    } catch (error: any) {
      this.errorMessage = error.message;
    }
  }
}
</script>
