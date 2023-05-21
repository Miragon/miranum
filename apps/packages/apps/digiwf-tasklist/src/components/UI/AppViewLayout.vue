<template>
  <v-container class="viewContainer">
    <v-layout column>
      <slot />
    </v-layout>
  </v-container>
</template>

<style scoped>
.viewContainer {
  max-width: 1100px;
  padding-bottom: 0;
  position: relative;
  margin-right: auto;
  margin-left: 50px;
}
</style>

<script lang="ts">

import {Component, Vue} from "vue-property-decorator";

@Component
export default class AppViewLayout extends Vue {
}
</script>

