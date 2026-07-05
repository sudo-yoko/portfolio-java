package com.example.application.domain3.rp;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Logger;

import com.example.application.CallbackBuilder;
import com.example.application.SessionManager;
import com.example.application.UrlBuilder;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

class Rp3SessionManager {
    private static final Logger logger = Logger.getLogger(Rp3SessionManager.class.getName());
    private static final String LOG_PREFIX = ">>> [RP3]: " + Rp3SessionManager.class.getSimpleName() + ": ";

    private static final String RP3_SESSION = "rp3.session";

    static void create(HttpServletRequest req, String id) {
        SessionManager.create(req, RP3_SESSION, new Rp3Session(id));
    }

    static void remove(HttpServletRequest req) {
        SessionManager.remove(req, RP3_SESSION);
    }

    static boolean validate(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (exists(req) && get(req).getCode() != null) {
            return true;
        }
        logger.severe(LOG_PREFIX + "session invalid.");
        UrlBuilder authorization = new UrlBuilder(req.getContextPath() + "/domain1/idp/auth");
        // UrlBuilder redirect = new UrlBuilder(authorization.build());
        authorization.appendQueryParam("clientId", ClientId.VALUE);
        CallbackBuilder callback = new CallbackBuilder(req.getContextPath() + "/domain3/rp/auth");
        authorization.appendQueryString(callback.buildQueryString());
        resp.sendRedirect(authorization.build());
        return false;
    }

    static boolean exists(HttpServletRequest req) {
        return SessionManager.exists(req, RP3_SESSION);
    }

    static Rp3Session get(HttpServletRequest req) {
        return SessionManager.get(req, RP3_SESSION, Rp3Session.class);
    }

    static void set(HttpServletRequest req, Rp3Session idpSession) {
        SessionManager.set(req, RP3_SESSION, idpSession);
    }

    static String getId(HttpServletRequest req) {
        return get(req).getCode();
    }

    static void setId(HttpServletRequest req, String code) {
        Rp3Session updated = get(req).applyCode(code);
        set(req, updated);
    }

    static final class Rp3Session implements Serializable {
        private static final long serialVersionUID = 1L;

        private final String code;

        Rp3Session() {
            this(null);
        }

        Rp3Session(String code) {
            this.code = code;
        }

        String getCode() {
            return this.code;
        }

        Rp3Session applyCode(String code) {
            return new Rp3Session(code);
        }
    }
}
