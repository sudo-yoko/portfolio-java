package com.example.application;

import jakarta.servlet.http.HttpServletRequest;

public class ClientIdBuilder {
    private static final String CLIENT_ID = "clientId";

    private final String clientId;

    public ClientIdBuilder(HttpServletRequest req) {
        String clientId = req.getParameter(CLIENT_ID);
        this.clientId = clientId;
    }

    public ClientIdBuilder(String clientId) {
        this.clientId = clientId;
    }

    public String getValue() {
        return clientId;
    }

    public boolean hasValue() {
        return clientId != null && !clientId.isBlank();
    }

    /**
     * 必須チェック
     */
    public ClientIdBuilder require() {
        if (!hasValue()) {
            throw new IllegalStateException("clientId does not exist.");
        }
        return this;
    }

    public String buildQueryString() {
        return CLIENT_ID + "=" + this.getValue();
    }
}
