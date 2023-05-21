<template>
  <v-text-field
    v-model="dateValue"
    type="date"
    :label="label"
    :dense="dense"
    :outlined="outlined"
    :disabled="readOnly"
    :rules="rules"
    @change="onChange"
    >
    <template #append-outer>
      <v-tooltip v-if="description" left :open-on-hover="false">
        <template v-slot:activator="{ on }">
          <v-btn icon @click="on.click" @blur="on.blur" retain-focus-on-click>
            <v-icon> mdi-information</v-icon>
          </v-btn>
        </template>
        <div class="tooltip">{{ description }}</div>
      </v-tooltip>
    </template>
  </v-text-field>
</template>

<script lang="ts">
import {defineComponent, ref} from "vue";

export default defineComponent ({
  props: [
    'schema',
    'on'
  ],
  setup({schema, on}){
    const {title: label, readOnly, description, default: defaultValue} = schema;
    const {dense, outlined} = schema['x-props'];
    let rules: any[] = [];

    if(!!schema['x-rules']?.includes('required')){
      rules.push((v: string) => !!v || 'Dieses Feld ist ein Pflichtfeld');
    }

    const dateValue = ref(defaultValue);

    const onChange = () => {
      if(!!on?.input) {
        on.input(dateValue.value);
      }
    }

    return {
      label,
      description,
      dense,
      outlined,
      readOnly,
      dateValue,
      rules,
      onChange
    }
  }
})
</script>

<style scoped>

</style>
