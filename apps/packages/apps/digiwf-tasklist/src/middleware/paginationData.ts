import {useRouter} from "vue-router/composables";
import {inject, ref, Ref} from "vue";
import {usePageId} from "./pageId";
import {DEFAULT_PAGE, DEFAULT_SIZE, PageBasedPaginationProvider} from "./PageBasedPaginationProvider";

interface PaginationData {
  readonly searchQuery: Ref<string | undefined>;
  readonly page: Ref<number>;
  readonly size: Ref<number>;
  readonly setSearchQuery: (searchFilter?: string) => void;
  readonly setPage: (page: number) => void;
  readonly setSize: (size: number) => void;
  readonly getSearchQueryOfUrl: () => string | undefined;
}

export const useGetPaginationData = (): PaginationData => {
  const router = useRouter();
  const pageId = usePageId();
  const pageKeyToPaginationData = inject<PageBasedPaginationProvider>("paginationData");
  if (!pageKeyToPaginationData) {
    throw Error("could not inject PageBasedPaginationProvider")
  }

  const paginationInformationOfPage = pageKeyToPaginationData.getPaginationDataInSession(pageId.id || "unknown");
  const getDefaultPage = (): number => {
    const pageString = router.currentRoute.query?.page as string | null;
    if (!!pageString && !isNaN(parseInt(pageString))) {
      return parseInt(pageString);
    }
    return paginationInformationOfPage?.page || DEFAULT_PAGE;
  }
  const getDefaultSize = (): number => {
    const sizeString = router.currentRoute.query?.size as string | null;
    if (!!sizeString && !isNaN(parseInt(sizeString))) {
      return parseInt(sizeString);
    }
    return paginationInformationOfPage?.size || DEFAULT_SIZE;
  }
  const getSearchQueryOfUrl = (): string | undefined => {
    const queryFilterValue = router.currentRoute.query?.filter as string | null
    if (!!queryFilterValue) {
      return queryFilterValue;
    }
    return !!paginationInformationOfPage?.searchQuery ? paginationInformationOfPage?.searchQuery : undefined;
  }

  const searchQuery = ref<string | undefined>(getSearchQueryOfUrl());
  const page = ref<number>(getDefaultPage());
  const size = ref<number>(getDefaultSize());

  const setPage = (newPage: number) => {
    page.value = newPage;
    router.replace({
      query: {
        ...router.currentRoute.query,
        page: page.value?.toString(),
      }
    });
    pageKeyToPaginationData.setPageOfPageId(pageId.id, newPage)
  }
  const setSize = (newSize: number) => {
    size.value = newSize;
    router.replace({
      query: {
        ...router.currentRoute.query,
        size: size.value?.toString(),
      }
    });
    pageKeyToPaginationData.setSizeOfPageId(pageId.id, newSize)
  }
  const setSearchQuery = (newSearchQuery?: string) => {
    searchQuery.value = newSearchQuery
    router.replace({
      query: {
        ...router.currentRoute.query,
        filter: newSearchQuery
      }
    });
    // jump back to first page, so that user can see the first results again
    setPage(0);
    pageKeyToPaginationData.setSearchQuery(pageId.id, searchQuery.value)
  }

  // load pagination from session after page switch
  if (paginationInformationOfPage) {
    setSearchQuery(paginationInformationOfPage.searchQuery)
    setSize(paginationInformationOfPage.size)
    setPage(paginationInformationOfPage.page)
  }

  return {
    searchQuery,
    page,
    size,
    setPage,
    setSize,
    setSearchQuery,
    getSearchQueryOfUrl,
  }
}

