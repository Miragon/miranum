<template>
  <v-snackbar
    id="snackbar"
    v-model="show"
    :color="color"
    :timeout="timeout"
  >
    {{ message }}
    <v-btn
      v-if="color === 'error'"
      color="primary"
      text
      @click="show = false"
    >
      Schließen
    </v-btn>
  </v-snackbar>
</template>

<script lang="ts">
import {Component, Vue, Watch} from 'vue-property-decorator';

@Component
export default class TheSnackbar extends Vue {

  static defaultTimeout = 5000

  show = false
  timeout: number = TheSnackbar.defaultTimeout
  message = ''
  color = 'info'

  @Watch('$store.state.snackbar.message')
  setMessage(): void {
    this.show = false;
    setTimeout(() => {
      this.show = true;
      this.message = this.$store.state.snackbar.message;
    }, 100);
  }

  @Watch('$store.state.snackbar.level')
  setColor(): void {
    this.color = this.$store.state.snackbar.level;
    if (this.color === 'error') {
      this.timeout = 0;
    } else {
      this.timeout = TheSnackbar.defaultTimeout;
    }
  }
}
</script>

