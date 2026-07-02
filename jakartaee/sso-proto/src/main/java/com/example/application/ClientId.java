package com.example.application;

import jakarta.servlet.http.HttpServletRequest;

public class ClientId {
    private static final String KEY_CLIENT_ID = "clientId";

    private final String clientId;

    public ClientId(HttpServletRequest req) {
        String clientId = req.getParameter(KEY_CLIENT_ID);
        this.clientId = clientId;
    }

    public String getValue() {
        if (clientId == null || clientId.isBlank()) {
            return "";
        }
        return clientId;
    }

    public String toQueryString() {
        return KEY_CLIENT_ID + "=" + this.getValue();
    }
}
