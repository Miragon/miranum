<template>
  <div class="pa-0">
    <v-file-input
      v-model="fileValue"
      :disabled="isReadonly || !canAddDocument"
      :error-messages="errorMessage"
      :label="label"
      :loading="isLoading"
      :rules="rules ? rules : true"
      multiple
      outlined
      truncate-length="50"
      type="file"
      v-bind="schema['x-props']"
      @change="changeInput"
    >
      <template #append-outer>
        <v-tooltip v-if="schema.description" :open-on-hover="false" left>
          <template v-slot:activator="{ on }">
            <v-btn icon retain-focus-on-click @blur="on.blur" @click="on.click">
              <v-icon> mdi-information</v-icon>
            </v-btn>
          </template>
          <div class="tooltip">{{ schema.description }}</div>
        </v-tooltip>
      </template>
    </v-file-input>

    <div v-if="documents && documents.length > 0" class="listWrapper">
      <template v-for="doc in documents">
        <dwf-file-preview
          :key="doc.name"
          :document="doc"
          :readonly="isReadonly"
          @remove-document="removeDocument"
        />
      </template>
    </div>
    <div style="clear: both"></div>
  </div>
</template>

<script lang="ts">

import mime from "mime";
import globalAxios from "axios";
//@ts-ignore
import {v4 as uuidv4} from 'uuid';
import {DocumentData, FormContext} from "../../types";
import {
  Configuration,
  FetchUtils,
  HumanTaskFileRestControllerApiFactory,
  ServiceInstanceFileRestControllerApiFactory,
  ServiceStartFileRestControllerApiFactory
} from "@miragon/digiwf-engine-api-internal";
import {computed, defineComponent, inject, onMounted, ref} from "vue";

