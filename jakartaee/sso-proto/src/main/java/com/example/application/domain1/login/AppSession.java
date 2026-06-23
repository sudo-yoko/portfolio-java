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
    private static final String ID = "id";

    protected static void create(HttpServletRequest req, String id) {
        HttpSession session = req.getSession(true);
        session.setAttribute(ID, id);
    }

    protected static Optional<Data> validate(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute(ID) == null) {
            resp.sendRedirect(req.getContextPath() + "/domain1/login/auth");
            return Optional.empty();
        }
        // セッションデータを返す
        String id = (String) session.getAttribute(ID);
        Data data = new Data();
        data.setId(id);
        return Optional.of(data);
    }

    protected static void invalidate(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
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
