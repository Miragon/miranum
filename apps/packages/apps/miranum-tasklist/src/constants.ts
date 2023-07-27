export const mucatarURL = function (username: string, mode?: string) {
    const baseUrl = "https://mucatar.muenchen.de/avatar?uid=";
    let usedMode = "fallbackIdenticon";
    if (mode) {
      usedMode = mode;
    }
    return `${baseUrl}${username}&m=${usedMode}`;
};

