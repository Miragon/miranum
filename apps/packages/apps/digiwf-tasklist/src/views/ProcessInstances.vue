<template>
  <app-view-layout>
    <div>
      <v-flex>
        <h1>Aktuelle Vorgänge</h1>
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
          label="Aufgaben durchsuchen"
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
            @click="loadMyProcessInstances(true)"
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
      <app-pageable-list
        :items="filteredProcessInstances"
        :totalNumberOfItems="numberOfProcessInstances"
        found-data-text="Vorgänge gefunden"
        no-data-text="Keine laufenden Vorgänge gefunden"
      >
        <template #default="props">
          <template v-for="item in props.items">
            <process-instance-item
              :key="item.id"
              :item="item"
              :search-string="filter || ''"
            />
          </template>
        </template>
      </app-pageable-list>
    </div>
  </app-view-layout>
</template>

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

<script lang="ts">
import {Component, Vue} from 'vue-property-decorator';
import AppToast from "@/components/UI/AppToast.vue";
import TaskItem from "@/components/task/TaskItem.vue";
import AppViewLayout from "@/components/UI/AppViewLayout.vue";
import {FilterTO, SaveFilterTO, ServiceInstanceTO} from '@miragon/digiwf-engine-api-internal';
import AppPageableList from "@/components/UI/AppPageableList.vue";
import ProcessInstanceItem from "@/components/process/ProcessInstanceItem.vue";
import {
  deletePersistentFilterForNonHookCompatibleFunction,
  getPersistentFilterForNonHookCompatibleFunction,
  savePersistentFilterForNonHookCompatibleFunction
} from "../middleware/persistentFilter/persistentFilters";

@Component({
  components: {ProcessInstanceItem, AppPageableList, TaskItem, AppToast, AppViewLayout}
})
export default class ProcessInstances extends Vue {

  processInstances: ServiceInstanceTO[] = [];
  numberOfProcessInstances: number = 0;
  isLoading = false;
  filter = "";
  errorMessage = "";
  persistentFilters: FilterTO[] = [];

  get filteredProcessInstances(): ServiceInstanceTO[] {
    this.processInstances = this.$store.getters['processInstances/processInstances'] || [];
    if (!this.filter) {
      return this.processInstances;
    }
    return this.processInstances.filter(task => JSON.stringify(Object.values(task)).toLocaleLowerCase().includes(this.filter.toLocaleLowerCase()));
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
      ) != undefined
    );
  }

  created(): void {
    this.loadMyProcessInstances();
    this.loadFilter();
    this.loadPersistentFilters();
  }

  async loadMyProcessInstances(refresh = false): Promise<void> {
    this.processInstances = this.$store.getters['processInstances/processInstances'];
    this.numberOfProcessInstances = this.processInstances.length
    this.isLoading = true;
    const startTime = new Date().getTime();
    try {
      await this.$store.dispatch('processInstances/getProcessInstances', refresh);
      this.errorMessage = "";
    } catch (error) {
      this.errorMessage = error.message;
    }
    setTimeout(() => this.isLoading = false, Math.max(0, 500 - (new Date().getTime() - startTime)));
  }

  loadFilter(): void {
    this.filter = this.$route.query.filter as string ?? "";
    if (!this.filter) {
      this.filter = this.$store.getters["processInstances/filter"];
      this.$router.replace({query: {filter: this.filter}});
    }
  }

  onFilterChanged(event: Event) {
    const el = event.target as HTMLInputElement
    this.filter = el.value;
    this.$store.commit('processInstances/setFilter', this.filter);
    this.$router.replace({query: {filter: el.value}})
  }

  async savePersistentFilter() {
    if (!this.filter) {
      return;
    }
    const request: SaveFilterTO = {
      pageId: "processinstances",
      filterString: this.filter,
    }
    try {
      await savePersistentFilterForNonHookCompatibleFunction(request)
      this.errorMessage = "";
    } catch (error) {
      this.errorMessage = 'Der Filter konnte nicht gespeichert werden.';
    }
  }

  async deletePersistentFilter() {
    const id = this.persistentFilters!.find((f: FilterTO) => f.filterString == this.filter)?.id!
    try {
      await deletePersistentFilterForNonHookCompatibleFunction(id);
      this.errorMessage = "";
    } catch (error) {
      this.errorMessage = 'Der Filter konnte nicht gelöscht werden.';
    }
  }

  async loadPersistentFilters(refresh = false): Promise<void> {
    try {
      const serverSideFilters = await getPersistentFilterForNonHookCompatibleFunction() || [];
      this.persistentFilters = serverSideFilters.filter((filter: FilterTO) => filter.pageId === "processinstances");
      this.errorMessage = "";
    } catch (error) {
      this.errorMessage = error.message;
    }
  }
}
</script>
