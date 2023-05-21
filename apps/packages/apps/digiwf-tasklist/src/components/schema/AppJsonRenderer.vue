<template>
  <v-form
    ref="form">
    <dwf-form-renderer
      :options="{locale : 'de', readOnly: readonly || false, markdownit: { breaks: true } }"
      :value="value"
      :schema="schema"
    >
      <template #custom-date-input="context">
        <dwf-date-input v-bind="context"/>
      </template>
      <template #custom-user-input="context">
        <v-user-input v-bind="context"/>
      </template>
      <template #custom-multi-user-input="context">
        <v-multi-user-input v-bind="context"/>
      </template>
      <template #custom-multi-file-input="context">
        <dwf-multi-file-input
          v-bind="context"
          :readonly="readonly"
        />
      </template>
    </dwf-form-renderer>
  </v-form>
</template>

<script lang="ts">
import {Component, Emit, Prop, Vue} from "vue-property-decorator";

@Component
export default class AppJsonRenderer extends Vue {

  @Prop()
  value: any;

  @Prop()
  schema: any;

  @Prop()
  readonly!: boolean;

  @Emit("input")
  input(value: any): any {
    return value;
  }


}
</script>
