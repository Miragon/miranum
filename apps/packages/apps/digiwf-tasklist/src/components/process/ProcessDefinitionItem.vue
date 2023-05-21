<template>
  <div>
    <v-list-item
      :aria-label="'Vorgang '+item.name+ ' starten'"
      :to="'/process/'+item.key"
      class="d-flex justify-space-between"
    >
      <v-flex
        class="d-flex flex-column"
        style="height: 5rem; margin: 15px 0"
      >
        <h2 class="processTitle">
          <text-highlight :queries="searchString">
            {{ item.name }}
          </text-highlight>
        </h2>
        <p>
          <text-highlight :queries="searchString">
            {{ item.description }}
          </text-highlight>
        </p>
      </v-flex>
      <v-flex
        class="d-flex justify-end align-center ml-2"
        style="min-width: 50px"
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
              :aria-label="'Vorgang '+item.name+ ' starten'"
              :to="'/process/'+item.key"
              link
              @click="(event) => { event.preventDefault()}"
            >
              <v-list-item-title>Starten</v-list-item-title>
            </v-list-item>
          </v-list>
        </v-menu>
      </v-flex>
    </v-list-item>
    <hr class="hrDivider">
  </div>
</template>

<style scoped>

.processTitle {
  font-size: 1.2rem;
}

</style>

<script lang="ts">
import {Component, Emit, Prop, Vue} from "vue-property-decorator";
import {ServiceDefinitionTO} from '@miragon/digiwf-engine-api-internal';

@Component
export default class ProcessDefinitionItem extends Vue {

  @Prop()
  item!: ServiceDefinitionTO;

  @Prop()
  searchString!: string;

  @Emit("on-click")
  onClick(): string {
    return this.item.key!;
  }

}
</script>
