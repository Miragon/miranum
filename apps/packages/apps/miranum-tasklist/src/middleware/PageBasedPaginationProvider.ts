import {PageId} from "./pageId";


export const DEFAULT_PAGE = 0;
export const DEFAULT_SIZE = 20;

export class PageBasedPaginationProvider {
  private pageKeyToPaginationData: Map<PageId, PaginationDataInSession> = new Map();

  public getPaginationDataInSession(pageId: PageId): PaginationDataInSession | undefined {
    return this.pageKeyToPaginationData.get(pageId)
  }

  public setPageOfPageId(pageId: PageId, page: number) {
    this.pageKeyToPaginationData.set(pageId, {
      ...this.getPaginationDataInSessionOrDefault(pageId),
      page,
    });
  }

  public setSizeOfPageId(pageId: PageId, size: number) {
    this.pageKeyToPaginationData.set(pageId, {
      ...this.getPaginationDataInSessionOrDefault(pageId),
      size,
    });
  }

  public setSearchQuery(pageId: PageId, searchQuery?: string) {
    this.pageKeyToPaginationData.set(pageId, {
      ...this.getPaginationDataInSessionOrDefault(pageId),
      searchQuery
    })
  }

  private getPaginationDataInSessionOrDefault(pageId: PageId): PaginationDataInSession {
    const paginationInformationOfPage = this.pageKeyToPaginationData.get(pageId);
    return {
      size: paginationInformationOfPage?.size || DEFAULT_SIZE,
      page: paginationInformationOfPage?.page || DEFAULT_PAGE,
      searchQuery: paginationInformationOfPage?.searchQuery,
    };
  }
}

export interface PaginationDataInSession {
  readonly searchQuery?: string;
  readonly page: number;
  readonly size: number;
}
