import {
  CompleteTO,
  DocumentRestControllerApiFactory,
  FetchUtils,
  HumanTaskDetailTO,
  HumanTaskRestControllerApiFactory,
  PageHumanTaskTO,
  SaveTO,
  StatusDokumentTO
} from "@muenchen/digiwf-engine-api-internal";
import {ApiConfig} from "../ApiConfig";
import {PageOfTasks, TaskApiFactory, TasksApiFactory, TaskWithSchema} from "@muenchen/digiwf-task-api-internal";
import {AxiosError} from "axios";
import {TaskVariables} from "../../middleware/tasks/tasksModels";

/**
 * old api for getting tasks. will be replaced by callGetTasksFromTaskService
 * @deprecated
 * @param page
 * @param size
 * @param query
 * @param followUp
 */
export const callGetTasksFromEngine = (page: number, size: number, query?: string, followUp?: boolean): Promise<PageHumanTaskTO> => {
  const cfg = ApiConfig.getAxiosConfig(FetchUtils.getGETConfig());
  return HumanTaskRestControllerApiFactory(cfg).getTasks(page, size, query, followUp).then((res) => {
    return Promise.resolve(res.data);
  }).catch((err: AxiosError) => Promise.reject(FetchUtils.defaultCatchHandler(err, "Die Aufgaben konnten nicht geladen werden. Bitte versuchen Sie es erneut.")));
};
export const callGetTasksFromTaskService = (page: number, size: number, query?: string, followUp?: string, sort?: string): Promise<PageOfTasks> => {
  // follow-up: YYYY-MM-dd: e.g. 2023-04-17
  const cfg = ApiConfig.getTasklistAxiosConfig(FetchUtils.getGETConfig());
  return TasksApiFactory(cfg).getCurrentUserTasks(page, size, query, followUp, sort)
    .then(res => Promise.resolve(res.data))
    .catch((err: AxiosError) => Promise.reject(FetchUtils.defaultCatchHandler(err, "Die Aufgaben konnten nicht geladen werden. Bitte versuchen Sie es erneut.")));
};

/**
 * old api for getting tasks. will be replaced by callGetOpenGroupTasksFromTaskService
 * @deprecated
 * @param page
 * @param size
 * @param query
 */
export const callGetOpenGroupTasksFromEngine = (page: number, size: number, query?: string): Promise<PageHumanTaskTO> => {
  const cfg = ApiConfig.getAxiosConfig(FetchUtils.getGETConfig());
  return HumanTaskRestControllerApiFactory(cfg).getOpenGroupTasks(page, size, query).then((res) => {
    return Promise.resolve(res.data);
  }).catch((err: AxiosError) => Promise.reject(FetchUtils.defaultCatchHandler(err, "Die Aufgaben konnten nicht geladen werden. Bitte versuchen Sie es erneut.")));
};
export const callGetOpenGroupTasksFromTaskService = (page: number, size: number, query?: string): Promise<PageOfTasks> => {
  const cfg = ApiConfig.getTasklistAxiosConfig(FetchUtils.getGETConfig());
  return TasksApiFactory(cfg).getUnassignedGroupTasks(page, size, query)
    .then((res) => Promise.resolve(res.data))
    .catch((err: AxiosError) => Promise.reject(FetchUtils.defaultCatchHandler(err, "Die Aufgaben konnten nicht geladen werden. Bitte versuchen Sie es erneut.")));
};

/**
 * old api for getting tasks. will be replaced by callGetAssignedGroupTasksFromTaskService
 * @deprecated
 * @param page
 * @param size
 * @param query
 */
export const callGetAssignedGroupTasksFromEngine = (page: number, size: number, query?: string): Promise<PageHumanTaskTO> => {
  const cfg = ApiConfig.getAxiosConfig(FetchUtils.getGETConfig());
  return HumanTaskRestControllerApiFactory(cfg).getAssignedGroupTasks(page, size, query).then((res) => {
    return Promise.resolve(res.data);
  }).catch((err: AxiosError) => Promise.reject(FetchUtils.defaultCatchHandler(err, "Die Aufgaben konnten nicht geladen werden. Bitte versuchen Sie es erneut.")));
};
export const callGetAssignedGroupTasksFromTaskService = (page: number, size: number, query?: string): Promise<PageOfTasks> => {
  const cfg = ApiConfig.getTasklistAxiosConfig(FetchUtils.getGETConfig());
  return TasksApiFactory(cfg).getAssignedGroupTasks(page, size, query).then((res) => {
    return Promise.resolve(res.data);
  }).catch((err: AxiosError) => Promise.reject(FetchUtils.defaultCatchHandler(err, "Die Aufgaben konnten nicht geladen werden. Bitte versuchen Sie es erneut.")));
};

/**
 * @deprecated
 * @param taskId
 */
export const callPostAssignTaskInEngine = (taskId: string): Promise<void> => {
  const cfg = ApiConfig.getAxiosConfig(FetchUtils.getPOSTConfig({}));
  return HumanTaskRestControllerApiFactory(cfg).assignTask(taskId)
    .then(() => Promise.resolve())
    .catch((err: AxiosError) => Promise.reject(FetchUtils.defaultCatchHandler(err, "Die Aufgabe konnte nicht zugewiesen werden.")));
};

