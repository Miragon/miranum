<template>
  <div>logged in</div>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import {useServices} from "../hooks/store";

export default defineComponent({
  props: [],
  setup() {
    const services = useServices();

    const logOutOidc = async () => {
      try {
        await services.$auth.logout()
      } catch (error) {
        console.log(error)
      }
    }

    logOutOidc();

    return {}
  }
});

</script>
