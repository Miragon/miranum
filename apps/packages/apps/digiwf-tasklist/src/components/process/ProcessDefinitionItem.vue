<template>
  <div>
    <v-list-item
      :aria-label="'Vorgang '+item.name+ ' starten'"
      class="d-flex justify-space-between"
      :to="'/process/'+item.key"
      data-cy="process-definition-item"
      :data-element-key="item.key"
    >
      <v-flex
        class="d-flex flex-column"
        style="height: 5rem; margin: 15px 0"
      >
        <h2
          class="processTitle"
          data-cy="name"
        >
          <text-highlight :queries="searchString">
            {{ item.name }}
          </text-highlight>
        </h2>
        <p
          data-cy="description"
        >
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
          top
          offset-x
        >
          <template #activator="{ on, attrs }">
            <v-btn
              icon
              v-bind="attrs"
              data-cy="dropdown-menu-button"
              @click="(event) => { event.preventDefault()}"
              v-on.prevent="on"
            >
              <v-icon>mdi-dots-vertical</v-icon>
            </v-btn>
          </template>
          <v-list>
            <v-list-item
              :aria-label="'Vorgang '+item.name+ ' starten'"
              link
              :to="'/process/'+item.key"
              data-cy="start-button"
              :data-process-definition-key="item.key"
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

<script lang="ts">
import {PropType} from "vue";
import {ProcessDefinition} from "../../middleware/processDefinitions/processDefinitionMiddleware";

export default {
  props: {
    item: {
      type: Object as PropType<ProcessDefinition>,
      required: true
    },
    searchString: {
      type: String,
      default: ""
    }
  },
  emits: {
    click: {
      type: Function as PropType<(id: string) => void>
    }
  }
};
</script>

<style scoped>

.processTitle {
  font-size: 1.2rem;
}

</style>
