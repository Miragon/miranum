import '@mdi/font/css/materialdesignicons.css';
import Vue from 'vue';
import Vuetify from 'vuetify';

Vue.use(Vuetify);

const theme = {
  themes: {
    light: {
      primary: '#3F51B5',
      secondary: '#2196F3',
      accent: '#E8EAF6',
      success: '#69BE28',
      error: '#FF0000',
    },
  },
  options: {customProperties: true}, // enable css vars
};

export default new Vuetify({
  theme: theme
});
