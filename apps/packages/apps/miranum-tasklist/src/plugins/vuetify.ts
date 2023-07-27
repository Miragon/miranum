import '@mdi/font/css/materialdesignicons.css';
import Vue from 'vue';
import Vuetify from 'vuetify';

Vue.use(Vuetify);

const theme = {
  themes: {
    light: {
      primary: '#ff7c02',
      secondary: '#333333',
      accent: '#7BA4D9',
      success: '#69BE28',
      error: '#FF0000',
    },
  },
  options: {customProperties: true}, // enable css vars
};

export default new Vuetify({
  theme: theme
});
