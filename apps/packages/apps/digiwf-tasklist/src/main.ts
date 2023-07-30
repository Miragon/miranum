import Vue, {provide, VNode} from 'vue';
import vuetify from "./plugins/vuetify";
import store from './store';
import App from './App.vue';
import router from "./router";
import TextHighlight from 'vue-text-highlight';
import {VDivider} from "vuetify/lib/components";
import AppJsonForm from "@/components/schema/AppJsonForm.vue";
import VUserInput from "@/components/schema/VUserInput.vue";
import VMultiUserInput from "@/components/schema/VMultiUserInput.vue";
import {DwfFormRenderer} from "@miragon/digiwf-form-renderer";
import {DwfMultiFileInput} from "@miragon/digiwf-multi-file-input";
import {DwfDateInput} from "@miragon/digiwf-date-input";
import Vue2PdfEmbed from 'vue-pdf-embed/dist/vue2-pdf-embed.js';
import './plugins/vjsf';
import './plugins/digiwf-forms';
import {VueQueryPlugin} from "@tanstack/vue-query";
import {queryClient} from "./middleware/queryClient";
import {initStatusCodeHandling} from "./api/statusCodeHandling";
import {PageBasedPaginationProvider} from "./middleware/PageBasedPaginationProvider";
import {initShouldUseTaskServiceFeatureToggle} from "./utils/featureToggles";


initStatusCodeHandling();
initShouldUseTaskServiceFeatureToggle();
Vue.config.productionTip = false;

Vue.use(VueQueryPlugin, {
  queryClient
});
Vue.component('TextHighlight', TextHighlight);
Vue.component('VDivider', VDivider);
Vue.component('AppJsonForm', AppJsonForm);
Vue.component('VUserInput', VUserInput);
Vue.component('VMultiUserInput', VMultiUserInput);
Vue.component('DwfFormRenderer', DwfFormRenderer);
Vue.component('DwfMultiFileInput', DwfMultiFileInput);
Vue.component('Vue2PdfEmbed', Vue2PdfEmbed);
Vue.component('DwfDateInput', DwfDateInput);

// highlight filter
Vue.filter('highlight', function (words: any, query: any) {
  const iQuery = new RegExp(query, "ig");
  return words.toString().replace(iQuery, function (matchedTxt: any, a: any, b: any) {
    return ('<span class=\'highlight\'>' + matchedTxt + '</span>');
  });
});

new Vue({
  router,
  setup() {
    provide("store", store);
    provide("paginationData", new PageBasedPaginationProvider())
  },
  store: store,
  vuetify,
  render: (h): VNode => h(App),
}).$mount('#app');
