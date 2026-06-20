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
 * ログインページ
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(MediaTypes.TEXT_HTML_UTF_8);
        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head><title>ログイン</title></head>");
            out.println("<body>");
            out.println("<h2>ログイン</h2>");
            String error = (String) req.getAttribute("error");
            if (error != null) {
                out.println("<p style='color:red;'>" + error + "</p>");
            }
            out.println("<form action='login' method='POST'>");
            out.println("<input type='text' name='id' placeholder='ID'><br><br>");
            out.println("<input type='password' name='password' placeholder='パスワード'><br><br>");
            out.println("<input type='submit' value='ログイン'>");
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String password = req.getParameter("password");

        if ("admin".equals(id) && "123".equals(password)) {
            HttpSession session = req.getSession(true);
            session.setAttribute("id", id);
            resp.sendRedirect("top");

        } else {
            req.setAttribute("error", "ログインできません。");
            doGet(req, resp);
        }
    }

}
