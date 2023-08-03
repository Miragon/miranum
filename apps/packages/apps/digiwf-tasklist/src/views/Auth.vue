<template>
  <div></div>
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
        await service.$auth.initUserManager()
        const user = await service.$auth.signinCallback()
        console.log(user)
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
