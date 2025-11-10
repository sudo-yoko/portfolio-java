package com.example.application;

import com.example.ApplicationDatabaseException;
import com.example.ValidationErrorException;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;

public class ErrorResponseCustomStrategy implements ErrorResponseStrategy {

    @Override
    public ResponseBuilder handleWebApplicationException(WebApplicationException cause) {
        // TODO: WebApplicationException は既に Response を持っている場合あり
        int status = cause.getResponse().getStatus();
        ErrorResponseCustom entity = new ErrorResponseCustom(
                cause.getClass().getSimpleName(),
                cause.getMessage());
        return Response.status(status).entity(entity);
    }

    @Override
    public ResponseBuilder handleValidationErrorException(ValidationErrorException cause) {
        int status = Response.Status.BAD_REQUEST.getStatusCode();
        ErrorResponseCustom entity = new ErrorResponseCustom();
        cause.getErrors().forEach(d -> {
            entity.addError(
                    cause.getClass().getSimpleName(),
                    String.format("%s[%s]", d.getMessage(), d.getField()));
        });
        return Response.status(status).entity(entity);
    }

    @Override
    public ResponseBuilder handleApplicationDatabaseException(ApplicationDatabaseException cause) {
        int status = cause.getStatus();
        ErrorResponseCustom entity = new ErrorResponseCustom(
                cause.getClass().getSimpleName(),
                cause.getMessage());
        return Response.status(status).entity(entity);
    }

    @Override
    public ResponseBuilder handleOtherException(Throwable cause) {
        int status = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
        ErrorResponseCustom entity = new ErrorResponseCustom(
                cause.getClass().getSimpleName(),
                cause.getMessage());
        return Response.status(status).entity(entity);
    }

}
