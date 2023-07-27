<template>
  <div class="mb-7">
    <v-text-field
      v-model.trim="documentInput"
      :readonly="readonly"
      class="documentInput"
      outlined
      :error="!valid && hasFocused"
      hide-details
      :disabled="requesting"
      :label="label"
      type="text"
    >
      <template #append>
        <div
          v-if="!readonly"
          class="appendWrapper"
        >
          <v-fade-transition leave-absolute>
            <v-progress-circular
              v-if="requesting"
              size="24"
              color="white"
              indeterminate
            />
            <v-btn
              v-else
              class="addButtonDocInput"
              text
              height="56"
              color="white"
              @click="addDocument"
            >
              <v-icon>
                mdi-file-plus-outline
              </v-icon>
            </v-btn>
          </v-fade-transition>
        </div>
      </template>
    </v-text-field>
    <div
      v-if="errorMessage"
      style="color: red"
    >
      {{ errorMessage }}
    </div>
    <div
      v-if="documents && documents.length > 0"
      class="listWrapper"
    >
      <div
        v-for="doc in documents"
        :key="doc.url"
      >
        <v-flex class="d-flex ma-2 ml-3 align-center">
          <v-icon class="mr-2">
            {{ calculateIcon(doc.type) }}
          </v-icon>
          <a
            target="_blank"
            class="documentLink"
            :href="doc.url"
          >{{ doc.name }}</a>
          <v-spacer/>
          <v-btn
            v-if="!readonly"
            class="removeButton"
            icon
            @click="removeDocument(doc.url)"
          >
            <v-icon>
              mdi-delete
            </v-icon>
          </v-btn>
        </v-flex>
      </div>
    </div>
    <VMessages
      v-if="!valid && hasFocused"
      class="mt-1"
      :value="errorBucket"
      color="error"
    />
  </div>
</template>

<style scoped>

.appendWrapper {
  background-color: #333333;
  border-top-right-radius: 4px;
  margin-right: -12px;
  height: 56px;
  width: 56px;
  margin-top: -17px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.removeButton {
  margin: 0;
}

.listWrapper {
  overflow: auto;
  z-index: 10;
  border-bottom-right-radius: 4px;
  border-bottom-left-radius: 4px;
  border: 1px solid rgba(0, 0, 0, 0.38);
  border-top: 0;
}

.documentInput {
  border-bottom-left-radius: 0;
  border-bottom-right-radius: 0;
}

.documentLink {
  white-space: nowrap;
  text-overflow: ellipsis;
  overflow: hidden;
}

</style>

<script lang="ts">
import {VInput} from "vuetify/lib";
import {Vue} from "vue-property-decorator";
import {DmsRestControllerApiFactory, FetchUtils, GetMetadataTO, MetadataTO} from '@muenchen/digiwf-engine-api-internal';
import {ApiConfig} from "../../api/ApiConfig";

export default Vue.extend({
  name: 'BaseDocumentInput',
  mixins: [VInput as any],
  props: {
    value: Array as () => Array<MetadataTO>,
    col: Number,
    dms: String,
    label: String,
    readonly: Boolean,
  },
  data() {
    return {
      documents: [] as Array<MetadataTO>,
      locked: false,
      requesting: false,
      errorMessage: "",
      documentInput: ""
    };
  },
  computed: {
    isReadonly(): boolean {
      return this.readonly || this.locked;
    }
  },
  created() {
    if (this.value) {
      this.documents = this.value;
    }
  },
  methods: {
    input(): any {
      this.$emit('input', this.documents);
    },
    async addDocument(): Promise<void> {
      if (!this.documentInput) {
        return;
      }

      const startTime = new Date().getTime();
      this.requesting = true;

      try {
        this.locked = true;
        //const document = await DmsService.getMetadata({url: this.documentInput});
        const to: GetMetadataTO = {
          url: this.documentInput
        };
        const cfg = ApiConfig.getAxiosConfig(FetchUtils.getGETConfig());
        const res = await DmsRestControllerApiFactory(cfg).getMetaData(to);

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
    },
    removeDocument(url: string): void {
      // this.documents = this.documents.filter(doc => doc.url !== url);
      for (let i = 0; i < this.documents.length; i++) {
        if (this.documents[i].url == url) {
          this.documents.splice(i, 1);
          break; // #838: only remove first item
        }
      }
      this.input();
    },
    calculateIcon(type: string): string {
      if (type === "PDF-Dokument") {
        return "mdi-file-pdf";
      }
      return "mdi-file";
    }
  },
});
</script>

