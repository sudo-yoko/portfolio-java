package com.example.application.domain1.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.logging.Logger;

import com.example.application.domain1.DomainCookie;
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
    private static final Logger logger = Logger.getLogger(TopServlet.class.getName());
    private static final String LOG_PREFIX = ">>> [LOGIN]: " + TopServlet.class.getSimpleName() + ": ";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info(LOG_PREFIX + "doGet start.");

        // ログインクッキーの確認
        Optional<String> sessionId = DomainCookie.SessionId.validate(req, resp);
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
            String href1 = req.getContextPath() + "/domain1/idp/auth";
            out.println("<p><a href='" + href1 + "' target='_blank' rel='noopener noreferrer'>別のアプリにログインする</p>");
            out.println("<br><br>");

            String href2 = req.getContextPath() + "/domain1/login/logout";
            out.println("<p><a href='" + href2 + "'>ログアウトする</p>");

            out.println("</body>");
            out.println("</html>");
        }
    }
}
