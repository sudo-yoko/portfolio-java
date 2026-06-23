package com.example.application.domain1.idp;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Idpアプリ ユーザーセッション
 */
public class Session {
    private static final Logger logger = Logger.getLogger(Session.class.getName());
    private static final String LOG_PREFIX = ">>> [LOGIN]: " + Session.class.getSimpleName() + ": ";

    private static final String CONSENT = "consent";

    protected static void create(HttpServletRequest req) {
        req.getSession(true);
    }

    protected static void invalidate(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    protected static Boolean getConsent(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute(CONSENT) == null) {
            return null;
        }
        return (boolean) session.getAttribute(CONSENT);
    }

    protected static void setConsent(HttpServletRequest req, Boolean consent) {
        HttpSession session = req.getSession(true);
        session.setAttribute(CONSENT, consent);
    }

    protected static boolean validate(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session == null) {
            logger.info(LOG_PREFIX + "session invalid.");
            String authorization = req.getContextPath() + "/domain1/idp/auth";
            String callback = req.getRequestURI();
            authorization += "?callback=" + URLEncoder.encode(callback, StandardCharsets.UTF_8);
            resp.sendRedirect(authorization);
            return false;
        }
        return true;
    }

}
