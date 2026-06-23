package com.example.application.domain1.idp;

import java.io.IOException;
import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Idpアプリケーション セッション
 */
public class AppSession {
    private static final String CONSENT = "consent";

    protected static void create(HttpServletRequest req) {
        req.getSession(true);
    }

    protected static void create(HttpServletRequest req, Boolean consent) {
        HttpSession session = req.getSession(true);
        session.setAttribute(CONSENT, consent);
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

    protected static Optional<Data> validate(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute(CONSENT) == null) {
            resp.sendRedirect(req.getContextPath() + "/domain1/idp/auth");
            return Optional.empty();
        }
        boolean consent = (boolean) session.getAttribute(CONSENT);
        Data data = new Data();
        data.setConsent(consent);
        return Optional.of(data);
    }

    protected static class Data {

        // 同意確認（null:未確認、true: 同意済み、false: 同意しない）
        private Boolean consent = null;

        public Boolean getConsent() {
            return consent;
        }

        public void setConsent(Boolean consent) {
            this.consent = consent;
        }
    }
}
