package com.example.application;

import jakarta.servlet.http.HttpServletRequest;

public class CallbackBuilder {
    private static final String CALLBACK = "callback";
    private final UrlBuilder builder;

    public CallbackBuilder(HttpServletRequest req) {
        String callback = req.getParameter(CALLBACK);
        if (callback != null && !callback.isBlank()) {
            this.builder = new UrlBuilder(callback.trim());
        } else {
            this.builder = null;
        }
    }

    public CallbackBuilder(String url) {
        if (url != null && !url.isBlank()) {
            this.builder = new UrlBuilder(url.trim());
        } else {
            this.builder = null;
        }
    }

    public boolean hasValue() {
        return this.builder != null;
    }

    public CallbackBuilder require() {
        if (!hasValue()) {
            throw new IllegalStateException("callback does not exist.");
        }
        return this;
    }

    public CallbackBuilder appendQueryParam(String name, String value) {
        if (!this.hasValue()) {
            throw new IllegalStateException("callback does not exist.");
        }
        this.builder.appendQueryParam(name, value);
        return this;
    }

    public String build() {
        if (!this.hasValue()) {
            throw new IllegalStateException("callback does not exist.");
        }
        return this.builder.build();
    }

    public String buildQueryString() {
        if (!this.hasValue()) {
            throw new IllegalStateException("callback does not exist.");
        }
        return CALLBACK + "=" + this.build();
    }
}
