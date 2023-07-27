import {useMutation, useQuery, useQueryClient} from "@tanstack/vue-query";
import {
  callCancelTaskInEngine,
  callCancelTaskInTaskService,
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
import {HumanTask, HumanTaskDetails, TaskVariables} from "./tasksModels";
import {shouldUseTaskService} from "../../utils/featureToggles";
import {
  mapTaskDetailsFromEngineService,
  mapTaskDetailsFromTaskService,
  mapTaskFromTaskService,
  mapTaskPageFromEngineService
} from "./taskMapper";
import {useStore} from "../../hooks/store";
import axios, {AxiosError} from "axios";
import {HumanTaskDetailTO} from "@muenchen/digiwf-engine-api-internal";
import {dateToIsoDateTime, getCurrentDate} from "../../utils/time";
import router from "../../router";
import {queryClient} from "../queryClient";
import store from "../../store";
import {getUserInfo} from "../user/userMiddleware";
import {PageOfTasks, Task} from "@muenchen/digiwf-task-api-internal";
import {addFinishedTaskIds, isInFinishedProcess} from "./finishedTaskFilter";

if (shouldUseTaskService()) {
  console.log("feature toggle enabled. New tasklist service is used for network requests.");
}

const userTasksQueryId = "user-tasks";
const assignedGroupTasksQueryId = "assigned-group-tasks";
const openGroupTasksQueryId = "open-group-tasks";

export const invalidUserTasks = () =>
  queryClient.invalidateQueries([userTasksQueryId]);
const addUserToTask = (r: Task): Promise<HumanTask> => {
  return (
    r.assignee
      ? getUserInfo(r.assignee)
      : Promise.resolve(undefined)
  )
    .then(user => Promise.resolve(mapTaskFromTaskService(r, isInFinishedProcess(r.id), user)));
};

const handlePageOfTaskResponse = (response: PageOfTasks) => {
  const tasksWithUserPromises = response.content?.map(addUserToTask) || [];
  return Promise.all<HumanTask>(tasksWithUserPromises)
    .then(tasks => Promise.resolve<Page<HumanTask>>({
        content: tasks,
        totalElements: response.totalElements,
        totalPages: response.totalPages || 0,
        size: response.size,
        page: response.page,
      })
    );
};
/**
 *
 * possible sort columns: https://github.com/holunda-io/camunda-bpm-taskpool/blob/develop/view/view-api/src/main/kotlin/Task.kt
 *
 * @param page
 * @param size
 * @param query
 * @param shouldIgnoreFollowUpTasks
 */
const handleTaskLoadingFromTaskService = (
  page: Ref<number>,
  size: Ref<number>,
  query: Ref<string | undefined>,
  shouldIgnoreFollowUpTasks: Ref<boolean | undefined>
) => {
  return callGetTasksFromTaskService(
    page.value,
    size.value,
    query.value,
    shouldIgnoreFollowUpTasks.value
      ? undefined
      : getCurrentDate(),
    "-createTime"
  ).then(handlePageOfTaskResponse);
};

export const useMyTasksQuery = (
  page: Ref<number>,
  size: Ref<number>,
  query: Ref<string | undefined>,
  shouldIgnoreFollowUp: Ref<boolean | undefined>
) => useQuery({
  queryKey: [userTasksQueryId, page.value, size.value, query.value, !shouldIgnoreFollowUp.value],

  queryFn: (): Promise<Page<HumanTask>> => {
    return shouldUseTaskService()
      ? handleTaskLoadingFromTaskService(page, size, query, shouldIgnoreFollowUp)
      : callGetTasksFromEngine(page.value, size.value, query.value, !shouldIgnoreFollowUp.value)
        .then((r) => Promise.resolve(mapTaskPageFromEngineService(r)));
  },
});

export const useOpenGroupTasksQuery = (
  page: Ref<number>,
  size: Ref<number>,
  query: Ref<string | undefined>
) => useQuery({
  queryKey: [openGroupTasksQueryId, page.value, size.value, query.value],
  queryFn: (): Promise<Page<HumanTask>> => {
    return shouldUseTaskService()
      ? callGetOpenGroupTasksFromTaskService(page.value, size.value, query.value)
        .then(handlePageOfTaskResponse)
      : callGetOpenGroupTasksFromEngine(page.value, size.value, query.value)
        .then((r) => Promise.resolve(mapTaskPageFromEngineService(r)));
  },
});

export const useAssignedGroupTasksQuery = (
  page: Ref<number>,
  size: Ref<number>,
  query: Ref<string | undefined>
) => useQuery({
  queryKey: [assignedGroupTasksQueryId, page.value, size.value, query.value],
  queryFn: (): Promise<Page<HumanTask>> => {
    return shouldUseTaskService()
      ? callGetAssignedGroupTasksFromTaskService(page.value, size.value, query.value)
        .then(handlePageOfTaskResponse)
      : callGetAssignedGroupTasksFromEngine(page.value, size.value, query.value)
        .then((r) => Promise.resolve(mapTaskPageFromEngineService(r)));
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
  };
};

export const useAssignTaskMutation = () => {
  const queryClient = useQueryClient();

  const lhmObjectId = (useStore().state as any).user?.info?.lhmObjectId;
  return useMutation<void, any, string>({
    mutationFn: (taskId) => {
      return shouldUseTaskService()
        ? callPostAssignTaskInTaskService(taskId, lhmObjectId)
        : callPostAssignTaskInEngine(taskId);
    },
    onSuccess: () => {
      queryClient.invalidateQueries(["user-tasks"]);
      queryClient.invalidateQueries(["assigned-group-tasks"]);
      queryClient.invalidateQueries(["open-group-tasks"]);
    },
  });
};

export interface LoadTaskFromEngineResultData {
  readonly task: HumanTaskDetails;
  // eslint-disable-next-line @typescript-eslint/ban-types
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
  return shouldUseTaskService()
    ? loadTaskFromTaskService(taskId)
    : loadTasksFromEngine(taskId);
};

const loadTaskFromTaskService = (taskId: string): Promise<LoadTaskResult> => {
  return callGetTaskDetailsFromTaskService(taskId)
    .then(taskResponse => {
      return (taskResponse.assignee
          ? getUserInfo(taskResponse.assignee)
          : Promise.resolve<undefined>(undefined)
      ).then((user) => {
        const taskDetails = mapTaskDetailsFromTaskService(taskResponse, isInFinishedProcess(taskId), user);
        return Promise.resolve<LoadTaskResult>({
          data: {
            task: taskDetails,
            hasDownloadButton: taskDetails.form?.buttons?.statusPdf!.showButton || false,
            model: taskDetails.variables, // FIXME: I guess that is wrong
            followUpDate: taskDetails.followUpDate!,
            cancelText: taskDetails.form?.buttons?.cancel!.buttonText || "Task abbrechen",
            downloadButtonText: taskDetails.form?.buttons?.statusPdf!.buttonText || ""
          }
        });
      });

    }).catch((error: Error | AxiosError) => {
      if (axios.isAxiosError(error) && (error as AxiosError).status === 404) {
        return Promise.resolve({
          error: "Die Aufgabe oder der zugehörige Vorgang wurden bereits abgeschlossen. Die Aufgabe kann daher nicht mehr angezeigt oder bearbeitet werden."
        });
      } else {
        return Promise.resolve({
          error: "Die Aufgabe konnte nicht geladen werden."
        });
      }
    });
};

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
      return task.form.buttons.statusPdf!.showButton || false;
    } else if (task.statusDocument) {
      return true;
    }
    return false; // I guess that is the default value, before it could be nullable
  };

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
    });
  })
    .catch((error: Error | AxiosError) => {
      if (axios.isAxiosError(error) && (error as AxiosError).status === 404) {
        return Promise.resolve({
          error: "Die Aufgabe oder der zugehörige Vorgang wurden bereits abgeschlossen. Die Aufgabe kann daher nicht mehr angezeigt oder bearbeitet werden."
        });
      } else {
        return Promise.resolve({
          error: "Die Aufgabe konnte nicht geladen werden."
        });
      }
    });
};

