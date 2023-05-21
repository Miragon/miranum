import axios from "axios";

/**
 * heartbeats the api gateway for getting new session via set-cookie header
 */
export const startSessionReloading = () => {
  callSessionInformation().then(sessionInformation => {
    const timeoutInSeconds = sessionInformation?.timeoutInSeconds
    if(!timeoutInSeconds) {
      console.warn("could not get session timeout information in seconds.")
      return;
    }
    if(timeoutInSeconds < 10) {
      console.warn("session timeout to small, do not start heartbeat.")
      return;
    }
    const intervalId = setInterval(() => {
        callSessionInformation()
          .then((sessionInformation) => {
            if(!sessionInformation.timeoutInSeconds) {
              clearInterval(intervalId);
            }
          })
          .catch(() => {
            clearInterval(intervalId)
          })
      }, (timeoutInSeconds - 5) * 1000
    )
  })
}

interface SessionInformationResponse {
  readonly timeoutInSeconds?: number;
}

const callSessionInformation = () => axios.get<SessionInformationResponse>("/api/gateway/session").then(r => Promise.resolve(r.data))
