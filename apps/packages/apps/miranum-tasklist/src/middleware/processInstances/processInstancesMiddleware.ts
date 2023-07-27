import {Ref} from "vue";
import {useQuery} from "@tanstack/vue-query";
import {callGetProcessDefinitionsFromEngine} from "../../api/processDefinitions/processDefinitionApiCalls";
import {Page} from "../commonModels";
import {callGetProcessInstances} from "../../api/processInstances/processInstancesApiCalls";
import {queryClient} from "../queryClient";
import {DateTime} from "luxon";

export interface ProcessInstance { // FIXME: check nullable properties
  readonly id: string;
  readonly definitionName: string;
  readonly description?: string;
  readonly startTime?: string; // FIXME check date formation
  readonly endTime?: string;
  readonly status?: string;
}

const processInstancesQueryKey = "process-definitions";
export const invalidProcessInstances = () =>
  queryClient.invalidateQueries([processInstancesQueryKey]);

export const useGetProcessInstances = (page: Ref<number>, size: Ref<number>, query: Ref<string | undefined>) =>
  useQuery({
    queryKey: [processInstancesQueryKey, page.value, size.value, query.value], //.filter(it => !!it), // remove query key if not set
    queryFn: () => {
      return callGetProcessInstances(page.value, size.value, query.value)
        .then(data => {

          const content = data.content?.map<ProcessInstance>(it => ({
            id: it.id || "", // FIXME: look if key could be really undefined
            definitionName: it.definitionName || "Unbekannt",
            startTime: it.startTime ? DateTime.fromISO(it.startTime).toLocaleString(DateTime.DATETIME_SHORT) : "-",
            endTime: it.endTime,
          })) ?? [];

          return Promise.resolve<Page<ProcessInstance>>({
            content,
            page: data.number || 0,
            size: data.size || 0,
            totalPages: data.totalPages || 0,
            totalElements: data.totalElements
          });
        });
    },
  });
