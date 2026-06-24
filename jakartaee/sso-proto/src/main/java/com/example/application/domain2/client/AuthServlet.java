package com.example.application.domain2.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * RPアプリ 認証
 */
@WebServlet("/domain2/client/auth")
public class AuthServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(AuthServlet.class.getName());
    private static final String LOG_PREFIX = ">>> [CLIENT]: " + AuthServlet.class.getSimpleName() + ": ";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info(LOG_PREFIX + "doGet start.");

        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head><title>工事中</title></head>");
            out.println("<body>");
            out.println("<p>シングルサインオンの練習は順調です。</p>");
            out.println("</body>");
            out.println("</html>");
        }
    }

}
