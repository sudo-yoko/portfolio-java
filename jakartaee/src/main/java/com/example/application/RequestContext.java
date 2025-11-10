package com.example.application;

import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class RequestContext {

    private String errorFormat = "";

    public String getErrorFormat() {
        return errorFormat;
    }

    public void setErrorFormat(String errorFormat) {
        this.errorFormat = (errorFormat == null || errorFormat.isBlank()) ? "" : errorFormat.strip();
    }

}
