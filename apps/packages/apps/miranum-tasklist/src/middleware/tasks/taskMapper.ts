import {HumanTaskDetailTO, HumanTaskTO, PageHumanTaskTO} from "@muenchen/digiwf-engine-api-internal";
import {HumanTask, HumanTaskDetails} from "./tasksModels";
import {Page} from "../commonModels";
import {PageOfTasks, TaskWithSchema} from "@muenchen/digiwf-task-api-internal";
import {Task} from "@muenchen/digiwf-task-api-internal/src";
import {DateTime} from "luxon";
import {formatIsoDate, formatIsoDateTime, getDateFromIsoDateTime} from "../../utils/time";
import {User} from "../user/userModels";

/**
 * @deprecated is only necessary until tasks will provided by task service in production
 * @param response
 */
export const mapTaskFromEngineService = (response: HumanTaskTO): HumanTask => {
  return {
    followUpDate: response.followUpDate ? DateTime.fromFormat(response.followUpDate, "yyyy-MM-dd").toLocaleString(DateTime.DATE_SHORT) : undefined,
    followUpDateFormatted: response.followUpDate ? DateTime.fromFormat(response.followUpDate, "yyyy-MM-dd").toLocaleString(DateTime.DATE_SHORT) : undefined,
    createTime: DateTime.fromISO(response.creationTime!).toLocaleString(DateTime.DATETIME_SHORT),
    id: response.id!,
    description: response.description,
    name: response.name || "-",
    processName: response.processName,
    assigneeId: response.assignee,
    assigneeFormatted: response.assigneeFormatted,
    inFinishProcess: false,
  };
};

/**
 * @deprecated is only necessary until tasks will provided by task service in production
 * @param response
 */
export const mapTaskPageFromEngineService = (response: PageHumanTaskTO): Page<HumanTask> => {
  return {
    content: response.content?.map(mapTaskFromEngineService) ||  [],
    totalElements: response.totalElements,
    totalPages: response.totalPages || 0,
    page: response.number || 0,
    size: response.size || 0,
  };
};
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
    isCancelable: response.form?.buttons?.cancel!.showButton || false,
  };
};

export const mapTaskFromTaskService = (response: Task, inFinishProcess: boolean , user?: User): HumanTask => {
  return {
    createTime: response.createTime ? formatIsoDateTime(response.createTime) : "-",
    followUpDate: response.followUpDate ? getDateFromIsoDateTime(response.followUpDate) : undefined,
    followUpDateFormatted: response.followUpDate ? formatIsoDate(response.followUpDate) : undefined,
    id: response.id!,
    description: response.description,
    name: response.name || "-",
    processName: response.processName,
    assigneeId: response.assignee,
    assigneeFormatted: user && user.fullInfo,
    inFinishProcess,
  };
};

export const mapTaskPageFromTaskService = (response: PageOfTasks, taskMapperFunction: (task: Task) => HumanTask): Page<HumanTask> => {
  return {
    content: response.content?.map(taskMapperFunction),
    totalElements: response.totalElements,
    totalPages: response.totalPages || 0,
    page: response.page || 0,
    size: response.size || 0,
  };
};

export const mapTaskDetailsFromTaskService = (response: TaskWithSchema, inFinishProcess: boolean, user?: User): HumanTaskDetails => {
  return {
    createTime: response.createTime ? formatIsoDateTime(response.createTime) : "-",
    followUpDate: response.followUpDate ? getDateFromIsoDateTime(response.followUpDate) : undefined,
    followUpDateFormatted: response.followUpDate ? formatIsoDate(response.followUpDate) : undefined,
    id: response.id!,
    description: response.description,
    name: response.name || "-",
    processName: response.processName,
    assigneeId: response.assignee,
    assigneeFormatted: user && `${user.firstName} ${user.surname} (${user.ou})`,
    form: response.schemaType === "VUETIFY_FORM_BASE" ? response.schema : undefined,
    variables: response.variables,
    processInstanceId: response.processInstanceId,
    schema: response.schemaType === "SCHEMA_BASED" ? response.schema : undefined,

    statusDocument: false,
    inFinishProcess,
    isCancelable: response.cancelable
  };
};
