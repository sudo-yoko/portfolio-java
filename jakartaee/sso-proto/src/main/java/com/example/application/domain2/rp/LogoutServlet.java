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
 * ログインアプリ ログアウト
 */
@WebServlet("/domain2/rp/logout")
public class LogoutServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(LogoutServlet.class.getName());
    private static final String LOG_PREFIX = ">>> [RP]: " + LogoutServlet.class.getSimpleName() + ": ";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info(LOG_PREFIX
                + String.format("doGet start. uri->%s, query->%s", req.getRequestURI(), req.getQueryString()));

        // ユーザーセッションを破棄
        Session.invalidate(req);

        // CallbackUrl callback = new CallbackUrl(req);
        // if (callback.hasValue()) {
        // resp.sendRedirect(callback.getValue());
        // return;
        // }
        CallbackBuilder callback = new CallbackBuilder(req);
        if (callback.hasValue()) {
            resp.sendRedirect(callback.build());
            return;
        }
    }

    // TODO: オープンリダイレクタ対応

}
