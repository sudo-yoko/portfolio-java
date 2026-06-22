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
        Optional<String> sessionId = LoginCookie.SessionId.validate(req, resp);
        if (!sessionId.isPresent()) {
            return;
        }
        // セッションの確認
        Optional<Data> optAppSession = AppSession.validate(req, resp);
        if (!optAppSession.isPresent()) {
            return;
        }
        Data appSession = optAppSession.get();
        //
        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head><title>トップページ</title></head>");
            out.println("<body>");
            out.println("<h2>こんにちは、" + appSession.getId() + "さん！</h2>");
            out.println("<p>シングルサインオンの練習を始めましょう。</p>");
            // リンク先
            String href = req.getContextPath() + "/domain1/idp/auth";
            out.println("<p><a href='" + href + "'>別のアプリにログインする</p>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
