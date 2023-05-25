<template>
  <v-list-item :to="{ path: path }">
    <v-list-item-content class="itemContent">
      <v-list-item-title class="itemTitle">
        <span>{{ label }}</span>
        <span v-if="!!numberOfElements" class="counter grey--text text--darken-2">{{ numberOfElements }}</span>
      </v-list-item-title>
    </v-list-item-content>
  </v-list-item>
</template>

<script>
export default {
  name: "AppMenuItem",
  props: {
    label: {
      type: String,
      required: true
    },
    path: {
      type: String,
      required: true
    },
    numberOfElements: {
      type: Number,
      required: false
    }
  }
}
</script>

<style scoped>
.itemContent {
  margin: 5px 20px;
}

.counter {
  font-size: 0.9rem;
  font-weight: bold;
}

.itemTitle {
  display: flex;
  justify-content: space-between;
  align-items: flex-end;
  flex-direction: row;
}

</style>
