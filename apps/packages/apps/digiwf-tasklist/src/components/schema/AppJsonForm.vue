<template>
  <v-form
    ref="form">
    <dwf-form-renderer
      :options="{locale : 'de', readOnly: readonly || false, markdownit: { breaks: true } }"
      :value="value"
      :schema="schema"
      @input="input"
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
    <v-flex class="d-flex" style="width: 100%">
      <v-spacer/>
      <v-btn
        class="mt-5"
        color="primary"
        :disabled="isCompleting || readonly"
        @click="complete">
        {{ buttonText || "Abschließen" }}
      </v-btn>
    </v-flex>
  </v-form>
</template>

<script lang="ts">
import {Component, Emit, Prop, Vue} from "vue-property-decorator";

@Component
export default class AppJsonForm extends Vue {

  currentValue: any = {};

  @Prop()
  buttonText: string | undefined;

  @Prop()
  value: any;

  @Prop()
  schema: any;

  @Prop()
  readonly!: boolean;

  @Prop()
  isCompleting: boolean | undefined;

  @Emit("complete-form")
  completeForm(value: any): any {
    return value;
  }

  @Emit("input")
  input(value: any): any {
    this.currentValue = value;
    return value;
  }

  complete(): void {
    if ((this.$refs.form as HTMLFormElement).validate()) {
      this.completeForm(this.currentValue);
    }
  }

  created() {
    this.currentValue = this.value;
  }

}
</script>
