package com.example.application.domain1.idp;

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
 * Idpアプリ SSOのキャンセル
 */
@WebServlet("/domain1/idp/cancel")
public class CancelServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(CancelServlet.class.getName());
    private static final String LOG_PREFIX = ">>> [IDP]: " + CancelServlet.class.getSimpleName() + ": ";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info(LOG_PREFIX + "doGet start.");

        // ログインクッキーの確認
        String sessionId = DomainCookie.SessionId.validate(req, resp);
        if (sessionId == null) {
            return;
        }
        // セッションの確認
        boolean valid = Session.validate(req, resp);
        if (!valid) {
            return;
        }

        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head><title>トップページ</title></head>");
            out.println("<body>");
            out.println("<h2>シングルサインオンをキャンセルしました！</h2>");
            out.println("<p>練習は順調です。</p>");
            // リンク先
            String href = req.getContextPath() + "/domain1/idp/retry";
            out.println("<p><a href='" + href + "'>シングルサインオンを再度試みる</p>");
            out.println("</body>");
            out.println("</html>");
        }
    }

}