export interface CancelTaskResult {
  readonly isError: boolean;
  readonly errorMessage?: string;
}

/**
 * @deprecated
 * @param taskId
 */
export const cancelTask = (taskId: string): Promise<CancelTaskResult> => {
  return (
    shouldUseTaskService()
      ? callCancelTaskInTaskService(taskId)
      : callCancelTaskInEngine(taskId)
  ).then(() => {
    queryClient.invalidateQueries([userTasksQueryId]);
    router.push({path: "/task"});

    return Promise.resolve<CancelTaskResult>({
      isError: false
    });

  }).catch(() => {
    return Promise.resolve<CancelTaskResult>({
      isError: true,
      errorMessage: "Die Aufgabe konnte nicht abgebrochen werden."
    });
  });
};

interface CompleteTaskResult {
  readonly errorMessage?: string;
  readonly isError: boolean;
}

export const completeTask = (taskId: string, variables: TaskVariables): Promise<CompleteTaskResult> => {
  return (
    shouldUseTaskService()
      ? callCompleteTaskInTaskService(taskId, variables)
      : callCompleteTaskInEngine(taskId, variables)
  )
    .then(() => {
      addFinishedTaskIds(taskId);
      invalidUserTasks();
      return Promise.resolve<CompleteTaskResult>({
        isError: false,
        errorMessage: undefined,
      });
    }).catch(error => {
      return Promise.resolve<CompleteTaskResult>({
        isError: true,
        errorMessage: error.message
      });
    });
};

