package com.example.application;

import jakarta.servlet.http.HttpServletRequest;

public class CallbackBuilder {
    private static final String KEY_CALLBACK = "callback";
    private final UrlBuilder builder;

    public CallbackBuilder(HttpServletRequest req) {
        String callback = req.getParameter(KEY_CALLBACK);
        if (callback != null && !callback.isBlank()) {
            this.builder = new UrlBuilder(callback);
        } else {
            this.builder = null;
        }
    }

    public CallbackBuilder(String url) {
        if (url != null && !url.isBlank()) {
            this.builder = new UrlBuilder(url);
        } else {
            this.builder = null;
        }
    }

    public boolean hasValue() {
        return this.builder != null;
    }

    public CallbackBuilder appendQueryParam(String name, String value) {
        if (!this.hasValue()) {
            return this;
        }
        this.builder.appendQueryParam(name, value);
        return this;
    }

    public String build() {
        if (!this.hasValue()) {
            throw new RuntimeException("error");
        }
        return this.builder.build();
    }

    public String toQueryString() {
        if (!this.hasValue()) {
            throw new RuntimeException("error");
        }
        return KEY_CALLBACK + "=" + this.build();
    }
}
