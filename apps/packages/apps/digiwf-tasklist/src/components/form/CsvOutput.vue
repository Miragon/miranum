<template>
  <div>
    <fieldset class="group">
      <legend>
        <span>{{ label }}</span>
      </legend>
      <a
        :href="decodedValue"
        :download="getFilename"
        target="_blank"
      >{{ getFilename }}</a>
    </fieldset>
  </div>
</template>

<style scoped>

.group {
  border: 1px solid rgba(0, 0, 0, 0.38);
  border-radius: 5px;
  margin-top: -10px;
  text-align: left;
  vertical-align: center;
}

.group > legend {
  color: rgba(0, 0, 0, 0.6);
  font-size: small;
  margin-left: 8px;
}

.group > a {
  margin: 6px 0 18px 10px;
  display: block;
}

</style>

<script lang="ts">
import {Component, Prop, Vue} from "vue-property-decorator";
import {DateTime} from "luxon";

@Component
export default class CsvOutput extends Vue {

  @Prop()
  description: string | undefined;

  @Prop()
  value: string | undefined;

  @Prop()
  label: string | undefined;

  get getFilename(): string {
    const formattedDate = DateTime.local().toFormat('yyyyMMdd_HHmmss');
    return formattedDate + "_" + this.label + ".csv";
  }

  get decodedValue(): string {
    if (this.value) {
      return "data:application/octet-stream;charset=utf-8;base64," + this.value;
    }
    return "";
  }

}
</script>

