import vue from "@vitejs/plugin-vue2";
import {defineConfig} from 'vite'
//@ts-ignore
import {fileURLToPath, URL} from "url";
import Components from 'unplugin-vue-components/vite'
import {VuetifyResolver} from "unplugin-vue-components/resolvers";

export default defineConfig({
  plugins: [
    vue(),
    Components({
      transformer: 'vue2',
      dts: true,
      resolvers: [
        VuetifyResolver()
      ]
    })
  ],
  build: {
    lib: {
      entry: 'src/index.ts',
      name: 'digiwf-date-input',
    },
    rollupOptions: {
      external: [
        'vue',
        'vuex',
        /vuetify\/.*/
      ],
      output: {
        globals: {
          vue: 'Vue'
        }
      }
    },
    minify: 'esbuild'
  },
  resolve: {
    alias: {
      //@ts-ignore
      "@": fileURLToPath(new URL("./src", import.meta.url)),
    },
  },
})
