package com.example.application.domain1.idp;

import java.io.IOException;
import java.util.logging.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * ログアウト
 */
@WebServlet("/domain1/idp/logout")
public class LogoutServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(LogoutServlet.class.getName());
    private static final String LOG_PREFIX = ">>> [IDP]: " + LogoutServlet.class.getSimpleName() + ": ";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info(LOG_PREFIX + "doGet start.");

        // アプリケーションセッションを破棄
        Session.invalidate(req);

        String callbackUri = req.getParameter("redirect_uri");
        if (callbackUri != null && !callbackUri.isBlank()) {
            resp.sendRedirect(callbackUri);
            return;
        }
        return;
    }

}
