package com.example.application.domain1.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import com.example.application.CallbackBuilder;
import com.example.application.UrlBuilder;
import com.example.application.domain1.DomainCookie;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * ログインアプリ ログインページ
 */
@WebServlet("/domain1/login/auth")
public class LoginServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(LoginServlet.class.getName());
    private static final String LOG_PREFIX = ">>> [LOGIN]: " + LoginServlet.class.getSimpleName() + ": ";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info(LOG_PREFIX
                + String.format("doGet start. uri->%s, query->%s", req.getRequestURI(), req.getQueryString()));

        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head><title>ログイン</title></head>");
            out.println("<body>");
            out.println("<h2>ログイン</h2>");
            // フォームアクション
            UrlBuilder authentication = new UrlBuilder(req.getContextPath() + "/domain1/login/auth");
            CallbackBuilder callback = new CallbackBuilder(req);
            if (callback.hasValue()) {
                authentication.appendQueryString(callback.toQueryString());
            }
            out.println("<form action='" + authentication.build() + "' method='POST'>");
            out.println("<input type='text' name='id' value='admin' placeholder='ID'><br><br>");
            out.println("<input type='password' name='password' value='123' placeholder='パスワード'><br><br>");
            out.println("<input type='submit' value='ログイン'>");
            // ログインエラーメッセージ
            String error = (String) req.getAttribute("error");
            if (error != null) {
                out.println("<p style='color:red;'>" + error + "</p>");
            }
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info(LOG_PREFIX
                + String.format("doPost start. uri->%s, query->%s", req.getRequestURI(), req.getQueryString()));

        String id = req.getParameter("id");
        String password = req.getParameter("password");

        String clientId = req.getParameter("client_id");
        String redirectUri = req.getParameter("redirect_id");

        if ("admin".equals(id) && "123".equals(password)) {
            // ログイン成功。セッションを作成する
            Session.create(req, id);
            // ログインクッキーを作成する
            DomainCookie.SessionId.create(req, resp);
            //
            CallbackBuilder callback = new CallbackBuilder(req);
            if (callback.hasValue()) {
                UrlBuilder redirect = new UrlBuilder(req.getContextPath() + "/domain1/idp/auth");
                redirect.appendQueryString(callback.toQueryString());
                resp.sendRedirect(redirect.build());
                return;
            }

            // トップページへ
            resp.sendRedirect(req.getContextPath() + "/domain1/login/top");
            return;
        } else {
            req.setAttribute("error", "ログインできません。");
            doGet(req, resp);
        }
    }
}
