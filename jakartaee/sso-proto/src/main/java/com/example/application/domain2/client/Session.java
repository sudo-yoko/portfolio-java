package com.example.application.domain2.client;

import java.io.IOException;
import java.util.logging.Logger;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * RPアプリ ユーザーセッション
 */
public class Session {
    private static final Logger logger = Logger.getLogger(Session.class.getName());
    private static final String LOG_PREFIX = ">>> [CLIENT]: " + Session.class.getSimpleName() + ": ";

    /**
     * ユーザーセッションを作成する
     */
    protected static void create(HttpServletRequest req, String id) {
        HttpSession session = req.getSession(true);
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
        if (session == null) {
            logger.info(LOG_PREFIX + "session invalid.");
            // String authentication = req.getContextPath() + "/domain1/login/auth";
            // String callback = req.getRequestURI();
            // authentication += "?callback=" + URLEncoder.encode(callback,
            // StandardCharsets.UTF_8);
            // resp.sendRedirect(authentication);
            return null;
        }
        // return (String) session.getAttribute(ID);
        return "";
    }

}
