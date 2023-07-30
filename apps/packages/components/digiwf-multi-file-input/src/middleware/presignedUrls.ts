import {
  Configuration,
  FetchUtils,
  ServiceInstanceFileRestControllerApiFactory,
  ServiceStartFileRestControllerApiFactory
} from "@miragon/digiwf-engine-api-internal";
import {Ref} from "vue";
import {
  getFileNamesFromTaskservice,
  getPresignedUrlForFileDeletionFromTaskservice,
  getPresignedUrlForFileDownloadFromTaskservice,
  getPresignedUrlForFileUploadFromTaskservice
} from "@/apiClient/taskServiceCalls";

interface EngineInteractionConfig {
  readonly formContext: any;
  readonly apiEndpoint: string;
  readonly filePath: Ref<string>
  readonly taskServiceApiEndpoint: string;
}

export const getPresignedUrlForPost = async (file: File, config: EngineInteractionConfig): Promise<string> => {
  const {filePath, formContext, apiEndpoint, taskServiceApiEndpoint} = config;
  const engineAxiosConfig = axiosConfig(apiEndpoint);
  const taskServiceAxiosConfig = axiosConfig(taskServiceApiEndpoint);

  let res: any;
  if (formContext!.type === "start") {
    res = await ServiceStartFileRestControllerApiFactory(engineAxiosConfig).getPresignedUrlForFileUpload(
      formContext!.id,
      file!.name,
      filePath.value
    );
  } else if (formContext!.type == "task") {
    res = await getPresignedUrlForFileUploadFromTaskservice(taskServiceAxiosConfig, formContext!.id, file!.name, filePath.value)
    // res.data does not exist
    return res;
  } else {
    //type "instance"
    res = await ServiceInstanceFileRestControllerApiFactory(engineAxiosConfig).getPresignedUrlForFileUpload1(
      formContext!.id,
      file!.name,
      filePath.value
    );
  }

  return res.data;
}

export const getPresignedUrlForGet = async (filename: string, config: EngineInteractionConfig): Promise<string> => {
  const {apiEndpoint, taskServiceApiEndpoint, filePath, formContext} = config;
  const engineAxiosConfig = axiosConfig(apiEndpoint);
  const taskServiceAxiosConfig = axiosConfig(taskServiceApiEndpoint);

  let res: any;
  if (formContext!.type === "start") {
    res = await ServiceStartFileRestControllerApiFactory(
      engineAxiosConfig
    ).getPresignedUrlForFileDownload(
      formContext!.id,
      filename,
      filePath.value
    );
  } else if (formContext!.type == "task") {
    res = await getPresignedUrlForFileDownloadFromTaskservice(
      taskServiceAxiosConfig,
      formContext!.id,
      filename,
      filePath.value
    );
    // res.data does not exist
    return res;
  } else {
    //type "instance"
    res = await ServiceInstanceFileRestControllerApiFactory(engineAxiosConfig).getPresignedUrlForFileDownload1(
      formContext!.id,
      filename,
      filePath.value
    );
  }

  return res.data;
}

export const getPresignedUrlForDelete = async (filename: string, config: EngineInteractionConfig): Promise<string> => {
  const {apiEndpoint, filePath, formContext, taskServiceApiEndpoint} = config;
  const engineDeleteAxiosConfig = FetchUtils.getAxiosConfig(FetchUtils.getDELETEConfig());
  engineDeleteAxiosConfig.basePath = apiEndpoint;

  const taskServiceAxiosConfig = axiosConfig(taskServiceApiEndpoint);

  let res: any;
  if (formContext!.type === "start") {
    res = await ServiceStartFileRestControllerApiFactory(
      engineDeleteAxiosConfig
    ).getPresignedUrlForFileDeletion(
      formContext!.id,
      filename,
      filePath.value
    );
  } else if (formContext!.type == "task") {
    res = await getPresignedUrlForFileDeletionFromTaskservice(
      taskServiceAxiosConfig,
      formContext!.id,
      filename,
      filePath.value
    );
    // res.data does not exist
    return res;
  } else {
    //type "instance"
    res = await ServiceInstanceFileRestControllerApiFactory(engineDeleteAxiosConfig).getPresignedUrlForFileDeletion1(
      formContext!.id,
      filename,
      filePath.value
    );
  }

  return res.data;
}

export const getFilenames = async (config: EngineInteractionConfig): Promise<string[]> => {
  const {apiEndpoint, filePath, formContext, taskServiceApiEndpoint} = config;
  const engineAxiosConfig = axiosConfig(apiEndpoint);

  const taskServiceAxiosConfig = axiosConfig(taskServiceApiEndpoint);

  let res: any;
  if (formContext!.type === "start") {
    res = await ServiceStartFileRestControllerApiFactory(engineAxiosConfig).getFileNames(
      formContext!.id,
      filePath.value
    );
  } else if (formContext!.type == "task") {
    res = await getFileNamesFromTaskservice(
      taskServiceAxiosConfig,
      formContext!.id,
      filePath.value
    );
    // res.data does not exist
    return res;
  } else {
    //type "instance"
    res = await ServiceInstanceFileRestControllerApiFactory(engineAxiosConfig).getFileNames1(
      formContext!.id,
      filePath.value
    );
  }
  return res.data;
}

const axiosConfig = (basePath: string): Configuration => {
  const cfg = FetchUtils.getAxiosConfig(FetchUtils.getGETConfig());
  cfg.baseOptions.headers = {"Content-Type": "application/json"};
  cfg.basePath = basePath;
  return cfg;
}
