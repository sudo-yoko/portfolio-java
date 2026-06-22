package com.example.application.domain1.idp;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import com.example.application.domain1.LoginCookie;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Identity Provider 同意確認ページ
 */
@WebServlet("/domain1/idp/consent")
public class ConsentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // ログインクッキーの確認
        Optional<String> sessionId = LoginCookie.SessionId.validate(req, resp);
        if (!sessionId.isPresent()) {
            return;
        }

        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head><title>同意確認</title></head>");
            out.println("<body>");
            out.println("<h2>同意確認</h2>");
            String action = req.getContextPath() + "/domain1/idp/consent";
            out.println("<form action='" + action + "' method='POST'>");
            out.println("<button type='submit' name='consent' value='approve'>同意する</button>");
            out.println("<button type='submit' name='consent' value='deny'>同意しない</button>");
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String consent = req.getParameter("consent");
        if ("approve".equals(consent)) {
            AppSession.create(req, true);
            resp.sendRedirect(req.getContextPath() + "/domain1/idp/auth");
        } else if ("deny".equals(consent)) {
            AppSession.create(req, false);
            resp.sendRedirect(req.getContextPath() + "/domain1/login/top");
        } else {

        }
    }

}
