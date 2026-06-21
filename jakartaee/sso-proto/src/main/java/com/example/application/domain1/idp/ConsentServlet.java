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
        Optional<LoginCookie.Data> data = LoginCookie.validate(req, resp);
        if (!data.isPresent()) {
            return;
        }

        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head><title>同意確認</title></head>");
            out.println("<body>");
            out.println("<h2>同意確認</h2>");
            out.println("<form action='login' method='POST'>");
            out.println("<input type='button' value='同意する'>");
            out.println("<input type='button' value='同意しない'>");
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");
        }
    }

}
