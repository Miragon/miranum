import {useMutation, useQuery, useQueryClient} from "@tanstack/vue-query";
import {
  callAssignTaskInEngine,
  callAssignTaskInTaskService,
  callCancelTaskInEngine,
  callCompleteTaskInEngine,
  callCompleteTaskInTaskService,
  callDeferTask,
  callDownloadPdfFromEngine,
  callGetAssignedGroupTasksFromEngine,
  callGetAssignedGroupTasksFromTaskService,
  callGetOpenGroupTasksFromEngine,
  callGetOpenGroupTasksFromTaskService,
  callGetTaskDetailsFromEngine,
  callGetTaskDetailsFromTaskService,
  callGetTasksFromEngine,
  callGetTasksFromTaskService,
  callPostAssignTaskInEngine,
  callPostAssignTaskInTaskService,
  callSaveTaskInEngine,
  callSaveTaskInTaskService,
  callSetFollowUpTaskInEngine
} from "../../api/tasks/tasksApiCalls";
import {computed, ref, Ref} from "vue";
import {Page} from "../commonModels";
import {HumanTask, HumanTaskDetails} from "./tasksModels";
import {isServiceTaskServiceEnabled} from "../../utils/featureToggles";
import {
  mapTaskDetailsFromEngineService,
  mapTaskDetailsFromTaskService,
  mapTaskFromTaskService,
  mapTaskPageFromEngineService
} from "./taskMapper";
import {useStore} from "../../hooks/store";
import axios, {AxiosError} from "axios";
import {HumanTaskDetailTO} from "@miragon/digiwf-engine-api-internal";
import {dateToIsoDateTime, getCurrentDate} from "../../utils/time";
import router from "../../router";
import {queryClient} from "../queryClient";
import store from "../../store";
import {getUserInfo} from "../user/userMiddleware";
import {PageOfTasks, Task} from "@miragon/digiwf-task-api-internal";

const shouldUseTaskService = isServiceTaskServiceEnabled();
if (shouldUseTaskService) {
  console.log("feature toggle enabled. New tasklist service is used for network requests.")
}

const userTasksQueryId = "user-tasks";
const assignedGroupTasksQueryId = "assigned-group-tasks";
const openGroupTasksQueryId = "open-group-tasks";
const addUserToTask = (r: Task): Promise<HumanTask> => {
  return (
    r.assignee
      ? getUserInfo(r.assignee)
      : Promise.resolve(undefined)
  )
    .then(user => Promise.resolve(mapTaskFromTaskService(r, user)))
};

const handlePageOfTaskResponse = (response: PageOfTasks) => {
  const tasksWithUserPromises = response.content?.map(addUserToTask);
  return Promise.all<HumanTask>(tasksWithUserPromises)
    .then(tasks => Promise.resolve<Page<HumanTask>>({
        content: tasks,
        totalElements: response.totalElements,
        totalPages: response.totalPages!,
      })
    )
};

const handleTaskLoadingFromTaskService = (
  page: Ref<number>,
  size: Ref<number>,
  query: Ref<string | undefined>,
  followUp: Ref<boolean | undefined>
) => {
  return callGetTasksFromTaskService(
    page.value,
    size.value,
    query.value,
    followUp.value ? getCurrentDate() : undefined
  ).then(handlePageOfTaskResponse);
}

export const useMyTasksQuery = (
  page: Ref<number>,
  size: Ref<number>,
  query: Ref<string | undefined>,
  followUp: Ref<boolean | undefined>
) => useQuery({
  queryKey: [userTasksQueryId, page.value, size.value, query.value, followUp.value],

  queryFn: (): Promise<Page<HumanTask>> => {
    return shouldUseTaskService
      ? handleTaskLoadingFromTaskService(page, size, query, followUp)
      : callGetTasksFromEngine(page.value, size.value, query.value, followUp.value)
        .then((r) => Promise.resolve(mapTaskPageFromEngineService(r)))
  },
});

