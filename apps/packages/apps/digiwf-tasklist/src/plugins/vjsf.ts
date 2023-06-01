import Vue from 'vue';
// eslint-disable-next-line
//@ts-ignore
import VJsf from '@muenchen/vjsf/lib/VJsf.js';
import '@muenchen/vjsf/lib/VJsf.css';
import '@muenchen/vjsf/lib/deps/third-party.js';
import 'easymde/dist/easymde.min.css';
import EasyMDE from 'easymde/dist/easymde.min.js';
// eslint-disable-next-line
// @ts-ignore
window.EasyMDE = EasyMDE;
Vue.component('Jsf', VJsf);
