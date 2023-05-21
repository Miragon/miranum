<template>
    <v-row
      class="ma-1 mt-3"
      align="center"
      justify="center"
    >
      <span>Seitengröße</span>
      <v-menu offset-y>
        <template #activator="{ on, attrs }">
          <v-btn
            text
            outlined
            color="primary"
            class="ml-2 pa-1"
            v-bind="attrs"
            v-on="on"
          >
            <span class="mr-1">{{ size }}</span>
            <v-icon>mdi-chevron-down</v-icon>
          </v-btn>
        </template>
        <v-list>
          <v-list-item
            v-for="(number, index) in itemsPerPageArray"
            :key="index"
            @click="updateItemsPerPage(number)"
          >
            <v-list-item-title>
              {{ number }}
            </v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>
      <v-spacer/>
      <span class=" mr-1">{{ totalNumberOfItems }} {{ foundDataText }}
        </span>
      <v-spacer/>

      <span
        class="mr-4"
      >
          Seite {{ page }} von {{ numberOfPages }}
        </span>
      <v-tooltip
        top
        open-delay="700"
      >
        <template #activator="{ on, attrs }">
          <v-btn
            :disabled="lastPageButtonDisabled"
            small
            text
            color="primary"
            class="mr-1"
            v-bind="attrs"
            v-on="on"
            @click="lastPage"
          >
            <v-icon>mdi-chevron-left</v-icon>
          </v-btn>
        </template>
        <span>Vorherige Seite</span>
      </v-tooltip>
      <v-tooltip
        top
        open-delay="700"
      >
        <template #activator="{ on, attrs }">
          <v-btn
            small
            text
            :disabled="nextPageButtonDisabled"
            color="primary"
            class="ml-1"
            v-bind="attrs"
            v-on="on"
            @click="nextPage"
          >
            <v-icon>mdi-chevron-right</v-icon>
          </v-btn>
        </template>
        <span>Nächste Seite</span>
      </v-tooltip>
    </v-row>
</template>

<script lang="ts">
import {defineComponent} from "vue";

export default defineComponent({
  props: {
    numberOfPages: {type: Number, required: true},
    totalNumberOfItems: {type: Number, required: true},
    page: {type: Number, required: true},
    size: {type: Number, required: true},
    foundDataText: {type: String, required: true},
    nextPageButtonDisabled: {type: Boolean, required:true},
    lastPageButtonDisabled: {type: Boolean, required:true},
    updateItemsPerPage: {type: Function, required: true},
    nextPage: {type: Function, required: true},
    lastPage: {type: Function, required: true},
  },
  components: {},
  setup() {
    return {
      itemsPerPageArray: [5, 10, 20]
    }
  }
})

</script>

<style scoped>

</style>
