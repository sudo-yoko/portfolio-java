package com.example.application.domain1.idp;

import java.io.IOException;
import java.util.logging.Logger;

import com.example.application.domain1.DomainCookie;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Identity Provider 認証処理
 */
@WebServlet("/domain1/idp/auth")
public class IdpServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(IdpServlet.class.getName());
    private static final String LOG_PREFIX = ">>> [IDP]: " + IdpServlet.class.getSimpleName() + ": ";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info(LOG_PREFIX + "doGet start.");

        // ログインクッキーの確認
        String sessionId = DomainCookie.SessionId.validate(req, resp);
        if (sessionId == null) {
            return;
        }
        // セッションが無ければ作成する
        Session.create(req);

        // 同意確認されていない
        Boolean consent = Session.getConsent(req);
        if (consent == null) {
            resp.sendRedirect(req.getContextPath() + "/domain1/idp/consent");
            return;
        }
        // 同意していない
        if (consent == false) {
            resp.sendRedirect(req.getContextPath() + "/domain1/idp/cancel");
            return;
        }
        // 同意している
        resp.sendRedirect(req.getContextPath() + "/domain2/client/top");
    }
}
