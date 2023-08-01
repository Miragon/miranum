import {ApiConfig} from "../ApiConfig";
import {
  FetchUtils,
  PageOfTasks,
  TaskApiFactory,
  TasksApiFactory,
  TaskWithSchema
} from "@miragon/digiwf-task-api-internal";
import {AxiosError} from "axios";
import {TaskVariables} from "../../middleware/tasks/tasksModels";

export const callGetTasksFromTaskService = (page: number, size: number, query?: string, followUp?: string, sort?: string): Promise<PageOfTasks> => {
  // follow-up: YYYY-MM-dd: e.g. 2023-04-17
  const cfg = ApiConfig.getTasklistAxiosConfig(FetchUtils.getGETConfig());
  return TasksApiFactory(cfg).getCurrentUserTasks(page, size, query, followUp, sort)
    .then(res => Promise.resolve(res.data))
    .catch((err: AxiosError) => Promise.reject(FetchUtils.defaultCatchHandler(err, "Die Aufgaben konnten nicht geladen werden. Bitte versuchen Sie es erneut.")));
};

export const callGetOpenGroupTasksFromTaskService = (page: number, size: number, query?: string): Promise<PageOfTasks> => {
  const cfg = ApiConfig.getTasklistAxiosConfig(FetchUtils.getGETConfig());
  return TasksApiFactory(cfg).getUnassignedGroupTasks(page, size, query)
    .then((res) => Promise.resolve(res.data))
    .catch((err: AxiosError) => Promise.reject(FetchUtils.defaultCatchHandler(err, "Die Aufgaben konnten nicht geladen werden. Bitte versuchen Sie es erneut.")));
};

export const callGetAssignedGroupTasksFromTaskService = (page: number, size: number, query?: string): Promise<PageOfTasks> => {
  const cfg = ApiConfig.getTasklistAxiosConfig(FetchUtils.getGETConfig());
  return TasksApiFactory(cfg).getAssignedGroupTasks(page, size, query).then((res) => {
    return Promise.resolve(res.data);
  }).catch((err: AxiosError) => Promise.reject(FetchUtils.defaultCatchHandler(err, "Die Aufgaben konnten nicht geladen werden. Bitte versuchen Sie es erneut.")));
};

export const callPostAssignTaskInTaskService = (taskId: string, assignee: string): Promise<void> => {
  console.log("assignee: " + assignee);
  const cfg = ApiConfig.getTasklistAxiosConfig(FetchUtils.getPOSTConfig({}));
  return TaskApiFactory(cfg).assignTask(taskId, {assignee})
    .then(() => Promise.resolve())
    .catch((err: AxiosError) => Promise.reject(FetchUtils.defaultCatchHandler(err, "Die Aufgabe konnte nicht zugewiesen werden.")));
};

/**
 * TaskDetails view calls:
 * only moved out of TaskDetail.vue
 */

export const callGetTaskDetailsFromTaskService = (taskId: string): Promise<TaskWithSchema> => {
  const cfg = ApiConfig.getTasklistAxiosConfig(FetchUtils.getGETConfig());
  return TaskApiFactory(cfg).getTaskWithSchemaByTaskId(taskId)
    .then((res) => Promise.resolve(res.data))
    .catch((err: AxiosError) => Promise.reject(FetchUtils.defaultCatchHandler(err, "Die Aufgabe konnte nicht geladen werden.")));
};

export const callCancelTaskInTaskService = (taskId: string): Promise<void> => {
  const cfg = ApiConfig.getTasklistAxiosConfig(FetchUtils.getPOSTConfig({}));
  return TaskApiFactory(cfg).cancelTask(taskId).then(() => Promise.resolve());
};

export const callCompleteTaskInTaskService = (taskId: string, variables: TaskVariables): Promise<void> => {
  const cfg = ApiConfig.getTasklistAxiosConfig(FetchUtils.getPOSTConfig({}));
  return TaskApiFactory(cfg).completeTask(taskId, variables)
    .then(() => Promise.resolve())
    .catch((e: AxiosError) => {
      if (e.response?.status === 400) {
        return Promise.reject(FetchUtils.defaultCatchHandler(e, "Validierung Ihrer Eingaben fehlgeschlagen. Bitte überprüfen Sie diese."));
      } else {
        return Promise.reject(FetchUtils.defaultCatchHandler(e, "Die Aufgaben konnten nicht abgeschlossen werden."));
      }
    });
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

export const callSaveTaskInTaskService = (taskId: string, variables: TaskVariables): Promise<void> => {
  const cfg = ApiConfig.getTasklistAxiosConfig(FetchUtils.getPOSTConfig({}));
  return TaskApiFactory(cfg).saveTaskVariables(taskId, variables)
    .then(() => Promise.resolve())
    .catch((e: AxiosError) => {
      if (e.response?.status === 400) {
        return Promise.reject(FetchUtils.defaultCatchHandler(e, "Validierung Ihrer Eingaben fehlgeschlagen. Bitte überprüfen Sie diese."));
      } else {
        return Promise.reject(FetchUtils.defaultCatchHandler(e, "Die Aufgaben konnten nicht gespeichert werden."));
      }
    });
};

