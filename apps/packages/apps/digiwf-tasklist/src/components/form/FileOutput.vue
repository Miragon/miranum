<template>
  <div style="position: relative">
    <image-output
      v-if="isImage === true"
      v-bind="$attrs"
      :value="value"
    />

    <pdf-output
      v-else
      v-bind="$attrs"
      :value="value"
    />
  </div>
</template>

<style scoped>

</style>

<script lang="ts">
import {Component, Prop, Vue} from "vue-property-decorator";
import {FileTO} from "@/types/File";
import ImageOutput from "@/components/form/ImageOutput.vue";
import PdfOutput from "@/components/form/PdfOutput.vue";


@Component({
  components: {ImageOutput, PdfOutput},
})
export default class FileOutput extends Vue {

  @Prop()
  value: FileTO | undefined;

  get isImage(): boolean {

    if (!this.value) {
      return false;
    }

    if (this.value.fileType === "image/png") {
      return true;
    }

    if (this.value.fileType === "image/jpeg") {
      return true;
    }

    return false;
  }


}
</script>

