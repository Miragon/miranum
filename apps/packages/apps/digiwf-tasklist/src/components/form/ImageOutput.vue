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
      class="group"
    >
      <legend> {{ label }}</legend>
      <v-img
        :src="imageValue"
        contain
        :height="imageHeight"
        :width="imageWidth"
        :alt="description"
      />
      <div class="subtext">
        {{ description }}
      </div>
    </fieldset>
  </div>
</template>

<style scoped>

.group {
  border: 1px solid rgba(0, 0, 0, 0.38);
  border-radius: 5px;
  margin-top: -10px;
  margin-bottom: 30px;
  display: block;
  background-color: #f5f5f5;
}

.group > legend {
  color: rgba(0, 0, 0, 0.6);
  font-size: small;
  margin-left: 8px;
}

.group > .v-image {
  margin: 0px auto 0px auto;
}

.subtext {
  text-align: center;
}
</style>

<script lang="ts">
import {Component, Prop, Vue} from "vue-property-decorator";
import {FileTO} from "@/types/File";


@Component
export default class ImageOutput extends Vue {

  @Prop()
  description: string | undefined;

  @Prop()
  value: FileTO | undefined;

  @Prop()
  label: string | undefined;

  @Prop()
  imageHeight: string | undefined;

  @Prop()
  imageWidth: string | undefined;

  get imageValue(): string {
    if (!this.value) {
      return "";
    }

    return "data:" + this.value.fileType + ";base64, " + this.value.data;
  }

}
</script>

