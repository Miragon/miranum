export interface HumanTask {
  readonly id: string;
  readonly createTime: string;
  readonly followUpDate?: string;
  readonly followUpDateFormatted?: string;
  readonly processName?: string;
  readonly name: string
  readonly description?: string;
  readonly assigneeId?: string;
  readonly assigneeFormatted?: string;
  readonly inFinishProcess: boolean;
}

// eslint-disable-next-line
export type TaskVariables = any;

export interface HumanTaskDetails extends HumanTask {
  /**
   * @deprecated old formular
   */
  readonly form?: any;
  /**
   * new schema for user forms: is used by https://github.com/koumoul-dev/vuetify-jsonschema-form
   */
  readonly schema?: any;
  readonly variables: TaskVariables

  readonly isCancelable: boolean;

  readonly processInstanceId?: string;
  /**
   * @deprecated
   */
  readonly statusDocument: boolean;
}
