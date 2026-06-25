package com.example.application;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class SsoUtil {

    public static String appendQueryParam(String url, String param) {
        if (param == null || param.isBlank()) {
            return url;
        }

        if (url.contains("?")) {
            return url + "&" + param;
        }

        return url + "?" + param;
    }

    public static String urlEncode(String callback) {
        return URLEncoder.encode(callback, StandardCharsets.UTF_8);
    }
}