export const useOpenGroupTasksQuery = (
  page: Ref<number>,
  size: Ref<number>,
  query: Ref<string | undefined>
) => useQuery({
  queryKey: [openGroupTasksQueryId, page.value, size.value, query.value],
  queryFn: (): Promise<Page<HumanTask>> => {
    return shouldUseTaskService
      ? callGetOpenGroupTasksFromTaskService(page.value, size.value, query.value)
        .then(handlePageOfTaskResponse)
      : callGetOpenGroupTasksFromEngine(page.value, size.value, query.value)
        .then((r) => Promise.resolve(mapTaskPageFromEngineService(r)))
  },
});

export const useAssignedGroupTasksQuery = (
  page: Ref<number>,
  size: Ref<number>,
  query: Ref<string | undefined>
) => useQuery({
  queryKey: [assignedGroupTasksQueryId, page.value, size.value, query.value],
  queryFn: (): Promise<Page<HumanTask>> => {
    return shouldUseTaskService
      ? callGetAssignedGroupTasksFromTaskService(page.value, size.value, query.value)
        .then(handlePageOfTaskResponse)
      : callGetAssignedGroupTasksFromEngine(page.value, size.value, query.value)
        .then((r) => Promise.resolve(mapTaskPageFromEngineService(r)))
  },
});

export interface UseNumberOfTasksReturn {
  readonly myTasks: Ref<number>;
  readonly assignedGroupTasks: Ref<number>;
  readonly openGroupTasks: Ref<number>;
}

export const useNumberOfTasks = (): UseNumberOfTasksReturn => {
  const dummyPage = ref(0);
  const dummyPageSize = ref(20);
  const dummyQuery = ref(undefined);
  const {data: myTasksData} = useMyTasksQuery(dummyPage, dummyPageSize, dummyQuery, ref(false));
  const {data: assignGroupData} = useAssignedGroupTasksQuery(dummyPage, dummyPageSize, dummyQuery);
  const {data: openGroupData} = useOpenGroupTasksQuery(dummyPage, dummyPageSize, dummyQuery);

  return {
    myTasks: computed(() => myTasksData?.value?.totalElements || 0),
    assignedGroupTasks: computed(() => assignGroupData?.value?.totalElements || 0),
    openGroupTasks: computed(() => openGroupData?.value?.totalElements || 0),
  }
}

export const useAssignTaskMutation = () => {
  const queryClient = useQueryClient();

  const lhmObjectId = (useStore().state as any).user?.info?.lhmObjectId;
  return useMutation<void, any, string>({
    mutationFn: (taskId) => {
      return shouldUseTaskService
        ? callPostAssignTaskInTaskService(taskId, lhmObjectId)
        : callPostAssignTaskInEngine(taskId)
    },
    onSuccess: () => {
      queryClient.invalidateQueries(["user-tasks"]);
      queryClient.invalidateQueries(["assigned-group-tasks"]);
      queryClient.invalidateQueries(["open-group-tasks"]);
    },
  })
}


export interface LoadTaskFromEngineResultData {
  readonly task: HumanTaskDetails;
  readonly model?: { [key: string]: object; }

  /**
   * with moment.js formatted date
   */
  readonly followUpDate: string;

  /**
   * @deprecated only used for old forms
   */
  readonly cancelText: string
  /**
   * @deprecated only used for old forms
   */
  readonly hasDownloadButton: boolean;
  /**
   * @deprecated only used for old forms
   */
  readonly downloadButtonText: string;
}

export interface LoadTaskResult {
  readonly data?: LoadTaskFromEngineResultData;
  readonly error?: string
}


export const loadTask = (taskId: string): Promise<LoadTaskResult> => {
  return shouldUseTaskService
    ? loadTaskFromTaskService(taskId)
    : loadTasksFromEngine(taskId)
}

