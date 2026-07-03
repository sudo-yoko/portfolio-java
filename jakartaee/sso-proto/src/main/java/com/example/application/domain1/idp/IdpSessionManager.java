package com.example.application.domain1.idp;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Logger;

import com.example.application.CallbackBuilder;
import com.example.application.SessionManager;
import com.example.application.UrlBuilder;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// NOTE: パッケージプライベートクラス
class IdpSessionManager {
    private static final Logger logger = Logger.getLogger(IdpSessionManager.class.getName());
    private static final String LOG_PREFIX = ">>> [IDP]: " + IdpSessionManager.class.getSimpleName() + ": ";

    private static final String IDP_SESSION = "idp.session";

    static void create(HttpServletRequest req) {
        SessionManager.create(req, IDP_SESSION, new IdpSession());
    }

    static void remove(HttpServletRequest req) {
        SessionManager.remove(req, IDP_SESSION);
    }

    static boolean validate(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (SessionManager.exists(req, IDP_SESSION)) {
            return true;
        }
        logger.severe(LOG_PREFIX + "session invalid.");
        UrlBuilder authorization = new UrlBuilder(req.getContextPath() + "/domain1/idp/auth");
        CallbackBuilder callback = new CallbackBuilder(req.getRequestURI());
        authorization.appendQueryString(callback.buildQueryString());
        resp.sendRedirect(authorization.build());
        return false;
    }

    static IdpSession get(HttpServletRequest req) {
        return SessionManager.get(req, IDP_SESSION, IdpSession.class);
    }

    static void set(HttpServletRequest req, IdpSession idpSession) {
        SessionManager.set(req, IDP_SESSION, idpSession);
    }

    static Boolean getConsent(HttpServletRequest req) {
        return get(req).getConsent();
    }

    static void setConsent(HttpServletRequest req, Boolean consent) {
        IdpSession updated = get(req).applyConsent(consent);
        set(req, updated);
    }

    static String getProp2(HttpServletRequest req) {
        return get(req).getProp2();
    }

    static void setProp2(HttpServletRequest req, String prop2) {
        IdpSession updated = get(req).applyProp2(prop2);
        set(req, updated);
    }

    static final class IdpSession implements Serializable {
        private static final long serialVersionUID = 1L;

        private final Boolean consent;
        private final String prop2;

        IdpSession() {
            this(null, null);
        }

        IdpSession(Boolean consent, String prop2) {
            this.consent = consent;
            this.prop2 = prop2;
        }

        Boolean getConsent() {
            return this.consent;
        }

        String getProp2() {
            return this.prop2;
        }

        IdpSession applyConsent(Boolean consent) {
            return new IdpSession(consent, this.prop2);
        }

        IdpSession applyProp2(String prop2) {
            return new IdpSession(this.consent, prop2);
        }
    }
}
