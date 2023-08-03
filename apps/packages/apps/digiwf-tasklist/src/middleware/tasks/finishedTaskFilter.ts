export const FINISHED_TASK_IDS_KEY = "FINISHED_TASK_IDS";

export const getFinishedTaskIds = (): string[] => {
  const value = sessionStorage.getItem(FINISHED_TASK_IDS_KEY);
  if(!value) {
    return [];
  }
  try {
    return JSON.parse(value) as string[];
  } catch (e) {
    console.warn("could not parse finished task ids from session storage");
    return [];
  }
};

const setFinishedTaskIds = (ids: string[]) => {
  sessionStorage.setItem(FINISHED_TASK_IDS_KEY, JSON.stringify(ids));
};

export const isInFinishedProcess = (taskId: string): boolean => getFinishedTaskIds().includes(taskId);
export const addFinishedTaskIds = (taskId: string) => setFinishedTaskIds([...getFinishedTaskIds(), taskId]);
