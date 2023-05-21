import {QueryClient} from "@tanstack/vue-query";

/**
 * the query client in an extra file is only required until the complete redux store is replaced
 */
export const queryClient = new QueryClient();
