package com.example.application.domain1.idp;

import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Idpアプリ ユーザーセッション
 */
public class Session {
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
            resp.sendRedirect(req.getContextPath() + "/domain1/idp/auth");
            return false;
        }
        return true;
    }

}
