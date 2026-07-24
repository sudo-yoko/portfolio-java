package com.example.application;

import jakarta.servlet.http.HttpServletRequest;

public class ClientIdBuilder {
    private static final String CLIENT_ID = "clientId";

    private final String clientId;

    public ClientIdBuilder(HttpServletRequest req) {
        String clientId = req.getParameter(CLIENT_ID);
        this.clientId = clientId == null ? null : clientId.trim();
    }

    public ClientIdBuilder(String clientId) {
        this.clientId = clientId == null ? null : clientId.trim();
    }

    public String getValue() {
        return clientId;
    }

    public boolean hasValue() {
        return clientId != null && !clientId.isBlank();
    }

    public ClientIdBuilder require() {
        if (!hasValue()) {
            throw new IllegalStateException("clientId does not exist.");
        }
        return this;
    }

    public String buildQueryString() {
        if (!hasValue()) {
            throw new IllegalStateException("clientId does not exist.");
        }
        return CLIENT_ID + "=" + this.getValue();
    }
}
