package com.example.application;

public class SsoUtil {
    // public static String[] splitQueryString(String url) {
    // String parts[] = url.split("\\?");

    // if (parts.length < 2 || parts[1].isBlank()) {
    // return new String[0];
    // }
    // return parts[1].split("&");
    // }

    // public static String joinQueryString(String params[]) {
    // if (params == null) {
    // return "";
    // }
    // return String.join("&", params);
    // }

    public static String appendQueryParam(String url, String param) {
        if (param == null || param.isBlank()) {
            return url;
        }

        if (url.contains("?")) {
            return url + "&" + param;
        }

        return url + "?" + param;
    }
}
