import {Configuration, FileApiFactory} from "@miragon/digiwf-task-api-internal";
import {AxiosResponse} from "axios";

/**
 * @param cfg
 * @param formContextId
 * @param fileName
 * @param filePath
 */
export const getPresignedUrlForFileUploadFromTaskservice = (cfg: Configuration, formContextId: string, fileName: string, filePath: string): Promise<string> => {
  return FileApiFactory(cfg)
    .getPresignedUrlForFile(formContextId, fileName, filePath, "POST")
    .then((response: AxiosResponse<string>) => Promise.resolve(response.data));
}

/**
 * @param cfg
 * @param formContextId
 * @param fileName
 * @param filePath
 */
export const getPresignedUrlForFileDownloadFromTaskservice = (cfg: Configuration, formContextId: string, fileName: string, filePath: string): Promise<string> => {
  return FileApiFactory(cfg)
    .getPresignedUrlForFile(formContextId, fileName, filePath, "GET")
    .then((response: AxiosResponse<string>) => Promise.resolve(response.data));
}

/**
 * @param cfg
 * @param formContextId
 * @param fileName
 * @param filePath
 */
export const getPresignedUrlForFileDeletionFromTaskservice = (cfg: Configuration, formContextId: string, fileName: string, filePath: string): Promise<string> => {
  return FileApiFactory(cfg)
    .getPresignedUrlForFile(formContextId, fileName, filePath, "DELETE")
    .then((response: AxiosResponse<string>) => Promise.resolve(response.data));
}

/**
 * @param cfg
 * @param formContextId
 * @param filePath
 */
export const getFileNamesFromTaskservice = (cfg: Configuration, formContextId: string, filePath: string): Promise<string[]> => {
  return FileApiFactory(cfg).getFileNames(formContextId, filePath)
    .then((response: AxiosResponse<string[]>) => Promise.resolve(response.data));
}

