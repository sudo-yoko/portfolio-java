package com.example.application.domain1.login;

import java.io.IOException;
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
 * ログインアプリ ログアウト
 */
@WebServlet("/domain1/login/logout")
public class LogoutServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(LogoutServlet.class.getName());
    private static final String LOG_PREFIX = ">>> [LOGIN]: " + LogoutServlet.class.getSimpleName() + ": ";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info(LOG_PREFIX
                + String.format("doGet start. uri->%s, query->%s", req.getRequestURI(), req.getQueryString()));

        // ユーザーセッションを破棄
        Session.invalidate(req);

        // ドメインクッキーを破棄
        DomainCookie.SessionId.kill(req, resp);

        UrlBuilder logout = new UrlBuilder(req.getContextPath() + "/domain1/idp/logout");
        CallbackBuilder callback = new CallbackBuilder(req.getContextPath() + "/domain1/login/auth");
        logout.appendQueryString(callback.buildQueryString());
        resp.sendRedirect(logout.build());
        return;
    }

}
