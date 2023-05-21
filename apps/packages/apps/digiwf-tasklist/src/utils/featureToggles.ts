export const isServiceTaskServiceEnabled = (): boolean => localStorage.getItem("FEATURE_USE_TASK_SERVICE")?.toLocaleLowerCase().trim() === "true"
