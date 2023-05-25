<template>
  <div>
    <v-text-field
      v-if="!value"
      readonly
      outlined
      :label="label"
      value=" "
    />
    <fieldset
      v-else
      class="group mb-8"
    >
      <legend>
        <span>{{ label }}</span>
      </legend>
      <a
        :href="fileValue"
        :download="value.name"
        target="_blank"
      >{{ value.name }}</a>
    </fieldset>
  </div>
</template>

<style scoped>

.group {
  min-width: 0;
  left: 0;
  right: 0;
  border: 1px solid rgba(0, 0, 0, 0.38);
  border-radius: 5px;
  box-sizing: border-box;
  margin-top: -8px;
  padding-top: 4px;
  text-align: left;
  vertical-align: center;
  background-color: #f5f5f5;
}

.group > legend {
  color: rgba(0, 0, 0, 0.6);
  font-size: small;
  margin-left: 8px;
}

.group > a {
  max-width: 100%;
  display: block;
  white-space: nowrap;
  text-overflow: ellipsis;
  overflow: hidden;
  margin: 4px 0 13px 10px;
}

</style>

<script lang="ts">
import {Component, Prop, Vue} from "vue-property-decorator";
import {FileTO} from "@/types/File";

@Component
export default class PdfOutput extends Vue {

  @Prop()
  description: string | undefined;

  @Prop()
  value: FileTO | undefined;

  @Prop()
  label: string | undefined;

  get fileValue(): string {
    if (!this.value) {
      return "";
    }
    return "data:" + this.value.fileType + ";base64, " + this.value.data;
  }

}
</script>

