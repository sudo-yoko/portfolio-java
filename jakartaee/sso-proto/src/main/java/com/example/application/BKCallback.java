package com.example.application;

import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;

// TODO: 後で削除

public class BKCallback {
    private static final String KEY_CALLBACK = "callback";

    public static class Factory {
        public static Optional<Builder> from(String url) {
            if (url != null && !url.isBlank()) {
                return Optional.of(new Builder(url));
            }
            return Optional.empty();
        }

        public static Optional<Builder> from(HttpServletRequest req) {
            String callback = req.getParameter(KEY_CALLBACK);
            return from(callback);
        }
    }

    public static class Builder {
        private final UrlBuilder builder;

        public Builder(String url) {
            this.builder = new UrlBuilder(url);
        }

        public Builder appendQueryParam(String name, String value) {
            this.builder.appendQueryParam(name, value);
            return this;
        }

        public String build() {
            return this.builder.build();
        }

        public String buildQueryString() {
            return KEY_CALLBACK + "=" + this.build();
        }
    }
}
