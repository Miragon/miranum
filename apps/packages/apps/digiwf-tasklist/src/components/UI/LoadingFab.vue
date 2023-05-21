<template>
  <v-tooltip bottom>
    <template #activator="{ on, attrs }">
      <v-btn
        :aria-label="buttonText"
        fab
        :color="color"
        :disabled="disabled"
        v-bind="attrs"
        @click="click"
        v-on="on"
      >
        <div class="buttonGroup">
          <v-progress-circular
            v-if="isLoading"
            :class="loadingClass"
            :color="loadingColor"
            indeterminate
          />
          <slot v-else />
        </div>
      </v-btn>
    </template>
    <span> {{ buttonText }} </span>
  </v-tooltip>
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
export default class LoadingFab extends Vue {
  @Prop()
  isLoading: boolean | undefined;

  @Prop()
  hasError: boolean | undefined;

  @Prop()
  buttonText!: string;

  @Prop()
  color!: string | undefined;

  @Prop()
  disabled!: boolean | undefined;

  get loadingClass(): string {
    return this.isLoading ? "" : "isNotLoading";
  }

  get loadingColor(): string {
    return this.color === "secondary" ? "white" : "primary";
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
