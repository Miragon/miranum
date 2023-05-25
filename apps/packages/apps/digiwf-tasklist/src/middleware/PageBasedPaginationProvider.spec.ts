import {DEFAULT_PAGE, DEFAULT_SIZE, PageBasedPaginationProvider} from "./PageBasedPaginationProvider";

describe("PageBasedPaginationProvider", () => {
  it("should return undefined when page information is not set for page", () => {
    const provider = new PageBasedPaginationProvider();
    expect(provider.getPaginationDataInSession("tasks")).toBeUndefined();
  });

  it("return add default page and size when not existing by entering search term", () => {
    const provider = new PageBasedPaginationProvider();
    provider.setSearchQuery("tasks", "search-query");
    const result = provider.getPaginationDataInSession("tasks");
    expect(result?.page).toBe(DEFAULT_PAGE);
    expect(result?.size).toBe(DEFAULT_SIZE)
    expect(result?.searchQuery).toBe("search-query");
  });

  it("should not change information from other page", () => {
    const provider = new PageBasedPaginationProvider();
    provider.setSearchQuery("tasks", "search-query");
    provider.setSearchQuery("unknown", "other-search-query");
    expect(provider.getPaginationDataInSession("tasks")?.searchQuery).toBe("search-query");
    expect(provider.getPaginationDataInSession("unknown")?.searchQuery).toBe("other-search-query");
  })
});
