package com.example.application.domain1.login;

import java.io.IOException;
import java.io.PrintWriter;

import com.example.application.domain1.LoginCookie;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * ログインアプリケーション ログインページ
 */
@WebServlet("/domain1/login/auth")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
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
            out.println(String.format("<form action='%s' method='POST'>", req.getContextPath() + "/domain1/login/auth"));
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

        String clientId = req.getParameter("client_id");
        String redirectUri = req.getParameter("redirect_id");

        if ("admin".equals(id) && "123".equals(password)) {
            // ログイン成功。セッションを作成する
            // HttpSession session = req.getSession(true);
            // session.setAttribute("id", id);
            AppSession.create(req, id);

            // ログインクッキーを作成する
            // Cookie cookie = new Cookie("SSO_SESSION_ID", "proto-token-123");
            // // 本番構成はログインアプリとIdpは同じドメインとし、ログインクッキーの有効範囲はそのドメインとする。
            // // cookie.setDomain(".sso-proto.com");
            // // cookie.setPath("/"); // ドメイン全体で有効
            // cookie.setHttpOnly(true);
            // cookie.setSecure(true);
            // resp.addCookie(cookie);
            LoginCookie.create(req, resp);

            resp.sendRedirect(req.getContextPath() + "/domain1/login/top");

        } else {
            req.setAttribute("error", "ログインできません。");
            doGet(req, resp);
        }
    }

}
