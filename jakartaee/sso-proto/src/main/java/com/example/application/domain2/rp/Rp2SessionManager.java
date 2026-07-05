package com.example.application.domain2.rp;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Logger;

import com.example.application.CallbackBuilder;
import com.example.application.SessionManager;
import com.example.application.UrlBuilder;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * RP2 セッション操作クラス
 */
class Rp2SessionManager {
    private static final Logger logger = Logger.getLogger(Rp2SessionManager.class.getName());
    private static final String LOG_PREFIX = ">>> [RP2]: " + Rp2SessionManager.class.getSimpleName() + ": ";

    private static final String RP2_SESSION = "rp2.session";

    static void create(HttpServletRequest req, String id) {
        SessionManager.create(req, RP2_SESSION, new Rp2Session(id));
    }

    static void remove(HttpServletRequest req) {
        SessionManager.remove(req, RP2_SESSION);
    }

    static boolean validate(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (exists(req) && get(req).getCode() != null) {
            return true;
        }
        logger.severe(LOG_PREFIX + "session invalid.");
        UrlBuilder authorization = new UrlBuilder(req.getContextPath() + "/domain1/idp/auth");
        // UrlBuilder redirect = new UrlBuilder(authorization.build());
        authorization.appendQueryParam("clientId", ClientId.VALUE);
        CallbackBuilder callback = new CallbackBuilder(req.getContextPath() + "/domain2/rp/auth");
        authorization.appendQueryString(callback.buildQueryString());
        resp.sendRedirect(authorization.build());
        return false;
    }

    static boolean exists(HttpServletRequest req) {
        return SessionManager.exists(req, RP2_SESSION);
    }

    static Rp2Session get(HttpServletRequest req) {
        return SessionManager.get(req, RP2_SESSION, Rp2Session.class);
    }

    static void set(HttpServletRequest req, Rp2Session idpSession) {
        SessionManager.set(req, RP2_SESSION, idpSession);
    }

    static String getId(HttpServletRequest req) {
        return get(req).getCode();
    }

    static void setId(HttpServletRequest req, String code) {
        Rp2Session updated = get(req).applyCode(code);
        set(req, updated);
    }

    static final class Rp2Session implements Serializable {
        private static final long serialVersionUID = 1L;

        private final String code;

        Rp2Session() {
            this(null);
        }

        Rp2Session(String code) {
            this.code = code;
        }

        String getCode() {
            return this.code;
        }

        Rp2Session applyCode(String code) {
            return new Rp2Session(code);
        }
    }
}
