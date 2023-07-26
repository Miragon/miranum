/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik
 * der Landeshauptstadt München, 2022
 */
package io.muenchendigital.digiwf.s3.integration.security;

import io.muenchendigital.digiwf.spring.security.authentication.UserAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


/**
 * This filter logs the username for requests.
 */
@Component
@Order(1)
@Slf4j
@RequiredArgsConstructor
public class RequestResponseLoggingFilter implements Filter {

  private static final String REQUEST_LOGGING_MODE_ALL = "all";

  private static final String REQUEST_LOGGING_MODE_CHANGING = "changing";

  private static final List<String> CHANGING_METHODS = Arrays.asList("POST", "PUT", "PATCH", "DELETE");

  /**
   * The property or a zero length string if no property is available.
   */
  @Value("${security.logging.requests:}")
  private String requestLoggingMode;

  private final UserAuthenticationProvider userAuthenticationProvider;

  /**
   * {@inheritDoc}
   */
  @Override
  public void init(final FilterConfig filterConfig) throws ServletException {
    log.debug("Initializing filter: {}", this);
  }

  /**
   * The method logs the username extracted out of the {@link SecurityContext},
   * the kind of HTTP-Request, the targeted URI and the response http status code.
   */
  @Override
  public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
      throws IOException, ServletException {
    chain.doFilter(request, response);
    final HttpServletRequest httpRequest = (HttpServletRequest) request;
    final HttpServletResponse httpResponse = (HttpServletResponse) response;
    if (checkForLogging(httpRequest)) {
      log.info("User {} executed {} on URI {} with http status {}",
          userAuthenticationProvider.getLoggedInUser(),
          httpRequest.getMethod(),
          httpRequest.getRequestURI(),
          httpResponse.getStatus()
      );
    }
  }

  @Override
  public void destroy() {
    log.debug("Destructing filter: {}", this);
  }

  /**
   * The method checks if logging the username should be done.
   *
   * @param httpServletRequest The request to check for logging.
   * @return True if logging should be done otherwise false.
   */
  private boolean checkForLogging(HttpServletRequest httpServletRequest) {
    return requestLoggingMode.equals(REQUEST_LOGGING_MODE_ALL)
        || (requestLoggingMode.equals(REQUEST_LOGGING_MODE_CHANGING)
        && CHANGING_METHODS.contains(httpServletRequest.getMethod()));
  }

}
