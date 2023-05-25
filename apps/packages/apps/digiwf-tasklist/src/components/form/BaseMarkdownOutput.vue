<template>
  <v-col
    class="pa-0"
    :cols="col"
  >
    <p>
      <div v-html="markdownToHtml"></div>
    </p>
  </v-col>
</template>

<style scoped>

</style>

<script lang="ts">
import {Component, Prop, Vue} from "vue-property-decorator";
import {VAutocomplete} from "vuetify/lib";
import {marked} from 'marked';

@Component({
  components: {
    VAutocomplete
  }
})
export default class BaseMarkdownOutput extends Vue {

  @Prop()
  col: string | undefined;

  @Prop()
  description: string | undefined;

 get markdownToHtml(){
    return marked(this.description || '');
  }

}
</script>

