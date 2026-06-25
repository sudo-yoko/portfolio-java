package com.example.application.domain1.idp;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * RPアプリ エラーページ
 */
@WebServlet("/domain1/idp/error")
public class ErrorServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(ErrorServlet.class.getName());
    private static final String LOG_PREFIX = ">>> [CLIENT]: " + ErrorServlet.class.getSimpleName() + ": ";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info(LOG_PREFIX
                + String.format("doGet start. uri->%s, query->%s", req.getRequestURI(), req.getQueryString()));

        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head><title>エラー</title></head>");
            out.println("<body style='background-color: #FFFFE0;'>");
            out.println("<h2>エラー</h2>");
            out.println("</body>");
            out.println("</html>");
        }
    }

}
