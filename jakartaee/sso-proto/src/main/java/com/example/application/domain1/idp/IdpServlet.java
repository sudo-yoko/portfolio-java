package com.example.application.domain1.idp;

import java.io.IOException;
import java.util.logging.Logger;

import com.example.application.CallbackBuilder;
import com.example.application.ClientId;
import com.example.application.UrlBuilder;
import com.example.application.domain1.DomainCookie;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 認可エンドポイント
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
        IdpSessionManager.create(req);

        CallbackBuilder callback = new CallbackBuilder(req);
        ClientId clientId = new ClientId(req);

        // 同意確認
        Boolean consent = IdpSessionManager.getConsent(req, clientId.getValue());
        // 同意確認されていない
        if (consent == null) {
            UrlBuilder redirect = new UrlBuilder(req.getContextPath() + "/domain1/idp/consent");
            redirect.appendQueryString(clientId.toQueryString());
            if (callback.hasValue()) {
                redirect.appendQueryString(callback.buildQueryString());
            }
            resp.sendRedirect(redirect.build());
            return;
        }
        // 同意しない
        if (consent == false) {
            UrlBuilder redirect = new UrlBuilder(req.getContextPath() + "/domain1/idp/cancel");
            if (clientId.hasValue()) {
                redirect.appendQueryString(clientId.toQueryString());
            }
            if (callback.hasValue()) {
                redirect.appendQueryString(callback.buildQueryString());
            }
            resp.sendRedirect(redirect.build());
            return;
        }
        // 同意する
        String code = "proto-code-123"; // 認可コード
        if (callback.hasValue()) {
            callback.appendQueryParam("code", code);
            resp.sendRedirect(callback.build());
            return;
        } else {
            // UrlBuilder redirect = new UrlBuilder(req.getContextPath() +
            // "/domain2/rp/auth");
            // redirect.appendQueryParam("code", code);
            // resp.sendRedirect(redirect.build());
            // return;
            UrlBuilder error = new UrlBuilder(req.getContextPath() + "/domain2/idp/error");
            resp.sendRedirect(error.build());
            return;
        }
    }
}
