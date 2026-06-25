package com.example.application.domain1.login;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * ログインアプリ ユーザーセッション
 */
public class Session {
    private static final Logger logger = Logger.getLogger(Session.class.getName());
    private static final String LOG_PREFIX = ">>> [LOGIN]: " + Session.class.getSimpleName() + ": ";

    private static final String ID = "id";

    /**
     * ユーザーセッションを作成する
     */
    protected static void create(HttpServletRequest req, String id) {
        HttpSession session = req.getSession(true);
        session.setAttribute(ID, id);
    }

    /**
     * ユーザーセッションを破棄する
     */
    protected static void invalidate(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    /**
     * ユーザーセッションを検証する
     */
    protected static String validate(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute(ID) == null) {
            logger.severe(LOG_PREFIX + "session invalid.");
            String authentication = req.getContextPath() + "/domain1/login/auth";
            String callback = req.getRequestURI();
            authentication += "?callback=" + URLEncoder.encode(callback, StandardCharsets.UTF_8);
            resp.sendRedirect(authentication);
            return null;
        }
        return (String) session.getAttribute(ID);
    }

}
