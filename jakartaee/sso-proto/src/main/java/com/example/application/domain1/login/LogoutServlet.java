package com.example.application.domain1.login;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.logging.Logger;

import com.example.application.domain1.DomainCookie;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * ログアウト
 */
@WebServlet("/domain1/login/logout")
public class LogoutServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(LogoutServlet.class.getName());
    private static final String LOG_PREFIX = ">>> [LOGIN]: " + LogoutServlet.class.getSimpleName() + ": ";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info(LOG_PREFIX + "doGet start.");

        // アプリケーションセッションを破棄
        AppSession.invalidate(req);

        // ドメインクッキーを破棄
        DomainCookie.SessionId.kill(req, resp);

        String callbackUri = req.getContextPath() + "/domain1/login/auth";
        String redirectUri = req.getContextPath() + "/domain1/idp/logout?redirect_uri="
                + URLEncoder.encode(callbackUri, "UTF-8");
        resp.sendRedirect(redirectUri);
        return;
    }

}
