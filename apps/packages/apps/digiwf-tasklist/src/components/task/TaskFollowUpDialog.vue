<template>
  <v-dialog
    :key="value"
    :value="value"
    width="500"
    @click:outside="cancel"
  >
    <v-card>
      <v-card-title class="headline grey lighten-2">
        Wiedervorlage bearbeiten
      </v-card-title>
      <div class="ma-8">
        <p style="margin-bottom: 1rem">
          Die Aufgabe wird Ihnen zum gewählten Zeitpunkt wieder in "Meine Aufgaben" angezeigt.
        </p>
        <v-menu
          v-model="dateSelectionOpen"
          :close-on-content-click="false"
          :nudge-right="40"
          transition="scale-transition"
          offset-y
          min-width="auto"
        >
          <template #activator="{ on, attrs }">
            <v-text-field
              style="pointer-events: auto!important; background-color: white !important;"
              outlined
              clearable
              hide-details
              readonly
              :value="computedDateSelection"
              label="Wiedervorlage am"
              prepend-icon="mdi-calendar"
              v-bind="attrs"
              @input="updateDateSelection"
              v-on="on"
            />
          </template>
          <v-date-picker
            v-model="dateSelection"
            locale="de-de"
            @input="dateSelectionOpen = false"
          />
        </v-menu>
      </div>
      <v-divider />

      <v-card-actions>
        <v-spacer />
        <v-btn
          text
          @click="cancel"
        >
          Abbrechen
        </v-btn>
        <v-btn
          color="primary"
          @click="save"
        >
          Speichern
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<style scoped>

</style>

<script lang="ts">
import {Component, Emit, Prop, Vue, Watch} from "vue-property-decorator";
import {DateTime} from "luxon";

@Component
export default class TaskFollowUpDialog extends Vue {

  dateSelection = "";

  dateSelectionOpen = false;

  @Prop()
  value!: boolean;

  @Prop()
  followUpDate!: string;

  @Watch("value")
  updateDataSelection(): void {
    this.dateSelection = this.followUpDate;
  }

  @Emit("cancel")
  cancel(): void {
    //
  }

  @Emit("save")
  save(): string {
    return this.dateSelection;
  }

  updateDateSelection(value: string): void {
    this.dateSelection = value;
  }

  get computedDateSelection(): string {
    return this.dateSelection ? DateTime.fromFormat(this.dateSelection, "yy-MM-dd").toLocaleString(DateTime.DATE_SHORT) : '';
  }

}
</script>