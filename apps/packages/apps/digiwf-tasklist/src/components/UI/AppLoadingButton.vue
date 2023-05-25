<template>
  <v-btn
    aria-label="Aufgabe abschließen"
    style="padding-left: 0.1rem; padding-right: 1.2rem"
    :color="color"
    :disabled="isLoading"
    @click="click"
  >
    <div class="buttonGroup">
      <v-icon
        v-if="hasError"
        small
        style="margin-right: 0.5rem; width: 0.7rem;"
        :color="loadingColor"
      >
        mdi-alert
      </v-icon>
      <v-progress-circular
        v-else
        style="margin-right: 0.5rem; width: 0.7rem;"
        :class="loadingClass"
        width="2"
        :color="loadingColor"
        indeterminate
      />
      <slot />
    </div>
  </v-btn>
</template>

<style scoped>

.buttonGroup {
  display: flex;
  justify-content: center;
  align-items: center;
}

.isNotLoading {
  visibility: hidden;
}

</style>

<script lang="ts">
import {Component, Emit, Prop, Vue} from "vue-property-decorator";

@Component
export default class AppLoadingButton extends Vue {

  @Prop()
  isLoading: boolean | undefined;

  @Prop()
  hasError: boolean | undefined;

  @Prop()
  buttonText!: string;

  @Prop()
  color!: string | undefined;

  get loadingClass(): string {
    return this.isLoading ? "" : "isNotLoading";
  }

  get loadingColor(): string {
    return this.color === "primary" ? "white" : "primary";
  }

  @Emit("on-click")
  onClick(): boolean {
    return true;
  }

  click(): void {

    this.onClick();
  }

}
</script>
