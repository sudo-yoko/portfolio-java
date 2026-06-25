package com.example.application;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.http.HttpServletRequest;

public final class CallbackUrl {
    private static final String KEY = "callback";
    private final String value;

    public CallbackUrl(HttpServletRequest req) {
        String callback = req.getParameter(KEY); // NOTE: getParameterはデコード済みを返す
        this.value = callback;
    }

    public CallbackUrl(String url) {
        this.value = url;
    }

    public boolean hasValue() {
        return value != null && !value.isBlank();
    }

    public String getValue() {
        if (!this.hasValue()) {
            throw new RuntimeException("Callback URL does not exist");
        }
        return this.value;
    }

    public CallbackUrl appendQueryParam(String param) {
        if (param == null || param.isBlank() || !this.hasValue()) {
            return this;
        }
        // TODO: 値オブジェクトではなく、ビルダーパターンでの実装も検討
        String newValue;
        if (this.value.contains("?")) {
            newValue = this.value + "&" + param;
        } else {
            newValue = this.value + "?" + param;
        }
        return new CallbackUrl(newValue);
    }

    public String toQueryString() {
        if (!this.hasValue()) {
            return "";
        }
        return KEY + "=" + URLEncoder.encode(this.value, StandardCharsets.UTF_8);
    }

}
