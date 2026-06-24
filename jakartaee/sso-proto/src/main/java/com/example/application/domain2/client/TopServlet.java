package com.example.application.domain2.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * RPアプリケーション トップページ
 */
@WebServlet("/domain2/client/top")
public class TopServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // ユーザーセッションの確認
        String session = Session.validate(req, resp);
        if (session == null) {
            // 認可
            String authorization = req.getContextPath() + "/domain1/idp/auth";
            String callback = req.getContextPath() + "/domain1/client/auth";
            String clientId = "appB";
            String redirect = authorization + "?clientId=" + clientId + "?callback="
                    + URLEncoder.encode(callback, StandardCharsets.UTF_8);
            resp.sendRedirect(redirect);
            return;
        }

        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head><title>トップページ</title></head>");
            out.println("<body>");
            out.println("<h2>お疲れさまです！</h2>");
            out.println("<p>シングルサインオンの練習は順調です。</p>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
