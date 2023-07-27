<script lang="ts">
import {Component, Mixins} from "vue-property-decorator";
import BaseDocumentInput from "@/components/form/BaseDocumentInput.vue";
import {AlwDmsRestControllerApiFactory, FetchUtils, GetMetadataTO} from '@muenchen/digiwf-engine-api-internal';
import {ApiConfig} from "../../api/ApiConfig";

@Component
export default class BaseAlwDocumentInput extends Mixins(BaseDocumentInput) {

  async addDocument(): Promise<void> {
    if (!this.documentInput) {
      return;
    }

    const startTime = new Date().getTime();
    this.requesting = true;

    try {
      this.locked = true;
      //const document = await AlwDmsService.getMetadata({url: this.documentInput});
      const to: GetMetadataTO = {
        url: this.documentInput
      };
      const cfg = ApiConfig.getAxiosConfig(FetchUtils.getGETConfig());
      const res = await AlwDmsRestControllerApiFactory(cfg).getMetadata(to);


      this.errorMessage = "";
      setTimeout(() => {
        this.documentInput = "";
        this.documents.push(res.data);
        this.requesting = false;
        this.input();
      }, Math.max(0, 1000 - (new Date().getTime() - startTime)));

    } catch (error) {
      setTimeout(() => {
        this.errorMessage = 'Das Dokument konnte nicht geladen werden.';
        this.requesting = false;
      }, Math.max(0, 1000 - (new Date().getTime() - startTime)));
    }
  }

}
</script>

