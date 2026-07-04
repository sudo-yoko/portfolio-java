package com.example.application;

import jakarta.servlet.http.HttpServletRequest;

public class ClientId {
    private static final String CLIENT_ID = "clientId";

    private final String clientId;

    public ClientId(HttpServletRequest req) {
        String clientId = req.getParameter(CLIENT_ID);
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
    public ClientId require() {
        if (!hasValue()) {
            throw new IllegalStateException("clientId does not exist.");
        }
        return this;
    }

    public String toQueryString() {
        return CLIENT_ID + "=" + this.getValue();
    }
}
