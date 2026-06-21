package com.example.application.domain1.login;

import java.io.IOException;
import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * ログインアプリケーション セッション
 */
public class AppSession {

    protected static void create(HttpServletRequest req, String id) {
        HttpSession session = req.getSession(true);
        session.setAttribute("id", id);
    }

    protected static Optional<Data> validate(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("id") == null) {
            resp.sendRedirect(req.getContextPath() + "/domain1/login/auth");
            return Optional.empty();
        }
        String id = (String) session.getAttribute("id");
        Data appSession = new Data();
        appSession.setId(id);
        return Optional.of(appSession);
    }

    protected static class Data {
        // ログインID
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
