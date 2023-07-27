import vue from "@vitejs/plugin-vue2";
import {defineConfig, loadEnv} from 'vite'
import Components from 'unplugin-vue-components/vite'
import {VuetifyResolver} from "unplugin-vue-components/resolvers";
//@ts-ignore
import {fileURLToPath, URL} from "url";

const portFromDevelopmentEnv = loadEnv("development", "./")?.VITE_PORT;
const port = portFromDevelopmentEnv
  ? Number.parseInt(portFromDevelopmentEnv)
  : 8081;

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
  server: {
    port,
    proxy: {
      "/api": "http://localhost:8082/"
    }
  },
  build: {
    commonjsOptions: {
      transformMixedEsModules: true,
    },
    minify: 'esbuild'
  },
  resolve: {
    alias: {
      //@ts-ignore
      "@": fileURLToPath(new URL("./src", import.meta.url))
    },
    dedupe: ['vue']
  }
})
