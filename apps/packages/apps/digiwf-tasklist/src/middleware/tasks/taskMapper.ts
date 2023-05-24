import {HumanTaskDetailTO, HumanTaskTO, PageHumanTaskTO} from "@miragon/digiwf-engine-api-internal";
import {HumanTask, HumanTaskDetails} from "./tasksModels";
import {Page} from "../commonModels";
import {PageOfTasks, TaskWithSchema} from "@miragon/digiwf-task-api-internal";
import {Task} from "@miragon/digiwf-task-api-internal/src";
import {DateTime} from "luxon";
import {formatIsoDate, formatIsoDateTime} from "../../utils/time";
import {User} from "../user/userModels";

/**
 * @deprecated is only necessary until tasks will provided by task service in production
 * @param response
 */
export const mapTaskFromEngineService = (response: HumanTaskTO): HumanTask => {
  return {
    followUpDate: response.followUpDate ? DateTime.fromFormat(response.followUpDate, "yyyy-MM-dd").toLocaleString(DateTime.DATE_SHORT) : undefined,
    createTime: DateTime.fromISO(response.creationTime!).toLocaleString(DateTime.DATETIME_SHORT),
    id: response.id!,
    description: response.description,
    name: response.name || "-",
    processName: response.processName,
    assigneeId: response.assignee,
    assigneeFormatted: response.assigneeFormatted,
  }
}

/**
 * @deprecated is only necessary until tasks will provided by task service in production
 * @param response
 */
export const mapTaskPageFromEngineService = (response: PageHumanTaskTO): Page<HumanTask> => {
  return {
    content: response.content?.map(mapTaskFromEngineService),
    totalElements: response.totalElements,
    totalPages: response.totalPages!,
  }
}
/**
 * @deprecated
 * @param response
 */
export const mapTaskDetailsFromEngineService = (response: HumanTaskDetailTO): HumanTaskDetails => {
  return {
    ...mapTaskFromEngineService(response),
    form: response.form,
    variables: response.variables,
    processInstanceId: response.processInstanceId,
    schema: response.jsonSchema,
    statusDocument: response.statusDocument || false,
    isCancelable: response.form?.buttons?.cancel!.showButton || false
  }
}

export const mapTaskFromTaskService = (response: Task, user?: User): HumanTask => {
  return {
    createTime: response.createTime ? formatIsoDateTime(response.createTime) : "-",
    followUpDate: response.followUpDate ? formatIsoDate(response.followUpDate) : undefined,
    id: response.id!,
    description: response.description,
    name: response.name || "-",
    processName: response.processName,
    assigneeId: response.assignee,
    assigneeFormatted: user && user.fullInfo,
  };
}

export const mapTaskPageFromTaskService = (response: PageOfTasks, taskMapperFunction: (task: Task) => HumanTask): Page<HumanTask> => {
  return {
    content: response.content?.map(taskMapperFunction),
    totalElements: response.totalElements,
    totalPages: response.totalPages!,
  }
}

export const mapTaskDetailsFromTaskService = (response: TaskWithSchema, user?: User): HumanTaskDetails => {
  return {
    createTime: response.createTime ? formatIsoDateTime(response.createTime) : "-",
    followUpDate: response.followUpDate ? formatIsoDate(response.followUpDate) : undefined,
    id: response.id!,
    description: response.description,
    name: response.name || "-",
    processName: response.processName,
    assigneeId: response.assignee,
    assigneeFormatted: user && `${user.firstName} ${user.surname} (${user.ou})`,
    form: undefined, // FIXME: check if it is correct
    variables: response.variables,
    processInstanceId: response.processInstanceId,
    schema: response.schema,
    statusDocument: false,

    isCancelable: false // TODO: change to Task property when Task Response is updated by TaskList backend service
  }
}
