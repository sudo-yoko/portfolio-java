package com.example.application.domain1.login;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Logger;

import com.example.application.CallbackBuilder;
import com.example.application.SessionManager;
import com.example.application.UrlBuilder;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

class LoginSessionManager {
    private static final Logger logger = Logger.getLogger(LoginSessionManager.class.getName());
    private static final String LOG_PREFIX = ">>> [LOGIN]: " + LoginSessionManager.class.getSimpleName() + ": ";

    private static final String LOGIN_SESSION = "login.session";

    static void create(HttpServletRequest req, String id) {
        SessionManager.create(req, LOGIN_SESSION, new LoginSession(id));
    }

    static void remove(HttpServletRequest req) {
        SessionManager.remove(req, LOGIN_SESSION);
    }

    static boolean validate(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (exists(req) && get(req).getId() != null) {
            return true;
        }
        logger.severe(LOG_PREFIX + "session invalid.");
        UrlBuilder authentication = new UrlBuilder(req.getContextPath() + "/domain1/login/auth");
        CallbackBuilder callback = new CallbackBuilder(req.getRequestURI());
        authentication.appendQueryString(callback.buildQueryString());
        resp.sendRedirect(authentication.build());
        return false;
    }

    static boolean exists(HttpServletRequest req) {
        return SessionManager.exists(req, LOGIN_SESSION);
    }

    static LoginSession get(HttpServletRequest req) {
        return SessionManager.get(req, LOGIN_SESSION, LoginSession.class);
    }

    static void set(HttpServletRequest req, LoginSession idpSession) {
        SessionManager.set(req, LOGIN_SESSION, idpSession);
    }

    static String getId(HttpServletRequest req) {
        return get(req).getId();
    }

    static void setId(HttpServletRequest req, String id) {
        LoginSession updated = get(req).applyId(id);
        set(req, updated);
    }

    static final class LoginSession implements Serializable {
        private static final long serialVersionUID = 1L;

        private final String id;

        LoginSession() {
            this(null);
        }

        LoginSession(String id) {
            this.id = id;
        }

        String getId() {
            return this.id;
        }

        LoginSession applyId(String id) {
            return new LoginSession(id);
        }
    }
}
