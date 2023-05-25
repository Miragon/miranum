<template>
  <v-app>
    <v-app-bar
      app
      clipped-left
      color="secondary"
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
          <span class="white--text">Digi</span>
          <span style="color: #FFCC00">WF</span>
        </v-toolbar-title>
      </router-link>
      <v-spacer/>
      <span v-if="appInfo !== null">{{ appInfo.environment }}</span>
      <v-spacer/>
      {{ username }}
      <v-btn
        fab
        text
      >
        <v-icon class="white--text">
          mdi-account-circle
        </v-icon>
      </v-btn>
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
      <v-banner
        v-if="appInfo && appInfo.maintenanceInfo1"
        class="maintenance"
        color="orange darken-1"
        elevation="1"
        icon="mdi-alert-circle-outline"
        icon-color="black"
        multi-line
        transition="slide-y-transition"
      >
        <p class="body-1 my-1">
          {{ appInfo.maintenanceInfo1 }}
        </p>
        <p class="body-2 my-1">
          {{ appInfo.maintenanceInfo2 }}
        </p>
      </v-banner>
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
import {InfoTO, ServiceInstanceTO, UserTO,} from "@miragon/digiwf-engine-api-internal";
import AppMenuList from "./components/UI/appMenu/AppMenuList.vue";
import {useServices} from "./hooks/store";

@Component({
  components: {AppMenuList}
})
export default class App extends Vue {
  drawer = true;
  processInstancesCount: number | null = null;
  username = "";
  appInfo: InfoTO | null = null;
  loginLoading = false;
  loggedIn = false;

  user: any = null;

  get getCurrentUser() {
    return this.user;
  }

  created(): void {
    //TODO muss an einer anderen Stelle gefixt werden
    this.loadData();
    const service = useServices();
    service.$auth.getUser().then((user) => {
      this.user = user;
      console.log("user", user);
      if (!user) {
        service.$auth.login();
      }
    });
  }

  loadData(refresh = false): void {
    this.$store.dispatch("processInstances/getProcessInstances", refresh);
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
  setUserName(user: UserTO): void {
    this.username = user.forename + " " + user.surname;
    // if session is not valid, user is updated to an empty object in redux store
    this.loggedIn = !!user.username
  }

  @Watch("$store.state.processInstances.processInstances")
  setMyProcessInstancesCount(processInstances: ServiceInstanceTO[]): void {
    this.processInstancesCount = processInstances.length;
  }

  @Watch("$store.state.info.info")
  setAppInfo(info: InfoTO): void {
    this.appInfo = info;
  }

}
</script>
