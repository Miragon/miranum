import {
  CompleteTO,
  Configuration,
  DocumentRestControllerApiFactory,
  FetchUtils,
  HumanTaskDetailTO,
  HumanTaskRestControllerApiFactory,
  PageHumanTaskTO,
  SaveTO,
  StatusDokumentTO
} from "@miragon/digiwf-engine-api-internal";
import {ApiConfig} from "../ApiConfig";
import {PageOfTasks, TaskApiFactory, TasksApiFactory, TaskWithSchema} from "@miragon/digiwf-task-api-internal"
import {configuredAxios} from "../statusCodeHandling";

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
  }).catch((err: any) => Promise.reject(FetchUtils.defaultCatchHandler(err, "Die Aufgaben konnten nicht geladen werden. Bitte versuchen Sie es erneut.")))
};
export const callGetTasksFromTaskService = (page: number, size: number, query?: string, followUp?: string): Promise<PageOfTasks> => {
  // follow-up: YYYY-MM-dd: e.g. 2023-04-17
  const cfg = ApiConfig.getTasklistAxiosConfig(FetchUtils.getGETConfig());
  return TasksApiFactory(cfg).getCurrentUserTasks(page, size, query) // FIXME: followUp?
    .then(res => Promise.resolve(res.data))
    .catch((err: any) => Promise.reject(FetchUtils.defaultCatchHandler(err, "Die Aufgaben konnten nicht geladen werden. Bitte versuchen Sie es erneut.")))
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
  }).catch((err: any) => Promise.reject(FetchUtils.defaultCatchHandler(err, "Die Aufgaben konnten nicht geladen werden. Bitte versuchen Sie es erneut.")))
};
export const callGetOpenGroupTasksFromTaskService = (page: number, size: number, query?: string): Promise<PageOfTasks> => {
  const cfg = ApiConfig.getTasklistAxiosConfig(FetchUtils.getGETConfig());
  return TasksApiFactory(cfg).getUnassignedGroupTasks(page, size, query)
    .then((res) => Promise.resolve(res.data))
    .catch((err: any) => Promise.reject(FetchUtils.defaultCatchHandler(err, "Die Aufgaben konnten nicht geladen werden. Bitte versuchen Sie es erneut.")))
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
  }).catch((err: any) => Promise.reject(FetchUtils.defaultCatchHandler(err, "Die Aufgaben konnten nicht geladen werden. Bitte versuchen Sie es erneut.")))
};
export const callGetAssignedGroupTasksFromTaskService = (page: number, size: number, query?: string): Promise<PageOfTasks> => {
  const cfg = ApiConfig.getTasklistAxiosConfig(FetchUtils.getGETConfig());
  return TasksApiFactory(cfg).getAssignedGroupTasks(page, size, query).then((res) => {
    return Promise.resolve(res.data);
  }).catch((err: any) => Promise.reject(FetchUtils.defaultCatchHandler(err, "Die Aufgaben konnten nicht geladen werden. Bitte versuchen Sie es erneut.")))
};

/**
 * @deprecated
 * @param taskId
 */
export const callPostAssignTaskInEngine = (taskId: string): Promise<void> => {
  const cfg = ApiConfig.getAxiosConfig(FetchUtils.getPOSTConfig({}));
  return HumanTaskRestControllerApiFactory(cfg).assignTask(taskId)
    .then(() => Promise.resolve())
    .catch((err: any) => Promise.reject(FetchUtils.defaultCatchHandler(err, "Die Aufgabe konnte nicht zugewiesen werden.")));
};

export const callPostAssignTaskInTaskService = (taskId: string, assignee: string): Promise<void> => {
  const cfg = ApiConfig.getTasklistAxiosConfig(FetchUtils.getPOSTConfig({}));
  return TaskApiFactory(cfg).assignTask(taskId, {assignee})
    .then(() => Promise.resolve())
    .catch((err: any) => Promise.reject(FetchUtils.defaultCatchHandler(err, "Die Aufgabe konnte nicht zugewiesen werden.")));
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
}

