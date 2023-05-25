<template>
  <div>
    <Jsf @input="input" :value="value" :schema="currentSchema" :options="currentOptions">
      <template v-for="(index, name) in $scopedSlots" v-slot:[name]="data">
        <slot :name="name" v-bind="data"></slot>
      </template>
    </Jsf>
  </div>
</template>

<script lang="ts">
//@ts-ignore
import deepmerge from "deepmerge";
import {computed, defineComponent} from "vue";

export default defineComponent({
  props: ['options', 'buttonText', 'value', 'schema'],
  emits: ['input'],
  setup(props, {emit}) {

    const defaultOptions = {
      "editMode": "inline",
      "disableSorting": true,
      "sectionsClass": "pl-0 col-12 pb-0 pt-0 pr-0",
      "objectContainerClass": "pl-0 pb-0 pt-0 pr-0",
      dialogProps: {maxWidth: 1000},
      "timePickerProps": {
        "format": "24hr"
      },
      "messages": {
        "required": "Dieses Feld ist ein Pflichtfeld",
        "preview": "Vorschau",
        "mdeGuide": "Dokumentation",
        "continue": "weiter",
        "minItems": "Mindestens {minItems} Einträge",
        "maxItems": "Nicht mehr als {maxItems} Einträge"
      },
    };
    const formats = {
      "time": function (e: any, t: any) {
        const r = new Date("".concat((new Date).toISOString().split("T")[0], "T").concat(e));
        return new Date(r.getTime() + 6e4 * r.getTimezoneOffset()).toLocaleTimeString(t)
      }
    }
    const rules = {
      required: function (v: any) {
        return (!!v && v !== '' || v === 0) || 'Dieses Feld ist ein Pflichtfeld';
      },
      requiredObject: function (v: any) {
        return (!!v && (v.amount >= 1 || v.length >= 1)) || 'Dieses Feld ist ein Pflichtfeld';
      }
    };

    const currentSchema = computed(() => {
      if (props.options && props.options.readOnly) {
        return {
          ...props.schema,
          readOnly: true
        }
      }
      return props.schema
    })

    const currentOptions = computed(() => {
      return {
        rules: rules,
        formats: formats,
        ...deepmerge(defaultOptions, props.options),
      }
    })

    const input = (value: any) => {
      emit('input', value);
    };

    return {
      currentSchema,
      input,
      currentOptions
    }
  }
})

</script>

<style>
.v-input--is-disabled:not(.v-input--is-readonly) a {
  pointer-events: all !important;
}

.read-only .v-card__text {
  color: black !important;
}

.read-only fieldset {
  background: rgb(245, 245, 245);
}

.v-input {
  margin-right: 10px !important;
}

.read-only .v-label {
  color: #222 !important;
  font-size: 16px !important;
}

.vjsf-property > .v-input--hide-details {
  margin-bottom: 15px !important;
}

.read-only > .v-text-field.v-text-field--enclosed {
  margin-bottom: 15px !important;
}

.read-only > .row > .v-text-field.v-text-field--enclosed {
  margin-bottom: 15px !important;
}

.vjsf-property > .row:last-child > .col > .v-card {
  margin-bottom: 20px !important;
}

.theme--light.v-text-field--filled > .v-input__control > .v-input__slot {
  background-color: white !important;
}

.theme--light.v-text-field--filled > .v-input__control > .v-input__slot:hover {
  background-color: white !important;
}

.vjsf-array {
  margin: 6px !important;
}
</style>
