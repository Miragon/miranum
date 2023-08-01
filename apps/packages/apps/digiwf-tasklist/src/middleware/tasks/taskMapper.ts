import {HumanTask, HumanTaskDetails} from "./tasksModels";
import {Page} from "../commonModels";
import {PageOfTasks, TaskWithSchema} from "@miragon/digiwf-task-api-internal";
import {Task} from "@miragon/digiwf-task-api-internal/src";
import {formatIsoDate, formatIsoDateTime, getDateFromIsoDateTime} from "../../utils/time";
import {User} from "../user/userModels";

export const mapTaskFromTaskService = (response: Task, inFinishProcess: boolean, user?: User): HumanTask => {
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
    variables: response.variables,
    processInstanceId: response.processInstanceId,
    schema: response.schemaType === "SCHEMA_BASED" ? response.schema : undefined,
    statusDocument: false,
    inFinishProcess,
    isCancelable: response.cancelable
  };
};
