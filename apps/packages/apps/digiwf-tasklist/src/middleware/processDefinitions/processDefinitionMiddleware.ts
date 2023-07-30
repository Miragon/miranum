import {Ref} from "vue";
import {useQuery} from "@tanstack/vue-query";
import {callGetProcessDefinitionsFromEngine} from "../../api/processDefinitions/processDefinitionApiCalls";
import {Page} from "../commonModels";

export interface ProcessDefinition {
  readonly key: string;
  readonly name: string;
  readonly description?: string;
  readonly versionTag?: string;
}

export const useGetProcessDefinitions = (page: Ref<number>, size: Ref<number>, query: Ref<string | undefined>) =>
  useQuery({
    queryKey: ["process-definitions", page.value, size.value, query.value], //.filter(it => !!it), // remove query key if not set
    queryFn: () => {
      return callGetProcessDefinitionsFromEngine(page.value, size.value, query.value)
        .then(data => {

          const content = data.content?.map<ProcessDefinition>(it => ({
            key: it.key || "", // FIXME: look if key could be really undefined
            name: it.name || "Unbekannt",
            description: it.description,
            versionTag: it.versionTag,
          })) ?? [];

          return Promise.resolve<Page<ProcessDefinition>>({
            content,
            page: data.number || 0,
            size: data.size || 0,
            totalPages: data.totalPages || 0,
            totalElements: data.totalElements
          });
        });
    },
  });
