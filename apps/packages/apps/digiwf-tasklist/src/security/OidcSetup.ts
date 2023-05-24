import { UserManager, WebStorageStateStore } from 'oidc-client';
export const ssoBaseUrl = import.meta.env.SSO_BASE_URL || "";
export const ssoEngineClientId = import.meta.env.SSO_ENGINE_CLIENT_ID || "";

const oidcConfig = {
  authority: ssoBaseUrl, // Replace with your OIDC provider's authority URL
  client_id: ssoEngineClientId, // Replace with your client ID
  redirect_uri: `${window.location.origin}/loginsuccess.html`, // Replace with your redirect URI
  response_type: 'code',
  scope: 'openid profile', // Replace with the required scopes
  post_logout_redirect_uri: `${window.location.origin}/logout.html` // Replace with your logout redirect URI
};

export const userManager = new UserManager({
  ...oidcConfig,
  userStore: new WebStorageStateStore({ store: window.localStorage })
});
