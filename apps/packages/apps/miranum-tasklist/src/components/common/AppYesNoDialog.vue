<template>
  <v-dialog
    :key="value"
    :value="value"
    persistent
    width="800"
    @input="changed"
  >
    <template #activator="{on}">
      <template v-if="buttontext">
        <v-btn
          color="primary"
          v-on="on"
        >
          {{ buttontext }}
        </v-btn>
      </template>
      <template v-else-if="icontext">
        <v-btn
          text
          color="primary"
          v-on="on"
        >
          <v-icon large>
            {{ icontext }}
          </v-icon>
        </v-btn>
      </template>
    </template>
    <v-card>
      <v-card-title>
        {{ dialogtitle }}
      </v-card-title>
      <v-card-text v-if="dialogtext">
        {{ dialogtext }}
      </v-card-text>
      <v-card-text v-else>
        <slot></slot>
      </v-card-text>
      <v-card-actions>
        <v-spacer />
        <v-btn
          id="yesnodialog-btn-no"
          text
          @click="no"
        >
          Nein
        </v-btn>
        <v-btn
          id="yesnodialog-btn-yes"
          color="primary"
          @click="yes"
        >
          Ja
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script lang="ts">

import {Component, Prop, Vue} from "vue-property-decorator";

/**
 * Der YesNo-Dialog ist ein generischer Dialog zur binären Abfrage beim Nutzer.
 * So kann z.B. die Bestätigung für das Löschen einer Entität damit realisiert werden.
 *
 * Da das Bestätigen einer Aktion in der Regel mit einem Button zusammenhängt, bietet der
 * YesNoDialog diesen gleich mit an. Über `buttontext` und `icontext` kann dieser konfiguriert werden.
 *
 * Wenn sowohl kein `buttontext` als auch `icontext` nicht gesetzt sind, kann der YesNoDialog auch
 * als reiner Dialog verwendet werden. Hierzu wird das Value vom Dialog durchgereicht.
 *
 * Die Bestätigung des Dialogs wird über ein `yes` Event signalisiert. Analog erfolgt die
 * Signalisierung der Abweisung durch ein `no` Event.
 *
 * Beispiel:
 * <yes-no-dialog
 *    v-model="deleteDialog"
 *    buttontext="Löschen"
 *    dialogtitle="Löschen?"
 *    dialogtext="Wollen Sie die Entität wirklich löschen?"
 *    @no="deleteDialog = false"
 *    @yes="deleteSome"></yes-no-dialog>
 */

@Component
export default class AppYesNoDialog extends Vue {
  @Prop()
  buttontext: string | undefined;
  @Prop()
  icontext: string | undefined;
  @Prop()
  dialogtitle!: string;
  @Prop()
  dialogtext!: string;
  /**
   * Steuerflag für den Dialog
   */
  @Prop()
  value!: boolean;


  no(): void {
    this.$emit('no');
  }

  yes(): void {
    this.$emit('yes');
  }

  changed(val: boolean): void {
    this.$emit("input", val);
  }

}
</script>