export default defineComponent({
  props: [
    'valid',
    'readonly',
    'hasFocused',
    'value',
    'options',
    'schema',
    'fullKey',
    'dense',
    'label',
    'disabled',
    'rules',
    'on'
  ],
  setup(props) {
    let model = "";
    let fileValue = ref<File[] | null>(null);
    let data: any = {};
    let documents = ref<DocumentData[]>([]);
    let errorMessage = ref<string>("");
    let isLoading = ref<boolean>(false);
    let uuid = "";

    const apiEndpoint = inject<string>('apiEndpoint');
    const formContext = inject<FormContext>('formContext');

    const input = (value: any): any => {
      if (!props.on) {
        return;
      }
      //return without uuid if not enabled
      if (!props.schema.uuidEnabled) {
        return props.on.input({amount: value});
      }
      return props.on.input({
        key: uuid,
        amount: value
      });
    }

    const isReadonly = computed(() => {
      return (
        props.disabled ||
        props.readonly ||
        props.schema.readOnly ||
        isLoading.value
      );
    });

    const canAddDocument = computed(() => {
      return documents.value.length < 10;
    });

    const filePath = computed(() => {
      let path = props.schema.filePath ? props.schema.filePath : '';

      //append uuid to path if enabled
      if (props.schema.uuidEnabled) {
        path = path !== '' ? path + "/" + uuid : uuid;
      }

      return path;
    })

    const loadInitialValues = async () => {
      try {
        isLoading.value = true;

        // get filenames
        const filenames = await getFilenames();
        for (const filename of filenames) {
          await loadFile(filename);
        }
        errorMessage.value = "";
        if (documents.value.length > 0) {
          // set dummy value to satisfy "required"-rule
          fileValue.value = [];
          fileValue.value.push(new File([""], documents.value[0].name));
          input(documents.value.length);
        }
      } catch (error) {
        errorMessage.value = "Die Dateien konnten nicht geladen werden.";
      }
      isLoading.value = false;
    }

    const loadFile = async (filename: string) => {
      // get presigned url
      const presignedUrl = await getPresignedUrlForGet(filename);

      // get file content
      const res = await globalAxios.get(presignedUrl, {
        responseType: "arraybuffer",
      });
      let content = arrayBufferToString(res.data);
      let size = getEncodedContentSize(content);

      // push data
      const doc = createDocumentDataInstance(
        filename,
        getMimeType(filename),
        base64OfString(content),
        size
      );
      documents.value.push(doc);
    }

    const getEncodedContentSize = (content: string): number => {
      if (isBase64Encoded(content)) { // deprecated: Files are no longer serialized in Base64 encoding
        let decoded = window.atob(content);
        return decoded.length;
      }
      return content.length;
    }

    const getMimeType = (filename: string) => {
      const mimetype = mime.getType(filename);
      return mimetype ? mimetype : "plain/text";
    }

    const addDocument = async (mydata: any, file: File): Promise<void> => {
      const startTime = new Date().getTime();
      isLoading.value = true;
      try {
        isLoading.value = true;

        validateFileSize(mydata);
        const presignedUrl = await getPresignedUrlForPost(file);

        await globalAxios.put(presignedUrl, mydata);

        let content = arrayBufferToString(mydata);

        const doc = createDocumentDataInstance(
          file!.name,
          file!.type,
          base64OfString(content),
          mydata.byteLength
        );

        documents.value.push(doc);

        errorMessage.value = "";
        isLoading.value = false;
        input(documents.value.length);
      } catch (error: any) {
        if (
          error.response &&
          error.response.status &&
          error.response.status == 409
        ) {
          errorMessage.value = "Das Dokument existiert bereits.";
        } else if (!errorMessage.value) {
          errorMessage.value = "Das Dokument konnte nicht hochgeladen werden.";
        }
        setTimeout(() => {
          isLoading.value = false;
        }, Math.max(0, 5000 - (new Date().getTime() - startTime)));
      }
      isLoading.value = false;
    }

    const validateFileSize = (mydata: ArrayBuffer) => {
      if (mydata.byteLength > 10485760) {
        errorMessage.value = "Die Datei ist muss kleiner als 10MB sein.";
        throw new Error("File too large.");
      }
    }

    const createDocumentDataInstance = (
      name: string,
      type: string,
      data: string,
      size: number
    ) => {
      const doc: DocumentData = {
        type: type,
        name: name,
        data: toDataUrl(type, data),
        size: size!,
      };
      return doc;
    }

    const toDataUrl = (type: string, data: string): string => {
      return `data:${type};base64, ${data}`;
    }

    const axiosConfig = (): Configuration => {
      const cfg = FetchUtils.getAxiosConfig(FetchUtils.getGETConfig());
      cfg.baseOptions.headers = {"Content-Type": "application/json"};
      cfg.basePath = apiEndpoint;
      return cfg;
    }

    const getFilenames = async (): Promise<string[]> => {
      const cfg = axiosConfig();

      let res: any;
      if (formContext!.type === "start") {
        res = await ServiceStartFileRestControllerApiFactory(cfg).getFileNames1(
          formContext!.id,
          filePath.value
        );
      } else if (formContext!.type == "task") {
        res = await HumanTaskFileRestControllerApiFactory(cfg).getFileNames(
          formContext!.id,
          filePath.value
        );
      } else {
        //type "instance"
        res = await ServiceInstanceFileRestControllerApiFactory(cfg).getFileNames2(
          formContext!.id,
          filePath.value
        );
      }

      return res.data;
    }

    const getPresignedUrlForPost = async (file: File): Promise<string> => {
      const cfg = axiosConfig();

      let res: any;
      if (formContext!.type === "start") {
        res = await ServiceStartFileRestControllerApiFactory(cfg).getPresignedUrlForFileUpload1(
          formContext!.id,
          file!.name,
          filePath.value
        );
      } else if (formContext!.type == "task") {
        res = await HumanTaskFileRestControllerApiFactory(cfg).getPresignedUrlForFileUpload(
          formContext!.id,
          file!.name,
          filePath.value
        );
      } else {
        //type "instance"
        res = await ServiceInstanceFileRestControllerApiFactory(cfg).getPresignedUrlForFileUpload2(
          formContext!.id,
          file!.name,
          filePath.value
        );
      }

      return res.data;
    }

    const getPresignedUrlForGet = async (filename: string): Promise<string> => {
      const cfg = axiosConfig();

      let res: any;
      if (formContext!.type === "start") {
        res = await ServiceStartFileRestControllerApiFactory(
          cfg
        ).getPresignedUrlForFileDownload1(
          formContext!.id,
          filename,
          filePath.value
        );
      } else if (formContext!.type == "task") {
        res = await HumanTaskFileRestControllerApiFactory(
          cfg
        ).getPresignedUrlForFileDownload(
          formContext!.id,
          filename,
          filePath.value
        );
      } else {
        //type "instance"
        res = await ServiceInstanceFileRestControllerApiFactory(cfg).getPresignedUrlForFileDownload2(
          formContext!.id,
          filename,
          filePath.value
        );
      }

      return res.data;
    }

    const getPresignedUrlForDelete = async (filename: string): Promise<string> => {
      const cfg = FetchUtils.getAxiosConfig(FetchUtils.getDELETEConfig());
      cfg.basePath = apiEndpoint;

      let res: any;
      if (formContext!.type === "start") {
        res = await ServiceStartFileRestControllerApiFactory(
          cfg
        ).getPresignedUrlForFileDeletion1(
          formContext!.id,
          filename,
          filePath.value
        );
      } else if (formContext!.type == "task") {
        res = await HumanTaskFileRestControllerApiFactory(
          cfg
        ).getPresignedUrlForFileDeletion(
          formContext!.id,
          filename,
          filePath.value
        );
      } else {
        //type "instance"
        res = await ServiceInstanceFileRestControllerApiFactory(cfg).getPresignedUrlForFileDeletion2(
          formContext!.id,
          filename,
          filePath.value
        );
      }

      return res.data;
    }

    const changeInput = () => {
      if (!fileValue.value) {
        return;
      }
      errorMessage.value = "";

      fileValue.value.forEach((file) => {
        const reader = new FileReader();
        reader.onload = (event) => {
          try {
            addDocument(event.target?.result, file);
          } catch (e: any) {
            errorMessage = e.message;
          }
        };
        reader.readAsArrayBuffer(file);
      });
    }

    const removeDocument = async (document: DocumentData): Promise<void> => {
      for (let i = 0; i < documents.value.length; i++) {
        if (documents.value[i].name == document.name) {
          try {
            const presignedDeleteUrl = await getPresignedUrlForDelete(
              document.name
            );
            await globalAxios.delete(presignedDeleteUrl);
            documents.value.splice(i, 1);
            if (documents.value.length == 0) {
              // set null value to violate "required"-rule
              fileValue.value = null;
            }
            break; // only remove first item
          } catch (error) {
            errorMessage.value = "Die Datei konnte nicht gelöscht werden.";
          }
        }
      }
      input(documents.value.length);
    }

    const base64OfString = (content: string) => {
      if (isBase64Encoded(content)) { // deprecated: Files are no longer serialized in Base64 encoding
        return content;
      }
      return window.btoa(content);
    }

    const arrayBufferToString = (buffer: ArrayBuffer) => {
      let content = "";
      const bytes = new Uint8Array(buffer);
      const len = bytes.byteLength;
      for (let i = 0; i < len; i++) {
        content += String.fromCharCode(bytes[i]);
      }
      return content;
    }

    const isBase64Encoded = (content: string) => {
      var base64Regex =
        /^([0-9a-zA-Z+/]{4})*(([0-9a-zA-Z+/]{2}==)|([0-9a-zA-Z+/]{3}=))?$/;
      return base64Regex.test(content);
    }

    onMounted(() => {
      if (!formContext!.id) {
        errorMessage.value = "no contextId";
        return;
      }
      //initialize uuid if enabled
      if (props.schema.uuidEnabled) {
        if (props.value && props.value.key) {
          uuid = props.value.key;
        } else {
          uuid = uuidv4();
        }
      }
      loadInitialValues();
    })

    return {
      model,
      fileValue,
      data,
      documents,
      errorMessage,
      isLoading,
      uuid,
      changeInput,
      canAddDocument,
      isReadonly,
      removeDocument
    }

  }
});
</script>

<style>
/* hide last added filename in textfield */
.v-file-input .v-file-input__text {
  display: none;
}
</style>

<style scoped>
.listWrapper {
  margin-top: -6px;
  margin-bottom: 26px;
  float: left;
  display: flex;
  flex-wrap: wrap;
}

.tooltip {
  max-width: 200px;
}

.v-input--is-disabled:not(.v-input--is-readonly) {
  pointer-events: all;
}

</style>
