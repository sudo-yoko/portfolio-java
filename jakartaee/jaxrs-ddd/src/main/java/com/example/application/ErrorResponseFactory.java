package com.example.application;

import java.util.Locale;
import java.util.logging.Logger;

import com.example.ApplicationDatabaseException;
import com.example.ValidationErrorException;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;

public class ErrorResponseFactory {
    private static final Logger logger = Logger.getLogger(ErrorResponseFactory.class.getName());
    private static final String LOG_PREFIX = ">>> [" + ErrorResponseFactory.class.getSimpleName() + "]: ";

    // ファクトリーパターンはステートレス・不変として設計する。
    // 内部フィールドはfinalにしてコンストラクタで設定するようにすること
    private final ErrorResponseStrategy strategy;

    private ErrorResponseFactory(ErrorResponseStrategy strategy) {
        this.strategy = strategy;
    }

    public static ErrorResponseFactory resolveStrategy(String errorFormat) {
        ErrorResponseStrategy strategy;
        switch (errorFormat.toLowerCase(Locale.ROOT)) {
            case "custom":
                strategy = new ErrorResponseCustomStrategy();
                break;
            default:
                strategy = new ErrorResponseCustomStrategy();
                break;
        }
        return new ErrorResponseFactory(strategy);
    }

    public Response build(Throwable throwable) {
        Throwable cause = findCause(throwable);

        ResponseBuilder builder;
        if (cause instanceof WebApplicationException) {
            builder = this.strategy.handleWebApplicationException((WebApplicationException) cause);
        } else if (cause instanceof ValidationErrorException) {
            builder = this.strategy.handleValidationErrorException((ValidationErrorException) cause);
        } else if (cause instanceof ApplicationDatabaseException) {
            builder = this.strategy.handleApplicationDatabaseException((ApplicationDatabaseException) cause);
        } else {
            builder = this.strategy.handleOtherException(cause);
        }
        return builder.type(MediaTypes.APPLICATION_JSON_UTF_8).build();
    }

    private Throwable findCause(Throwable throwable) {
        final int max_depth = 10;

        Throwable cause = throwable;
        for (int i = 0; i < max_depth; i++) {
            if (cause.getCause() == null) {
                return cause;
            }
            cause = cause.getCause();
        }
        logger.warning(LOG_PREFIX + "原因例外の取得で、ループの最大回数を超えました。");
        return cause;
    }
}
