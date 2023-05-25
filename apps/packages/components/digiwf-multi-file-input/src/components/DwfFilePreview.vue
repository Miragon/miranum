<template>
  <div>
    <a target="_blank" @click="openInTab()">
      <v-card class="doc-card mb-2" elevation="2" outlined max-width="350px">
        <v-card-title class="text-subtitle-1 title">
          <div class="d-flex align-start flex-row">
            <v-icon left size="30" class="mr-2">
              {{ icon }}
            </v-icon>
            {{ document.name }}
          </div>
        </v-card-title>
        <v-card-text>
          <div class="preview">
            <v-img
                v-if="isImage"
                class="preview-component"
                :src="document.data"
                max-width="200px"
                alt="Image preview..."
            >
            </v-img>

            <vue2-pdf-embed
                v-else-if="document.type === 'application/pdf'"
                :source="document.data"
                class="preview-component"
            />

            <div v-else class="preview-text">Keine Vorschau verfügbar</div>
            <div>
              <div class="footer">{{ formatBytes(0) }}</div>
              <template v-if="!readonly">
                <v-btn
                    class="remove-button ma-1"
                    elevation="1"
                    icon
                    @click.stop="removeDocument"
                >
                  <v-icon> mdi-delete</v-icon>
                </v-btn>
              </template>
            </div>
          </div>
        </v-card-text>
      </v-card>
    </a>
  </div>
</template>

<script lang="ts">

import {computed, defineComponent} from "vue";
import {fileIcons} from "../util";

export default defineComponent({
  props: ['document', 'readonly'],
  emits: ['remove-document'],
  setup(props, {emit}) {

    const calcByteCharacters = computed(() => atob(props.document.data.substr(`data:${props.document.type};base64,`.length)));

    const icon = computed(() => fileIcons[props.document.type] ?? "mdi-file");

    const isImage = computed(() => props.document.type.toLowerCase() === "image/jpeg" || props.document.type.toLowerCase() === "image/png");

    const blobUrl = (): string => {
      const byteCharacters = calcByteCharacters.value;

      const byteArrays: Uint8Array[] = [];

      for (let offset = 0; offset < byteCharacters.length; offset += 1024) {
        const slice = byteCharacters.slice(offset, offset + 1024);

        const byteNumbers = new Array(slice.length);
        for (let i = 0; i < slice.length; i++) {
          byteNumbers[i] = slice.charCodeAt(i);
        }
        const byteArray = new Uint8Array(byteNumbers);
        byteArrays.push(byteArray);
      }
      const blob = new Blob(byteArrays, {type: props.document.type});
      return URL.createObjectURL(blob);
    }

    const openInTab = () => {
      const calcVlobUrl = blobUrl();
      const link = document.createElement("a");
      link.href = calcVlobUrl;
      link.setAttribute("download", props.document.name!);
      document.body.appendChild(link);
      link.click();
    }

    const formatBytes = (decimals = 2) => {
      const k = 1024;
      const dm = decimals < 0 ? 0 : decimals;
      const sizes = ["Bytes", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB"];

      const i = Math.floor(Math.log(props.document.size) / Math.log(k));

      return (
          parseFloat((props.document.size / Math.pow(k, i)).toFixed(dm)) +
          " " +
          sizes[i]
      );
    }

    const removeDocument = () => {
      emit('remove-document', props.document)
    }

    return {
      calcByteCharacters,
      icon,
      isImage,
      openInTab,
      formatBytes,
      removeDocument
    }
  }

})

</script>

<style scoped>
.remove-button {
  margin: 0;
  background-color: #eeeeee;
  opacity: 70%;
  position: absolute;
  right: 0;
  bottom: 0;
}

.doc-card {
  height: 200px;
  overflow: hidden;
  margin: 0 4px;
}

.title {
  background: #eeeeee;
}

.preview {
  margin: 2px 2px 2px 2px;
  padding: 5px;
}

.v-card:hover {
  background-color: #fafafa;
}

.preview-component {
  margin-left: auto;
  margin-right: auto;
}

.preview-text {
  display: flex;
  justify-content: center;
  align-items: center;
  padding-top: 40px;
  color: #aaaaaa;
}

.footer {
  position: absolute;
  left: 0;
  bottom: 0;
  margin-bottom: 0;
  color: #aaaaaa;
  font-size: 13px;
  background-color: #eeeeee;
  opacity: 70%;
  border-radius: 0 4px 0 0;
}
</style>
