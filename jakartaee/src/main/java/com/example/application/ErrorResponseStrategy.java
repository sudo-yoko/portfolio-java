package com.example.application;

import com.example.ApplicationDatabaseException;
import com.example.ValidationErrorException;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response.ResponseBuilder;

public interface ErrorResponseStrategy {

    /**
     * WebApplicationExceptionを処理する
     */
    ResponseBuilder handleWebApplicationException(WebApplicationException cause);

    /**
     * ValidationErrorExceptionを処理する
     */
    ResponseBuilder handleValidationErrorException(ValidationErrorException cause);

    /**
     * ApplicationDatabaseExceptionを処理する
     */
    ResponseBuilder handleApplicationDatabaseException(ApplicationDatabaseException cause);

    /**
     * その他例外を処理する
     */
    ResponseBuilder handleOtherException(Throwable cause);
}