const loadTaskFromTaskService = (taskId: string): Promise<LoadTaskResult> => {
  return callGetTaskDetailsFromTaskService(taskId)
    .then(taskResponse => {
      return (taskResponse.assignee
          ? getUserInfo(taskResponse.assignee)
          : Promise.resolve()
      ).then(user => {
        const taskDetails = mapTaskDetailsFromTaskService(taskResponse);
        return Promise.resolve<LoadTaskResult>({
          data: {
            task: taskDetails,
            hasDownloadButton: false,
            model: taskDetails.variables, // FIXME: I guess that is wrong
            followUpDate: taskDetails.followUpDate!,
            cancelText: taskDetails.form?.buttons?.cancel!.buttonText || "Task abbrechen",
            downloadButtonText: taskDetails.form?.buttons?.statusPdf!.buttonText || ""
          }
        })
      })

    }).catch((error: Error | AxiosError) => {
      if (axios.isAxiosError(error) && (error as AxiosError).status === 404) {
        return Promise.resolve({
          error: "Die Aufgabe oder der zugehörige Vorgang wurden bereits abgeschlossen. Die Aufgabe kann daher nicht mehr angezeigt oder bearbeitet werden."
        })
      } else {
        return Promise.resolve({
          error: "Die Aufgabe konnte nicht geladen werden."
        })
      }
    });
}

/**
 * requests for TaskDetailsView
 */

/**
 * @deprecated
 * @param id
 */
const loadTasksFromEngine = (id: string): Promise<LoadTaskResult> => {
  const hasDownloadButton = (task: HumanTaskDetailTO): boolean => {
    if (task.form && task.form.buttons) {
      return task.form.buttons.statusPdf!.showButton || false
    } else if (task.statusDocument) {
      return true;
    }
    return false; // I guess that is the default value, before it could be nullable
  }

  return callGetTaskDetailsFromEngine(id).then(taskDetails => {
    return Promise.resolve<LoadTaskResult>({
      data: {
        task: mapTaskDetailsFromEngineService(taskDetails),
        model: taskDetails.variables,
        followUpDate: taskDetails.followUpDate!,
        cancelText: taskDetails.form?.buttons?.cancel!.buttonText || "Task abbrechen",
        hasDownloadButton: hasDownloadButton(taskDetails),
        downloadButtonText: taskDetails.form?.buttons?.statusPdf!.buttonText || ""
      },
      error: undefined,
    })
  })
    .catch((error: Error | AxiosError) => {
      if (axios.isAxiosError(error) && (error as AxiosError).status === 404) {
        return Promise.resolve({
          error: "Die Aufgabe oder der zugehörige Vorgang wurden bereits abgeschlossen. Die Aufgabe kann daher nicht mehr angezeigt oder bearbeitet werden."
        })
      } else {
        return Promise.resolve({
          error: "Die Aufgabe konnte nicht geladen werden."
        })
      }
    })
}

export interface CancelTaskResult {
  readonly isError: boolean;
  readonly errorMessage?: string;

}

/**
 * @deprecated
 * @param taskId
 */
export const cancelTaskInEngine = (taskId: string): Promise<CancelTaskResult> => {
  return callCancelTaskInEngine(taskId).then(() => {
    queryClient.invalidateQueries([userTasksQueryId])
    router.push({path: "/task"}); // FIXME: copied from old source code. Question is why /task is called (path does not exist)

    return Promise.resolve<CancelTaskResult>({
      isError: false
    });

  }).catch(_ => {
    return Promise.resolve<CancelTaskResult>({
      isError: true,
      errorMessage: "Die Aufgabe konnte nicht abgebrochen werden."
    });
  });
}

interface CompleteTaskResult {
  readonly errorMessage?: string;
  readonly isError: boolean;
}

