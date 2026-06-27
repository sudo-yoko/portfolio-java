package com.example.application.domain1.idp;

import java.io.IOException;
import java.util.logging.Logger;

import com.example.application.CallbackBuilder;
import com.example.application.UrlBuilder;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Idpアプリ ユーザーセッション
 */
public class Session {
    private static final Logger logger = Logger.getLogger(Session.class.getName());
    private static final String LOG_PREFIX = ">>> [LOGIN]: " + Session.class.getSimpleName() + ": ";

    /**
     * 同意確認
     */
    private static final String CONSENT = "consent";

    /**
     * ユーザーセッションを作成する
     */
    protected static void create(HttpServletRequest req) {
        req.getSession(true);
    }

    /**
     * ユーザーセッションを破棄する
     */
    protected static void invalidate(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            logger.info(LOG_PREFIX + "session invalidated.");
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
            logger.severe(LOG_PREFIX + "session invalid.");
            UrlBuilder authorization = new UrlBuilder(req.getContextPath() + "/domain1/idp/auth");
            CallbackBuilder callback = new CallbackBuilder(req.getRequestURI());
            authorization.appendQueryString(callback.toQueryString());
            resp.sendRedirect(authorization.build());
            return false;
        }
        return true;
    }

}
