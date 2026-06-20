package com.example.application;

import java.util.ArrayList;
import java.util.List;

/**
 * 独自形式のエラーレスポンス
 */
public class ErrorResponseCustom {
    private List<Error> errors;

    public ErrorResponseCustom() {
        errors = new ArrayList<>();
    }

    public ErrorResponseCustom(String type, String message) {
        this();
        addError(type, message);
    }

    public List<Error> getErrors() {
        return List.copyOf(errors);
    }

    public void addError(String type, String message) {
        errors.add(new Error(type, message));
    }

    public void addAllErrors(List<Error> errors) {
        errors.forEach(e -> addError(e.getType(), e.getMessage()));
    }

    public static class Error {
        private String type;
        private String message;

        public Error() {
        }

        public Error(String type, String message) {
            this.type = type;
            this.message = message;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
