<template>
  <app-view-layout>
    <v-flex v-if="errorMessage">
      <AppToast
        :message="errorMessage"
        type="error"
      />
    </v-flex>
    <v-flex
      v-if="process !== null"
    >
      <h1>{{ process.name }}</h1>
      <p>{{ process.description }}</p>
      <base-form
        v-if="process.startForm"
        :has-complete-error="hasCompleteError"
        :is-completing="isCompleting"
        class="startForm"
        :form="process.startForm"
        :init-model="$route.query"
        :show-save-button="false"
        @model-changed="setDirty"
        @complete-form="startProcess"
      />
      <app-json-form
        v-else
        :value="{}"
        :schema="process.jsonSchema"
        :is-completing="isCompleting"
        @complete-form="startProcess"
      />
    </v-flex>
    <app-yes-no-dialog
      :dialogtext="saveLeaveDialogText"
      :dialogtitle="saveLeaveDialogTitle"
      :value="saveLeaveDialog"
      @yes="leave"
      @no="cancel"
    />
  </app-view-layout>
</template>

<style scoped>
.startForm {
  margin-top: 1rem;
}
</style>

<script lang="ts">

import {Component, Prop, Provide} from "vue-property-decorator";
import AppViewLayout from "@/components/UI/AppViewLayout.vue";
import BaseForm from "@/components/form/BaseForm.vue";
import AppToast from "@/components/UI/AppToast.vue";
import router from "../router";
import SaveLeaveMixin from "../mixins/saveLeaveMixin";
import AppYesNoDialog from "@/components/common/AppYesNoDialog.vue";

import {
  FetchUtils,
  ServiceDefinitionControllerApiFactory,
  ServiceDefinitionDetailTO,
  StartInstanceTO
} from '@muenchen/digiwf-engine-api-internal';

import {FormContext} from "@muenchen/digiwf-multi-file-input";
import {ApiConfig} from "../api/ApiConfig";
import {invalidUserTasks} from "../middleware/tasks/taskMiddleware";
import {invalidProcessInstances} from "../middleware/processInstances/processInstancesMiddleware";

@Component({
  components: {BaseForm, AppToast, AppViewLayout, AppYesNoDialog}
})
export default class StartProcess extends SaveLeaveMixin {

  process: ServiceDefinitionDetailTO | null = null;
  errorMessage = "";
  hasChanges = false;
  isCompleting = false;
  hasCompleteError = false;

  @Prop()
  processKey!: string;

  @Provide('formContext')
  get formContext(): FormContext { return {id: this.processKey, type: "start"};}

  @Provide('apiEndpoint')
  apiEndpoint = ApiConfig.base;


  created() {
    this.loadProcess();
  }

  async startProcess(model: any): Promise<void> {
    this.isCompleting = true;
    this.hasCompleteError = false;
    let hasError = false;
    const startTime = new Date().getTime();

    const request: StartInstanceTO = {
      key: this.processKey,
      variables: model
    };
    try {
      const cfg = ApiConfig.getAxiosConfig(FetchUtils.getPOSTConfig({}));
      await ServiceDefinitionControllerApiFactory(cfg).startInstance(request);

      this.errorMessage = "";
      invalidUserTasks();
      invalidProcessInstances();

      // hier eventuell zum userTask routen
      this.hasChanges = false;
      router.push({path: '/process'});
    } catch (error) {
      this.errorMessage = 'Der Vorgang konnte nicht gestartet werden.';
      hasError = true;
    }

    setTimeout(() => {
      this.isCompleting = false;
      this.hasCompleteError = hasError;
    }, Math.max(0, 500 - (new Date().getTime() - startTime)));
  }

  async loadProcess(): Promise<void> {
    const cfg = ApiConfig.getAxiosConfig(FetchUtils.getGETConfig());
    cfg.baseOptions.validateStatus = function (status: number) {
      return status >= 200 && status < 500;
    }; // override axios default impl. (holding back http statuses >= 300)
    ServiceDefinitionControllerApiFactory(cfg)
      .getServiceDefinition(this.processKey)
      .then((res) => {
        if (res.status >= 200 && res.status < 300) { // as in axios default impl.
          this.process = res.data;
          this.errorMessage = "";
          return res;
        } else {
          if (res.status === 403) {
            this.errorMessage = "Es liegt keine Berechtigung zum Starten dieses Vorgangs vor. " +
              "Weitere Infos finden Sie in unseren FAQs in Wilma.";
          } else {
            this.errorMessage = "Der Vorgang konnte nicht geladen werden: " + res.status;
          }
        }
      })
      .catch(() => {
        this.errorMessage = "Der Vorgang konnte nicht geladen werden.";
      });
  }

  setDirty(): void {
    this.hasChanges = true;
  }

  isDirty(): boolean {
    return this.hasChanges;
  }

}
</script>