interface SetFollowUpResult {
  readonly errorMessage?: string;
  readonly isError: boolean;
}

export const deferTask = (taskId: string, followUp: string): Promise<SetFollowUpResult> => {
  return (
    shouldUseTaskService()
      ? handleDeferTaskInTaskService(taskId, followUp)
      : callSetFollowUpTaskInEngine(taskId, followUp)
  )
    .then(() => {
      invalidUserTasks();
      router.push({path: "/task"});

      return Promise.resolve<SetFollowUpResult>({
        errorMessage: undefined,
        isError: false,
      });
    })
    .catch(error => {
      return Promise.resolve<SetFollowUpResult>({
        errorMessage: error.message === "incorrect date format"
          ? "Ungültiges Format für das Wiedervorlagedatum angegeben"
          : "Die Aufgabe konnte nicht gespeichert werden.",
        isError: true,
      });
    });
};

const handleDeferTaskInTaskService = (taskId: string, followUp: string) => {
  let date: string | undefined = undefined;
  try {
    date = dateToIsoDateTime(followUp);
  } catch (e: any) {
    return Promise.reject(e);
  }
  return callDeferTask(taskId, date);
};

interface SaveTaskResult {
  readonly errorMessage?: string;
  readonly isError: boolean;
}

export const saveTask = (taskId: string, variables: TaskVariables): Promise<SaveTaskResult> => {
  return (
    shouldUseTaskService()
      ? callSaveTaskInTaskService(taskId, variables)
      : callSaveTaskInEngine(taskId, variables)
  ).then(() => Promise.resolve({ // FIXME: invalide task list?
    isError: false,
    errorMessage: undefined
  }))
    .catch(error => Promise.resolve({
      isError: true,
      errorMessage: error.message,
    }));
};

interface AssignTaskResult {
  readonly isError: boolean;
}

export const assignTask = (taskId: string,): Promise<AssignTaskResult> => {
  const userId = store.getters["user/info"].lhmObjectId;
  return (
    shouldUseTaskService()
      ? callPostAssignTaskInTaskService(taskId, userId)
      : callPostAssignTaskInEngine(taskId)
  ).then(() => {
    router.push({path: "/task/" + taskId});
    invalidUserTasks();
    queryClient.invalidateQueries([openGroupTasksQueryId]);
    queryClient.invalidateQueries([assignedGroupTasksQueryId]);
    return Promise.resolve({isError: false});
  }).catch(() => Promise.resolve({isError: true}));
};

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
      });
    })
    .catch(_ => {
      return Promise.resolve({
        isError: true,
        errorMessage: "Das Statusdokument konnte nicht erstellt werden."
      });
    });
};

const base64ToArrayBuffer = (base64: string): Uint8Array => {
  const binaryString = window.atob(base64);
  const binaryLen = binaryString.length;
  const bytes = new Uint8Array(binaryLen);
  for (let i = 0; i < binaryLen; i++) {
    bytes[i] = binaryString.charCodeAt(i);
  }
  return bytes;
};
