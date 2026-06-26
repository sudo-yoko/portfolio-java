package com.example.application;

import jakarta.ws.rs.core.UriBuilder;

public class UrlBuilder {
    private final UriBuilder builder;

    public UrlBuilder(String url) {
        if (url == null || url.isBlank()) {
            throw new RuntimeException("error");
        }
        this.builder = UriBuilder.fromUri(url.trim());
    }

    public UrlBuilder appendQueryParam(String name, String value) {
        if (name == null || value == null) {
            throw new RuntimeException("error");
        }
        this.builder.queryParam(name, value);
        return this;
    }

    public UrlBuilder appendQueryString(String queryString) {
        if (queryString == null || !queryString.contains("=")) {
            throw new RuntimeException("error");
        }
        String parts[] = queryString.split("=", 2);
        this.builder.queryParam(parts[0], parts[1]);
        return this;
    }

    public String build() {
        return this.builder.build().toString();
    }

}
