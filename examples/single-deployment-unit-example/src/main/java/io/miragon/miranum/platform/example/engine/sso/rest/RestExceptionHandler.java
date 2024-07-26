package io.miragon.miranum.platform.example.engine.sso.rest;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.camunda.bpm.engine.rest.dto.ExceptionDto;
import org.camunda.bpm.engine.rest.exception.ExceptionHandlerHelper;
import org.camunda.bpm.engine.rest.exception.RestException;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.camunda.commons.utils.StringUtil.getStackTrace;


@Provider
public class RestExceptionHandler extends org.camunda.bpm.engine.rest.exception.RestExceptionHandler {

    private static final Logger LOGGER = Logger.getLogger(RestExceptionHandler.class.getSimpleName());

    @Override
    public Response toResponse(RestException exception) {
        Response.Status responseStatus = ExceptionHandlerHelper.getInstance().getStatus(exception);
        ExceptionDto exceptionDto = ExceptionHandlerHelper.getInstance().fromException(exception);

        if (responseStatus == Response.Status.INTERNAL_SERVER_ERROR) {
            LOGGER.log(Level.WARNING, getStackTrace(exception));
        } else if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, getStackTrace(exception));
        }

        return Response
                .status(responseStatus)
                .entity(exceptionDto)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }
}
