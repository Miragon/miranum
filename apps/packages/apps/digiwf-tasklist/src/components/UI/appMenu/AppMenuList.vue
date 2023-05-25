<template>
    <v-list>
      <AppMenuItem path="/mytask" label="Meine Aufgaben" :number-of-elements="numberOfMyTasks"/>
      <hr class="hrDividerMenu">
      <AppMenuItem path="/instance" label="Aktuelle Vorgänge" :number-of-elements="numberOfProcessInstances"/>
      <hr class="hrDividerMenu">
      <AppMenuItem path="/process" label="Vorgang Starten"/>
      <hr
        class="hrDividerMenu"
        style="margin-bottom: 60px"
      >
      <p class="grey--text ml-9 mt-5 mb-0">
        Gruppenaufgaben
      </p>
      <AppMenuItem path="/opengrouptask" label="Offen" :number-of-elements="numberOfOpenGroupTasks"/>
      <hr class="hrDividerMenu">
      <AppMenuItem path="/assignedgrouptask" label="In Bearbeitung" :number-of-elements="numberOfAssignedGroupTasks"/>
      <hr class="hrDividerMenu">
    </v-list>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import {useNumberOfTasks} from "@/middleware/tasks/taskMiddleware";
import AppMenuItem from "./AppMenuItem.vue";

export default defineComponent({
  components: {AppMenuItem},
  props: ["numberOfProcessInstances"],
  setup() {
    const numberOfTasks = useNumberOfTasks();
    return {
      numberOfMyTasks: numberOfTasks.myTasks,
      numberOfOpenGroupTasks: numberOfTasks.openGroupTasks,
      numberOfAssignedGroupTasks: numberOfTasks.assignedGroupTasks,
    }
  },
})
</script>

<style scoped>

</style>
