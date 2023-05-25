import { defineConfig } from 'vite';
//@ts-ignore
import { fileURLToPath, URL } from "url";
export default defineConfig({
    plugins: [],
    build: {
        target: 'esnext',
        lib: {
            entry: 'src/index.ts',
            name: 'digiwf-engine-api-internal',
        },
        rollupOptions: {
            plugins: [
            //  typescript(/*{ plugin options }*/)
            ]
        },
        minify: 'esbuild'
    },
    resolve: {
        alias: {
            //@ts-ignore
            "@": fileURLToPath(new URL("./src", import.meta.url)),
        },
    },
});
//# sourceMappingURL=vite.config.js.map