<template>
  <v-app>
    <v-app-bar
      app
      clipped-left
      color="primary"
      dark
    >
      <v-app-bar-nav-icon
        aria-hidden="false"
        aria-label="Menü öffnen/schließen"
        @click.stop="drawer = !drawer"
      />

      <router-link
        style="text-decoration: none;"
        to="/"
      >
        <v-toolbar-title class="font-weight-bold">
          <span class="white--text">Miranum</span>
        </v-toolbar-title>
      </router-link>
      <v-spacer/>
      <v-spacer/>
      {{ username }}

      <v-menu offset-y>
        <template v-slot:activator="{ on, attrs }">
          <v-btn
            fab
            text
            v-bind="attrs"
            v-on="on"
          >
            <v-icon class="white--text">
              mdi-account-circle
            </v-icon>
          </v-btn>
        </template>
      </v-menu>
    </v-app-bar>

    <v-navigation-drawer
      v-model="drawer"
      app
      clipped
      width="300"
    >
      <AppMenuList :number-of-process-instances="processInstancesCount"/>
    </v-navigation-drawer>
    <v-main class="main">
      <v-container fluid>
        <v-fade-transition mode="out-in">
          <router-view/>
        </v-fade-transition>
      </v-container>
    </v-main>
  </v-app>
</template>

<style scoped>
.maintenance >>> .v-banner__wrapper {
  padding: 0;
}

.maintenance >>> .v-avatar {
  margin: 8px;
}

</style>

<style>

.hrDividerMenu {
  border: 0;
  border-top: 1px solid #ddd;
  margin: -2px 20px 0 20px;
}


.hrDivider {
  border: 0;
  border-top: 1px solid #ddd;
  margin: 0 5px;
}

.main {
  background-color: white;
}

html {
  overflow: auto;
}

button {
  text-transform: none !important;
}

a {
  text-transform: none !important;
}

/* Set table style for markdown tables */
.vjsf-markdown-input table {
  border-collapse: collapse;
  margin: 25px 0;
  font-size: 0.9em;
  font-family: sans-serif;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.15);
}

.vjsf-markdown-input thead tr {
  background-color: var(--v-secondary-base);
  color: white;
  text-align: left;
}

.vjsf-markdown-input th,
.vjsf-markdown-input td {
  padding: 12px 15px;
}

.vjsf-markdown-input tbody tr {
  border-bottom: 1px solid white;
}

.vjsf-markdown-input tbody tr:nth-of-type(even) {
  background-color: lightgray;
}
</style>


<script lang="ts">
import Vue from "vue";
import {Component, Watch} from "vue-property-decorator";
import {ProcessInstanceDto, UserDto} from "@miragon/digiwf-engine-api-internal";
import AppMenuList from "./components/UI/appMenu/AppMenuList.vue";
import Auth from "./views/Auth.vue";
import {useServices} from "./hooks/store";

@Component({
  components: {Auth, AppMenuList}
})
export default class App extends Vue {
  drawer = true;
  processInstancesCount: number | null = null;
  username = "";
  loginLoading = false;
  loggedIn = true;

  user: any = null;

  get getCurrentUser() {
    return this.user;
  }

  created(): void {
    this.loadData();
    const service = useServices();
    setTimeout(() => {
      service.$auth.getUser().then((user) => {
        this.user = user;
        if (!user) {
          service.$auth.login();
        }
        this.loadData();
      });
    }, 1000);
  }

  loadData(refresh = false): void {
    this.$store.dispatch("user/getUserInfo", refresh);
    this.$store.dispatch("info/getInfo", refresh);
    this.drawer = this.$store.getters["menu/open"];
  }

  getUser(): void {
    this.loginLoading = true;
    this.$store.dispatch("user/getUserInfo", true);
    this.loginLoading = false;
  }

  @Watch("$store.state.menu.open")
  onMenuChanged(menuOpen: boolean): void {
    this.drawer = menuOpen;
  }

  @Watch("$store.state.user.info")
  setUserName(user: UserDto): void {
    this.username = user.forename + " " + user.surname;
    // if session is not valid, user is updated to an empty object in redux store
    this.loggedIn = !!user.username;
  }

  @Watch("$store.state.processInstances.processInstances")
  setMyProcessInstancesCount(processInstances: ProcessInstanceDto[]): void {
    this.processInstancesCount = processInstances.length;
  }
}
</script>
