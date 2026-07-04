package com.example.application.domain3.rp;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * RPアプリケーション トップページ
 */
@WebServlet("/domain3/rp/top")
public class TopServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(TopServlet.class.getName());
    private static final String LOG_PREFIX = ">>> [RP3]: " + TopServlet.class.getSimpleName() + ": ";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info(LOG_PREFIX
                + String.format("doGet start. uri->%s, query->%s", req.getRequestURI(), req.getQueryString()));

        // ユーザーセッションの確認
        String session = Session.validate(req, resp);
        if (session == null) {
            return;
        }

        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head><title>トップページ</title></head>");
            out.println("<body style='background-color: #FFE4E1;'>");
            out.println("<h2>お疲れさまです！</h2>");
            out.println("<p>シングルサインオンの練習は順調です。</p>");
            String logout = req.getContextPath() + "/domain3/rp/logout";
            out.println("<p><a href='" + logout + "'>ログアウトする</p>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
