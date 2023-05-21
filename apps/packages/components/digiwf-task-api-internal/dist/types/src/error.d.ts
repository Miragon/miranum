export declare const enum Levels {
    INFO = "info",
    WARNING = "warning",
    ERROR = "error"
}
export declare class ApiError extends Error {
    level: string;
    constructor({ level, message }: {
        level?: string;
        message?: string;
    });
}
