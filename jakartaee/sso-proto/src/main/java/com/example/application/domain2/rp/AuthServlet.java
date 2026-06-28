package com.example.application.domain2.rp;

import java.io.IOException;
import java.util.logging.Logger;

import com.example.application.CallbackBuilder;

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

        // 認可コード
        String code = req.getParameter("code");
        if (code == null || code.isBlank()) {
            // TODO: 認可コード無い場合は、IdP経由でログイン画面に戻すか自前のログイン画面を表示するか検討
            logger.severe(LOG_PREFIX + "code invalid.");
            resp.sendRedirect(req.getContextPath() + "/domain2/rp/error");
            return;
        }

        // ユーザーセッションを作成する
        Session.create(req, code);

        CallbackBuilder callback = new CallbackBuilder(req);
        if (callback.hasValue()) {
            resp.sendRedirect(callback.build());
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/domain2/rp/top");
        return;
    }

}
