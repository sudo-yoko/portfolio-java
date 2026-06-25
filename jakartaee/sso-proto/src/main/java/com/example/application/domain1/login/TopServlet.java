package com.example.application.domain1.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import com.example.application.domain1.DomainCookie;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * ログインアプリ トップページ
 */
@WebServlet("/domain1/login/top")
public class TopServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(TopServlet.class.getName());
    private static final String LOG_PREFIX = ">>> [LOGIN]: " + TopServlet.class.getSimpleName() + ": ";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info(LOG_PREFIX
                + String.format("doGet start. uri->%s, query->%s", req.getRequestURI(), req.getQueryString()));

        // ログインクッキーの確認
        String sessionId = DomainCookie.SessionId.validate(req, resp);
        if (sessionId == null) {
            return;
        }
        // ユーザーセッションの確認
        String id = Session.validate(req, resp);
        if (id == null) {
            return;
        }

        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head><title>トップページ</title></head>");
            out.println("<body>");
            out.println("<h2>こんにちは、" + id + "さん！</h2>");
            out.println("<p>シングルサインオンの練習を始めましょう。</p>");
            // 認可
            String authorization = req.getContextPath() + "/domain1/idp/auth";
            out.println(
                    "<p><a href='" + authorization + "' target='_blank' rel='noopener noreferrer'>別のアプリにログインする</p>");
            // ログアウト
            String logout = req.getContextPath() + "/domain1/login/logout";
            out.println("<p><a href='" + logout + "'>ログアウトする</p>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
