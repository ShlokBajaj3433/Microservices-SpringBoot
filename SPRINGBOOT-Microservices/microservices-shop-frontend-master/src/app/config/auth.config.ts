import { LogLevel, PassedInitialConfig } from 'angular-auth-oidc-client';

export const authConfig: PassedInitialConfig = {
  config: {
    authority: 'http://localhost:8181/realms/spring-microservices-security-realm',
    redirectUrl: window.location.origin + '/',
    postLogoutRedirectUri: window.location.origin + '/',
    clientId: 'angular-client',
    responseType: 'code',
    scope: 'openid',
    silentRenew: true,
    useRefreshToken: true,
    renewTimeBeforeTokenExpiresInSeconds: 30,
    unauthorizedRoute: '/',
    forbiddenRoute: '/',
    autoUserInfo: true,
    logLevel: LogLevel.Debug,
  }
}
