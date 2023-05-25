<template>
  <div>
    <v-data-iterator
      class="dataContainer"
      :items="items"
      :items-per-page.sync="itemsPerPage"
      :page="page"
      :no-data-text="noDataText"
      hide-default-footer
    >
      <template #default="props">
        <v-list>
          <slot :items="props.items"/>
        </v-list>
      </template>
    </v-data-iterator>
    <AppPaginationFooter
      :found-data-text="foundDataText"
      :size="itemsPerPage"
      :last-page="lastPage"
      :last-page-button-disabled="lastPageButtonDisabled"
      :next-page="nextPage"
      :total-number-of-items="items.length"
      :next-page-button-disabled="nextPageButtonDisabled"
      :number-of-pages="numberOfPages"
      :page="page"
      :update-items-per-page="updateItemsPerPage"/>
  </div>
</template>
<style scoped>
.list {
  overflow: auto;
  max-height: calc(100vh - 19rem);
}
</style>

<script lang="ts">

import {Component, Prop, Vue, Watch} from "vue-property-decorator";
import AppPaginationFooter from "./AppPaginationFooter.vue";

@Component({
  components: {AppPaginationFooter}
})
export default class AppPageableList extends Vue {

  itemsPerPage = 10;
  page = 1;

  @Prop()
  items!: any[];

  @Prop()
  totalNumberOfItems!: number

  @Prop()
  noDataText!: string;

  @Prop()
  foundDataText!: string;

  get numberOfPages(): number {
    return Math.ceil(this.items.length / this.itemsPerPage);
  }

  updateItemsPerPage(number: number): void {
    this.itemsPerPage = number;
  }


  nextPage(): void {
    if (this.page + 1 <= this.numberOfPages) this.page += 1;
  }

  lastPage(): void {
    if (this.page - 1 >= 1) this.page -= 1;
  }

  get nextPageButtonDisabled(): boolean {
    return this.page >= this.numberOfPages;
  }

  get lastPageButtonDisabled(): boolean {
    return this.page <= 1;
  }

  @Watch("numberOfPages")
  updatePageNumber(numberOfPages: number) {
    if (numberOfPages < this.page) {
      this.page = numberOfPages;
    }
    if (this.page == 0 && numberOfPages > 0) {
      this.page = 1;
    }

    if (numberOfPages == 0) {
      this.page = 0;
    }
  }
}
</script>

