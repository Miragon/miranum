<template>
  <div id="top">
    <v-autocomplete
      v-model="selectedUsers"
      :class="[isReadonly ? 'userInputReadonly' : 'userInput']"
      :search-input.sync="search"
      hide-no-data
      chips
      small-chips
      auto-select-first
      :readonly="isReadonly"
      :disabled="disabled"
      :rules="rules ? rules : true"
      :items="entries"
      :loading="isLoading"
      :label="label"
      v-bind="schema['x-props']"
      :filter="filterUsers"
      item-value="lhmObjectId"
      item-text="username"
      placeholder="Benutzer suchen..."
      return-object
      multiple
      @input="input"
      @change="change"
    >
      <template
        #selection="data"
      >
        <v-chip
          class="ma-1 pa-4"
          v-bind="data.attrs"
          :input-value="data.selected"
          small
          :close="!readonly"
          @click:close="removeUser(data.item)"
        >
          <v-avatar left>
            <v-img :src="mucatarUrl(data.item.username)">
              <template #placeholder>
                <v-icon>mdi-account</v-icon>
              </template>
            </v-img>
          </v-avatar>
          {{ getFullName(data.item) }} ({{ data.item.ou }})
        </v-chip>
      </template>
      <template #item="data">
        <template>
          <v-list-item-avatar>
            <v-img :src="mucatarUrl(data.item.username)">
              <template #placeholder>
                <v-icon>mdi-account</v-icon>
              </template>
            </v-img>
          </v-list-item-avatar>
          <v-list-item-content>
            <v-list-item-title>{{ getFullName(data.item) }}</v-list-item-title>
            <v-list-item-subtitle v-html="data.item.ou"/>
          </v-list-item-content>
        </template>
      </template>
    </v-autocomplete>
  </div>
</template>

<style>

/* Hide Expand/Collapse-Icon */
#top .v-autocomplete .v-input__append-inner > div {
  display: none;
}

/* Hide items already selected in input field */
/*#top .v-autocomplete.primary--text*/
/*> div*/
/*> div.v-input__slot*/
/*> div.v-select__slot*/
/*> div.v-select__selections*/
/*> div {*/
/*  display: none;*/
/*}*/

#top .v-chip__content {
  font-size: 14px;
}
</style>

<script lang="ts">
import {Component, Prop, Vue, Watch} from "vue-property-decorator";
import {VAutocomplete} from "vuetify/lib";
import {FetchUtils, SearchUserTO, UserRestControllerApiFactory, UserTO} from "@muenchen/digiwf-engine-api-internal";
import {AxiosResponse} from "axios";
import {mucatarURL} from "../../constants";
import {ApiConfig} from "../../api/ApiConfig";

@Component({
  components: {
    VAutocomplete
  }
})
export default class VMultiUserInput extends Vue {
  search = "";
  items: UserTO[] = []; // search result
  isLoading = false;
  errorMessage = "";
  lastSearch = "";
  locked = false;
  selectedUsers: UserTO[] = [];
  readonly = false;
  ldapGroups = "";

  @Prop()
  valid: boolean | undefined;

  @Prop()
  hasFocused: boolean | undefined;

  @Prop()
  value: string[] | undefined; // lhmObjectIds of all selected users

  @Prop()
  options: any;

  @Prop()
  label: string | undefined;

  @Prop()
  rules: any | undefined;

  @Prop()
  htmlDescription: string | undefined;

  @Prop()
  disabled: boolean | undefined;

  @Prop()
  on: any;

  @Prop()
  schema: string | undefined;

  input(value: any): any {
    return this.on.input(value);
  }

  created(): void {
    const schemaobj = JSON.parse(JSON.stringify(this.schema));
    this.readonly = schemaobj.readOnly;
    this.ldapGroups = schemaobj['ldap-groups'];

    if (this.readonly) {
      this.items = this.selectedUsers; // in readonly: show initial values in autocomplete
    }
    if (this.value) {
      for (const elem of this.value) {
        this.loadInitialValue(elem);
      }
    }
  }

  get isReadonly(): boolean {
    return this.readonly || this.locked;
  }

  async loadInitialValue(id: string): Promise<void> {
    try {
      this.locked = true;
      const cfg = ApiConfig.getAxiosConfig(FetchUtils.getGETConfig());
      let res: AxiosResponse;
      //if number: search by objectId; if string: search by username
      if (id.match(/^-?\d+$/)) {
        res = await UserRestControllerApiFactory(cfg).getUser(id);
      } else {
        res = await UserRestControllerApiFactory(cfg).getUserByUsername(id);
      }
      const user = res.data;

      this.selectedUsers.push(user);
      this.errorMessage = "";
    } catch (error) {
      this.errorMessage = "Ein Benutzer konnte nicht geladen werden.";
    }
    this.locked = false;
  }

  getFullName(user: UserTO): string {
    return user.forename + " " + user.surname;
  }

  filterUsers(item: UserTO, queryText: string): boolean {
    const fullName = this.getFullName(item);
    if (fullName.toLowerCase().includes(queryText.toLowerCase())) {
      return true;
    }
    return false;
  }

  getNamePrefix(user: UserTO): string {
    return user.forename!.slice(0, 1) + user.surname!.slice(0, 1);
  }

  castNoAttrAvailable(val: string): string {
    if (val === "No_Attribute_Available") {
      return "-";
    }
    return val;
  }

  get entries(): UserTO[] {
    return this.items.concat(this.selectedUsers);
  }

  @Watch("search")
  async queryResults(): Promise<void> {
    if (!this.search || this.search.length < 3) return;

    if (this.lastSearch === this.search.slice(0, 3)) return;

    this.lastSearch = this.search.slice(0, 3);

    try {
      this.isLoading = true;
      const to: SearchUserTO = {
        searchString: this.lastSearch,
        ous: this.ldapGroups ? this.ldapGroups : undefined
      };

      const cfg = ApiConfig.getAxiosConfig(FetchUtils.getGETConfig());
      const res = await UserRestControllerApiFactory(cfg).getUsers(to);

      if (this.lastSearch === this.search.slice(0, 3)) {
        this.items = res.data;
      }
      this.errorMessage = "";
    } catch (error) {
      this.errorMessage = "Der Benutzer konnte nicht geladen werden.";
    }
    this.isLoading = false;
  }

  change(): void {
    let selectedLhmObjectIds = this.selectedUsers.map(a => a.lhmObjectId);
    this.input(selectedLhmObjectIds);
  }

  resetInput(): void {
    this.lastSearch = "";
    this.search = "";
    this.items = [];
  }

  removeUser(user: UserTO): void {
    this.resetInput();
    this.selectedUsers = this.selectedUsers.filter(function (item) {
      return item.lhmObjectId !== user.lhmObjectId;
    });
  }

  mucatarUrl(uid: string) {
    return mucatarURL(uid);
  }
}
</script>
