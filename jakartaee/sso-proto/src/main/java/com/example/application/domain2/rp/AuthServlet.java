package com.example.application.domain2.rp;

import java.io.IOException;
import java.util.logging.Logger;

import com.example.application.CallbackUrl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * RPアプリ 認証
 */
@WebServlet("/domain2/rp/auth")
public class AuthServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(AuthServlet.class.getName());
    private static final String LOG_PREFIX = ">>> [RP]: " + AuthServlet.class.getSimpleName() + ": ";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info(LOG_PREFIX
                + String.format("doGet start. uri->%s, query->%s", req.getRequestURI(), req.getQueryString()));

        // アクセストークンチェック
        String token = req.getParameter("token");
        if (token == null || token.isBlank()) {
            logger.severe(LOG_PREFIX + "token invalid.");
            resp.sendRedirect(req.getContextPath() + "/domain2/rp/error");
            return;
        }

        // ユーザーセッションを作成する
        Session.create(req, token);

        CallbackUrl callback = new CallbackUrl(req);
        if (callback.hasValue()) {
            resp.sendRedirect(callback.getValue());
            return;
        }
        resp.sendRedirect(req.getContextPath() + "/domain2/rp/top");
        return;
    }

}
