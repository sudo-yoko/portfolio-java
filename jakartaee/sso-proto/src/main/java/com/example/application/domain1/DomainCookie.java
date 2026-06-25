package com.example.application.domain1;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * ドメイン属性付きCookie
 */
public class DomainCookie {
    private static final Logger logger = Logger.getLogger(DomainCookie.class.getName());
    private static final String LOG_PREFIX = ">>> [LOGIN]: " + DomainCookie.class.getSimpleName() + ": ";

    private static final String SSO_SESSION_ID = "SSO_SESSION_ID";

    public static class SessionId {
        public static void create(HttpServletRequest req, HttpServletResponse resp) {
            Cookie cookie = new Cookie(SSO_SESSION_ID, "proto-token-123");
            // 本番構成はログインアプリとIdpは同じドメインとし、ログインクッキーの有効範囲はそのドメインとする。
            // cookie.setDomain(".sso-proto.com");
            cookie.setPath(req.getContextPath() + "/domain1"); // ドメイン全体で有効
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            resp.addCookie(cookie);
        }

        public static void kill(HttpServletRequest req, HttpServletResponse resp) {
            Cookie cookie = new Cookie(SSO_SESSION_ID, "");
            cookie.setPath(req.getContextPath() + "/domain1");
            cookie.setMaxAge(0);
            resp.addCookie(cookie);
        }

        public static String validate(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            String sessionId = null;
            Cookie[] cookies = req.getCookies();
            if (cookies != null) {
                for (Cookie c : cookies) {
                    if (SSO_SESSION_ID.equals(c.getName())) {
                        sessionId = c.getValue();
                        break;
                    }
                }
            }
            if (sessionId == null) {
                logger.severe(LOG_PREFIX + "cookie invalid.");
                String authentication = req.getContextPath() + "/domain1/login/auth";
                String callback = req.getParameter("callback");
                if (callback == null || callback.isBlank()) {
                    callback = req.getRequestURI();
                }
                authentication += "?callback=" + URLEncoder.encode(callback, StandardCharsets.UTF_8);
                resp.sendRedirect(authentication);
                return null;
            }
            return sessionId;
        }
    }
}
