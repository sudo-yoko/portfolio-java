package com.example.application.domain3.rp;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/domain3/rp/logout")
public class LogoutServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(LogoutServlet.class.getName());
    private static final String LOG_PREFIX = ">>> [RP3]: " + LogoutServlet.class.getSimpleName() + ": ";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info(LOG_PREFIX
                + String.format("doGet start. uri->%s, query->%s", req.getRequestURI(), req.getQueryString()));

        // ユーザーセッションを破棄
        Rp3SessionManager.remove(req);

        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head><title>トップページ</title></head>");
            out.println("<body style='background-color: #FFE4E1;'>");
            out.println("<h2>ログアウトしました。</h2>");
            out.println("<p>シングルサインオンの練習は順調です。</p>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // TODO: オープンリダイレクタ対応

}
