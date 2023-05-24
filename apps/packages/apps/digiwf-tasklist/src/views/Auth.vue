<template>
  <div>logged in</div>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import {useRouter} from "vue-router/composables";
import {useServices} from "../hooks/store";

export default defineComponent({
  props: [],
  setup() {
    const router = useRouter();
    const service = useServices();
    const authenticateOidc = async () => {
      try {
        console.log('authenticating')
        await service.$auth.signinCallback()
        router.push('/')
      } catch (error) {
        console.log(error)
      }
    }

    authenticateOidc();

    return {}
  }
});

</script>