export const completeTask = (taskId: string, variables: any): Promise<CompleteTaskResult> => {
  return (
    shouldUseTaskService
      ? callCompleteTaskInTaskService(taskId, variables)
      : callCompleteTaskInEngine(taskId, variables)
  )
    .then(() => {
      queryClient.invalidateQueries([userTasksQueryId]);
      router.push({path: "/mytask"}); // FIXME: copied from old source code. Question is why /task is called (path does not exist)
      // normally it is not needed anymore because it will redirect to the task list path before
      return Promise.resolve<CompleteTaskResult>({
        isError: false,
        errorMessage: undefined,
      });
    }).catch(_ => {
      return Promise.resolve<CompleteTaskResult>({
        isError: true,
        errorMessage: "Die Aufgabe konnte nicht abgeschlossen werden."
      })
    });
}

interface SetFollowUpResult {
  readonly errorMessage?: string;
  readonly isError: boolean;
}

export const deferTask = (taskId: string, followUp: string): Promise<SetFollowUpResult> => {
  return (
    shouldUseTaskService
      ? callDeferTask(taskId, dateToIsoDateTime(followUp))
      : callSetFollowUpTaskInEngine(taskId, followUp)
  )
    .then(() => {
      queryClient.invalidateQueries([userTasksQueryId])
      router.push({path: "/task"});

      return Promise.resolve<SetFollowUpResult>({
        errorMessage: undefined,
        isError: false,
      })
    })
    .catch(_ => Promise.resolve<SetFollowUpResult>({
      errorMessage: "Die Aufgabe konnte nicht gespeichert werden.",
      isError: true,
    }))
}

interface SaveTaskResult {
  readonly errorMessage?: string;
  readonly isError: boolean;
}

export const saveTask = (taskId: string, variables: any): Promise<SaveTaskResult> => {
  return (
    shouldUseTaskService
      ? callSaveTaskInTaskService(taskId, variables)
      : callSaveTaskInEngine(taskId, variables)
  ).then(() => Promise.resolve({ // FIXME: invalide task list?
    isError: false,
    errorMessage: undefined
  }))
    .catch(_ => Promise.resolve({
      isError: true,
      errorMessage: "Die Aufgabe konnte nicht gespeichert werden.",
    }))
}

interface AssignTaskResult {
  readonly isError: boolean;
}

export const assignTask = (taskId: string,): Promise<AssignTaskResult> => {
  const userId = store.getters["user/info"].lhmObjectId;
  return (
    shouldUseTaskService
      ? callAssignTaskInEngine(taskId)
      : callAssignTaskInTaskService(taskId, userId)
  ).then(() => {
    router.push({path: "/task/" + taskId});
    queryClient.invalidateQueries([userTasksQueryId])
    queryClient.invalidateQueries([openGroupTasksQueryId])
    queryClient.invalidateQueries([assignedGroupTasksQueryId])
    return Promise.resolve({isError: false})
  }).catch(() => Promise.resolve({isError: true}))
}

interface DownloadPdfResult {
  readonly errorMessage?: string;
  readonly isError: boolean;
}

export const downloadPDFFromEngine = (taskId: string): Promise<DownloadPdfResult> => {

  return callDownloadPdfFromEngine(taskId)
    .then(result => {
      const fileURL = window.URL.createObjectURL(new Blob([base64ToArrayBuffer(result.data as any)], {type: "application/pdf"}));
      const fileLink = document.createElement("a");
      fileLink.href = fileURL;
      fileLink.setAttribute("download", "statusdokument.pdf");
      document.body.appendChild(fileLink);
      fileLink.click();
      return Promise.resolve({
        isError: false,
        errorMessage: undefined,
      })
    })
    .catch(_ => {
      return Promise.resolve({
        isError: true,
        errorMessage: "Das Statusdokument konnte nicht erstellt werden."
      })
    });
}

const base64ToArrayBuffer = (base64: string): Uint8Array => {
  const binaryString = window.atob(base64);
  const binaryLen = binaryString.length;
  const bytes = new Uint8Array(binaryLen);
  for (let i = 0; i < binaryLen; i++) {
    bytes[i] = binaryString.charCodeAt(i);
  }
  return bytes;
}
