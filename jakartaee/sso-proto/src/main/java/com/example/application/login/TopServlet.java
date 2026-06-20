package com.example.application.login;

import java.io.IOException;
import java.io.PrintWriter;

import com.example.application.MediaTypes;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * トップページ
 */
@WebServlet("/top")
public class TopServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("id") == null) {
            resp.sendRedirect("login");
            return;
        }
        String id = (String) session.getAttribute("id");

        resp.setContentType(MediaTypes.TEXT_HTML_UTF_8);
        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head><title>トップページ</title></head>");
            out.println("<body>");
            out.println("<h2>こんにちは、" + id + "さん！</h2>");
            out.println("<p>シングルサインオンの検証を始めましょう。</p>");
            out.println("<p>別のアプリにログインする</p>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
