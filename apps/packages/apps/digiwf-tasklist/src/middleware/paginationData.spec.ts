import {useGetPaginationData} from "./paginationData";
import {PageBasedPaginationProvider} from "./PageBasedPaginationProvider";
import {useRouter} from "vue-router/composables";
import {inject} from "vue";

beforeEach(() => {
  jest.resetModules();
});
jest.mock("vue-router/composables", () => ({
  ...jest.requireActual('vue-router/composables'),
  useRoute: jest.fn(),
  useRouter: jest.fn(() => {
    console.log("useRouter wird verwendet")
    return {
      currentRoute: {
        path: "#/mytask",
        query: {
          size: "100"
        }
      },
      push: () => {
      },
      replace: () => {
      },
    }
  })
}))
jest.mock('vue', () => ({
  ...jest.requireActual('vue'),
  inject: jest.fn(() => {
    return new PageBasedPaginationProvider();
  }),
}));
const mockRouter = (path: string, page?: number, size?: number, filter?: string) => {
  (useRouter as any).mockImplementation(() => ({
    currentRoute: {
      path,
      query: {
        page: page + "",
        size: size + "",
        filter
      }
    },
    push: () => {
    },
    replace: () => {
    },
  }))
}

describe("usePaginationData", () => {
  it("should throw exception if PageBasesPaginationProvider could not be injected", () => {
    (inject as any).mockImplementation(() => {
    })
    expect(useGetPaginationData).toThrow()
  })
  it("should return values from url query if session is empty", () => {
    (inject as any).mockImplementation(() => {
      return new PageBasedPaginationProvider()
    })
    mockRouter("/#/mytasks", 99, 999, "filter")
    const {
      size,
      page,
      searchQuery
    } = useGetPaginationData()
    expect(size.value).toBe(999)
    expect(page.value).toBe(99)
    expect(searchQuery.value).toBe("filter")
  });

  it("should return values from session if session is existing", () => {
    const sessionData = new PageBasedPaginationProvider();
    sessionData.setPageOfPageId("unknown", 22);
    sessionData.setSizeOfPageId("unknown", 11);
    sessionData.setSearchQuery("unknown", "session filter");
    (inject as any).mockImplementation(() => {
      return sessionData
    })
    mockRouter("/", 99, 999, "filter")
    const {
      size,
      page,
      searchQuery
    } = useGetPaginationData()
    expect(size.value).toBe(11)
    expect(page.value).toBe(22)
    expect(searchQuery.value).toBe("session filter")
  });

  it("should set page to zero if search query is changed", () => {
    const sessionData = new PageBasedPaginationProvider();
    sessionData.setPageOfPageId("unknown", 1);
    sessionData.setSizeOfPageId("unknown", 5);
    sessionData.setSearchQuery("unknown", undefined);
    (inject as any).mockImplementation(() => {
      return sessionData
    })
    mockRouter("/", 0, 0, "filter")
    const {
      size,
      page,
      searchQuery,
      setSearchQuery
    } = useGetPaginationData()
    expect(size.value).toBe(5)
    expect(page.value).toBe(1)
    expect(searchQuery.value).toBe(undefined)
    setSearchQuery("new filter")
    expect(page.value).toBe(0)
    expect(searchQuery.value).toBe("new filter")
  });
});
