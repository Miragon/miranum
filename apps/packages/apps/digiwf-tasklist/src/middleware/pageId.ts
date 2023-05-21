import {useRouter} from "vue-router/composables";


export type PageId = "tasks" | "assignedgrouptasks" | "opengrouptasks" | "unknown";
const pathToPageId: { [key: string]: PageId } = {
  "/mytask": "tasks",
  "/assignedgrouptask": "assignedgrouptasks",
  "/opengrouptask": "opengrouptasks"
}

interface PageData {
  readonly id: PageId;
  readonly path: string;
}

/**
 * returns the mapped page id from path.
 */
export const usePageId = (): PageData => {
  const router = useRouter();
  const path = router.currentRoute.path;
  const pageId = pathToPageId[path] as PageId;
  if(!pageId) {
    console.error(`Digiwf: no pageId set for path ${path}`)
  }
  return {
    id: pageId || "unknown",
    path
  };
}
