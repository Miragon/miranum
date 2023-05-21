export const enum Levels {
    INFO = 'info',
    WARNING = 'warning',
    ERROR = 'error'
}

export class ApiError extends Error {
    level: string;
    constructor({level = Levels.ERROR, message = "Ein unbekannter Fehler ist aufgetreten, bitte den Administrator informieren."}: { level?: string; message?: string }) {
		// Übergibt die verbleibenden Parameter (einschließlich Vendor spezifischer Parameter) dem Error Konstruktor
		super(message);
	
		// Behält den richtigen Stack-Trace für die Stelle bei, an der unser Fehler ausgelöst wurde
        this.stack = new Error().stack;
	
		// Benutzerdefinierte Informationen
		this.level = level;
		this.message = message;
    }
}