export const callGetTaskDetailsFromTaskService = (taskId: string): Promise<TaskWithSchema> => {
  const cfg = ApiConfig.getTasklistAxiosConfig(FetchUtils.getGETConfig());
  return TaskApiFactory(cfg).getTaskWithSchemaByTaskId(taskId)
    .then((res) => Promise.resolve(res.data))
    .catch((err: any) => Promise.reject(FetchUtils.defaultCatchHandler(err, "Die Aufgabe konnte nicht geladen werden.")));
}

/**
 * @deprecated
 * @param taskId
 */
export const callCancelTaskInEngine = (taskId: string): Promise<void> => {
  const cfg = ApiConfig.getAxiosConfig(FetchUtils.getPOSTConfig({}));
  return HumanTaskRestControllerApiFactory(cfg).cancelTask(taskId).then(() => Promise.resolve());
}

/**
 * @deprecated
 * @param taskId
 * @param variables
 */
export const callCompleteTaskInEngine = (taskId: string, variables: any): Promise<void> => {
  const request: CompleteTO = {
    taskId: taskId,
    variables,
  };
  const cfg = ApiConfig.getAxiosConfig(FetchUtils.getPOSTConfig({}));
  return HumanTaskRestControllerApiFactory(cfg).completeTask(request).then(() => Promise.resolve());
}

export const callCompleteTaskInTaskService = (taskId: string, variables: any): Promise<void> => {
  const cfg = ApiConfig.getTasklistAxiosConfig(FetchUtils.getPOSTConfig({}));
  return TaskApiFactory(cfg).completeTask(taskId, variables).then(() => Promise.resolve());
}

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
}

export const callDeferTask = (taskId: string, followUpDate: string): Promise<void> => {
  const cfg = ApiConfig.getTasklistAxiosConfig(FetchUtils.getPOSTConfig({}));
  return TaskApiFactory(cfg).deferTask(
    taskId,
    {
      followUpDate
    },
  ).then(() => Promise.resolve());
}


/**
 * @deprecated
 * @param taskId
 * @param variables
 */
export const callSaveTaskInEngine = (taskId: string, variables: any): Promise<void> => {
  const request: SaveTO = {
    taskId,
    variables,
  };
  const cfg = ApiConfig.getAxiosConfig(FetchUtils.getPUTConfig({}));
  return HumanTaskRestControllerApiFactory(cfg).saveTask(request).then(() => Promise.resolve());
}

export const callSaveTaskInTaskService = (taskId: string, variables: any): Promise<void> => {
  const cfg = ApiConfig.getTasklistAxiosConfig(FetchUtils.getPOSTConfig({}));
  return TaskApiFactory(cfg).saveTaskVariables(taskId, variables).then(() => Promise.resolve())
}

export const callDownloadPdfFromEngine = (taskId: string): Promise<StatusDokumentTO> => {
  const cfg = ApiConfig.getAxiosConfig(FetchUtils.getGETConfig());
  return DocumentRestControllerApiFactory(cfg).getStatusDokumentForTask(taskId).then(res => Promise.resolve(res.data));
}


export const callAssignTaskInEngine = (taskId: string): Promise<void> => {
  const cfg = ApiConfig.getAxiosConfig(FetchUtils.getPOSTConfig({}));
  return HumanTaskRestControllerApiFactory(cfg).assignTask(taskId).then(() => Promise.resolve());
}

export const callAssignTaskInTaskService = (taskId: string, userId: string): Promise<void> => {
  const cfg = ApiConfig.getAxiosConfig(FetchUtils.getPOSTConfig({}));
  return TaskApiFactory(cfg).assignTask(taskId, {
    assignee: userId,
  }).then(() => Promise.resolve());
};

const getFactoryFromConfig = (cfg: Configuration) => HumanTaskRestControllerApiFactory(cfg, undefined, configuredAxios);