export const callPostAssignTaskInTaskService = (taskId: string, assignee: string): Promise<void> => {
  const cfg = ApiConfig.getTasklistAxiosConfig(FetchUtils.getPOSTConfig({}));
  return TaskApiFactory(cfg).assignTask(taskId, {assignee})
    .then(() => Promise.resolve())
    .catch((err: AxiosError) => Promise.reject(FetchUtils.defaultCatchHandler(err, "Die Aufgabe konnte nicht zugewiesen werden.")));
};

/**
 * TaskDetails view calls:
 * only moved out of TaskDetail.vue
 */

/**
 *
 *
 * @deprecated
 * @param taskId
 */
export const callGetTaskDetailsFromEngine = (taskId: string): Promise<HumanTaskDetailTO> => {
  const cfg = ApiConfig.getAxiosConfig(FetchUtils.getGETConfig());
  return HumanTaskRestControllerApiFactory(cfg).getTaskDetail(taskId)
    .then(res => Promise.resolve(res.data));
};

export const callGetTaskDetailsFromTaskService = (taskId: string): Promise<TaskWithSchema> => {
  const cfg = ApiConfig.getTasklistAxiosConfig(FetchUtils.getGETConfig());
  return TaskApiFactory(cfg).getTaskWithSchemaByTaskId(taskId)
    .then((res) => Promise.resolve(res.data))
    .catch((err: AxiosError) => Promise.reject(FetchUtils.defaultCatchHandler(err, "Die Aufgabe konnte nicht geladen werden.")));
};

/**
 * @deprecated
 * @param taskId
 */
export const callCancelTaskInEngine = (taskId: string): Promise<void> => {
  const cfg = ApiConfig.getAxiosConfig(FetchUtils.getPOSTConfig({}));
  return HumanTaskRestControllerApiFactory(cfg).cancelTask(taskId).then(() => Promise.resolve());
};

export const callCancelTaskInTaskService = (taskId: string): Promise<void> => {
  const cfg = ApiConfig.getTasklistAxiosConfig(FetchUtils.getPOSTConfig({}));
  return TaskApiFactory(cfg).cancelTask(taskId).then(() => Promise.resolve());
};


/**
 * @deprecated
 * @param taskId
 * @param variables
 */
export const callCompleteTaskInEngine = (taskId: string, variables: TaskVariables): Promise<void> => {
  const request: CompleteTO = {
    taskId: taskId,
    variables,
  };
  const cfg = ApiConfig.getAxiosConfig(FetchUtils.getPOSTConfig({}));
  return HumanTaskRestControllerApiFactory(cfg).completeTask(request).then(() => Promise.resolve());
};

export const callCompleteTaskInTaskService = (taskId: string, variables: TaskVariables): Promise<void> => {
  console.log("callCompleteTaskInTaskService");
  const cfg = ApiConfig.getTasklistAxiosConfig(FetchUtils.getPOSTConfig({}));
  return TaskApiFactory(cfg).completeTask(taskId, variables)
    .then(() => Promise.resolve())
    .catch((e: AxiosError) => {
    if(e.response?.status === 400) {
      return Promise.reject(FetchUtils.defaultCatchHandler(e, "Validierung Ihrer Eingaben fehlgeschlagen. Bitte überprüfen Sie diese."));
    } else {
      return Promise.reject(FetchUtils.defaultCatchHandler(e, "Die Aufgaben konnten nicht abgeschlossen werden."));
    }
  });
};

/**
 * @deprecated
 * @param taskId
 * @param followUpDate
 */
export const callSetFollowUpTaskInEngine = (taskId: string, followUpDate: string): Promise<void> => {
  const cfg = ApiConfig.getAxiosConfig(FetchUtils.getPOSTConfig({}));
  return HumanTaskRestControllerApiFactory(cfg).followUpTask({
    taskId,
    followUpDate,
  }).then(() => Promise.resolve());
};

export const callDeferTask = (taskId: string, followUpDate: string): Promise<void> => {
  const cfg = ApiConfig.getTasklistAxiosConfig(FetchUtils.getPOSTConfig({}));
  return TaskApiFactory(cfg).deferTask(
    taskId,
    {
      followUpDate
    },
  ).then(() => Promise.resolve());
};


/**
 * @deprecated
 * @param taskId
 * @param variables
 */
export const callSaveTaskInEngine = (taskId: string, variables: TaskVariables): Promise<void> => {
  const request: SaveTO = {
    taskId,
    variables,
  };
  const cfg = ApiConfig.getAxiosConfig(FetchUtils.getPUTConfig({}));
  return HumanTaskRestControllerApiFactory(cfg).saveTask(request).then(() => Promise.resolve());
};

export const callSaveTaskInTaskService = (taskId: string, variables: TaskVariables): Promise<void> => {
  const cfg = ApiConfig.getTasklistAxiosConfig(FetchUtils.getPOSTConfig({}));
  return TaskApiFactory(cfg).saveTaskVariables(taskId, variables)
    .then(() => Promise.resolve())
    .catch((e: AxiosError) => {
      if(e.response?.status === 400) {
        return Promise.reject(FetchUtils.defaultCatchHandler(e, "Validierung Ihrer Eingaben fehlgeschlagen. Bitte überprüfen Sie diese."));
      } else {
        return Promise.reject(FetchUtils.defaultCatchHandler(e, "Die Aufgaben konnten nicht gespeichert werden."));
      }
    });
};

export const callDownloadPdfFromEngine = (taskId: string): Promise<StatusDokumentTO> => {
  const cfg = ApiConfig.getAxiosConfig(FetchUtils.getGETConfig());
  return DocumentRestControllerApiFactory(cfg).getStatusDokumentForTask(taskId).then(res => Promise.resolve(res.data));
};
