package com.example.application.domain1.idp;

import java.io.IOException;
import java.util.logging.Logger;

import com.example.application.SsoUtil;
import com.example.application.domain1.DomainCookie;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Idpアプリ 認証処理
 */
@WebServlet("/domain1/idp/auth")
public class IdpServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(IdpServlet.class.getName());
    private static final String LOG_PREFIX = ">>> [IDP]: " + IdpServlet.class.getSimpleName() + ": ";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info(LOG_PREFIX
                + String.format("doGet start. uri->%s, query->%s", req.getRequestURI(), req.getQueryString()));

        // ログインクッキーの確認
        String sessionId = DomainCookie.SessionId.validate(req, resp);
        if (sessionId == null) {
            return;
        }
        // セッションが無ければ作成する
        Session.create(req);

        String callback = req.getParameter("callback");

        // 同意確認
        Boolean consent = Session.getConsent(req);
        // 同意確認されていない
        if (consent == null) {
            String redirect = req.getContextPath() + "/domain1/idp/consent";
            if (callback != null && !callback.isBlank()) {
                redirect += "?callback=" + callback;
            }
            resp.sendRedirect(redirect);
            return;
        }
        // 同意しない
        if (consent == false) {
            String redirect = req.getContextPath() + "/domain1/idp/cancel";
            if (callback != null && !callback.isBlank()) {
                redirect += "?callback=" + callback;
            }
            resp.sendRedirect(redirect);
            return;
        }
        // 同意する
        String token = "proto-token-456";
        if (callback != null && !callback.isBlank()) {
            callback = SsoUtil.appendQueryParam(callback, "token=" + token);
            resp.sendRedirect(callback);
            return;
        } else {
            String redirect = req.getContextPath() + "/domain2/client/top";
            redirect += "?token=" + token;
            resp.sendRedirect(redirect);
            return;
        }
    }
}
