<template>
  <div>
    <v-list-item
      :aria-label="'Vorgang '+item.definitionName+ ' öffnen'"
      :to="'/instance/'+item.id"
      class="d-flex justify-space-between"
    >
      <v-flex
        class="d-flex flex-column processColumn"
        style="min-height: 4.5rem; max-height: 6rem; margin: 8px 0"
      >
        <h2 class="processTitle">
          <text-highlight :queries="searchString">
            {{ item.definitionName }}
          </text-highlight>
        </h2>
        <span>
          <text-highlight :queries="searchString"> {{ item.description }} </text-highlight>
        </span>
      </v-flex>
      <v-flex
        class="processColumn"
        style="min-width: 150px; max-width: 150px"
      >
        <span>
          <text-highlight :queries="searchString"> {{ item.status }}</text-highlight>
        </span>
      </v-flex>
      <v-flex
        class="processColumn"
        style="min-width: 100px; max-width: 100px"
      >
        <span class="taskInfo">
          {{ createdAt }}
        </span>
      </v-flex>
      <div
        class="ml-2"
        style="min-width: 30px; max-width: 30px"
      >
        <v-menu
          offset-x
          top
        >
          <template #activator="{ on, attrs }">
            <v-btn
              v-on.prevent="on"
              icon
              v-bind="attrs"
              @click="(event) => { event.preventDefault()}"
            >
              <v-icon>mdi-dots-vertical</v-icon>
            </v-btn>
          </template>
          <v-list>
            <v-list-item
              :to="'/instance/'+item.id"
              link
              @click="(event) => { event.preventDefault()}"
            >
              <v-list-item-title>Anzeigen</v-list-item-title>
            </v-list-item>
          </v-list>
        </v-menu>
      </div>
    </v-list-item>
    <hr class="hrDivider">
  </div>
</template>

<style scoped>

.processColumn {
  margin: 0 0 0 5px;
  align-self: baseline;
  overflow: hidden;
}

.taskInfo {
  font-size: 0.9rem;
  display: flex;
  align-items: center;
  margin-bottom: 0.3rem;
}

.processTitle {
  font-size: 1.2rem;
}
</style>

<script lang="ts">
import {Component, Emit, Prop, Vue} from "vue-property-decorator";
import {ServiceInstanceTO} from '@miragon/digiwf-engine-api-internal';
import {DateTime} from "luxon";

@Component
export default class ProcessDefinitionItem extends Vue {

  @Prop()
  item!: ServiceInstanceTO;

  @Prop()
  searchString!: string;

  get createdAt(): string {
    return DateTime.fromISO(this.item.startTime!).toLocaleString(DateTime.DATETIME_SHORT);
  }

  @Emit("on-click")
  onClick(): string {
    return this.item.id!;
  }

}
</script>
