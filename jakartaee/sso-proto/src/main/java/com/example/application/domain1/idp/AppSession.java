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

    protected static void create(HttpServletRequest req) {
        HttpSession session = req.getSession(true);
        session.setAttribute("consent", null);
    }

    protected static Optional<Data> validate(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("consent") == null) {
            resp.sendRedirect(req.getContextPath() + "/login/top");
            return Optional.empty();
        }
        boolean consent = (boolean) session.getAttribute("consent");
        Data appSession = new Data();
        appSession.setConsent(consent);
        return Optional.of(appSession);
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
