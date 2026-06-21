package com.example.application.domain1.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import com.example.application.domain1.LoginCookie;
import com.example.application.domain1.login.AppSession.Data;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * ログインアプリケーション トップページ
 */
@WebServlet("/domain1/login/top")
public class TopServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // ログインクッキーの確認
        // String sessionId = null;
        // Cookie[] cookies = req.getCookies();
        // if (cookies != null) {
        // for (Cookie c : cookies) {
        // if ("SSO_SESSION_ID".equals(c.getName())) {
        // sessionId = c.getValue();
        // }
        // }
        // }
        // if (sessionId == null) {
        // resp.sendRedirect(req.getContextPath() + "/login/auth");
        // return;
        // }
        Optional<LoginCookie.Data> data = LoginCookie.validate(req, resp);
        if (!data.isPresent()) {
            return;
        }

        // セッションの確認
        // HttpSession session = req.getSession(false);
        // if (session == null || session.getAttribute("id") == null) {
        // resp.sendRedirect(req.getContextPath() + "/login/auth");
        // return;
        // }
        // String id = (String) session.getAttribute("id");
        Optional<Data> optAppSession = AppSession.validate(req, resp);
        if (!optAppSession.isPresent()) {
            return;
        }
        Data appSession = optAppSession.get();

        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head><title>トップページ</title></head>");
            out.println("<body>");
            out.println("<h2>こんにちは、" + appSession.getId() + "さん！</h2>");
            out.println("<p>シングルサインオンの練習を始めましょう。</p>");
            out.println(String.format("<p><a href='%s' target='_blank' rel='noopener noreferrer'>別のアプリにログインする</p>",
                    req.getContextPath() + "/domain1/idp/auth"));
            out.println("</body>");
            out.println("</html>");
        }
    }
}
