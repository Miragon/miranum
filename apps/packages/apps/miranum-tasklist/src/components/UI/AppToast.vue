<template>
  <v-alert
    prominent
    style="padding: 0.5rem 1rem"
    :type="type"
  >
    <v-flex class="d-flex align-center justify-space-between">
      {{ message }}
    </v-flex>
  </v-alert>
</template>

<style scoped>
</style>

<script lang="ts">
import {Component, Prop, Vue} from "vue-property-decorator";

@Component
export default class AppToast extends Vue {

  @Prop()
  message!: string;

  @Prop()
  type!: 'error' | 'info'

}
</script>
