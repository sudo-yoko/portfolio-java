package com.example.application.domain1.idp;

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

        // CallbackUrl callback = new CallbackUrl(req);
        CallbackBuilder callback = new CallbackBuilder(req);

        // 同意確認
        Boolean consent = Session.getConsent(req);
        // 同意確認されていない
        if (consent == null) {
            // String redirect = req.getContextPath() + "/domain1/idp/consent";
            UrlBuilder redirect = new UrlBuilder(req.getContextPath() + "/domain1/idp/consent");
            if (callback.hasValue()) {
                // redirect += "?" + callback.toQueryString();
                redirect.appendQueryString(callback.toQueryString());
            }
            resp.sendRedirect(redirect.build());
            return;
        }
        // 同意しない
        if (consent == false) {
            // String redirect = req.getContextPath() + "/domain1/idp/cancel";
            UrlBuilder redirect = new UrlBuilder(req.getContextPath() + "/domain1/idp/cancel");
            if (callback.hasValue()) {
                // redirect += "?" + callback.toQueryString();
                redirect.appendQueryString(callback.toQueryString());
            }
            resp.sendRedirect(redirect.build());
            return;
        }
        // 同意する
        String token = "proto-token-456";
        if (callback.hasValue()) {
            // TODO: 値オブジェクトではなく、ビルダーパターンでの実装も検討
            // resp.sendRedirect(callback.appendQueryParam("token=" + token).getValue());
            callback.appendQueryParam("token", token);
            resp.sendRedirect(callback.build());
            return;
        } else {
            // String redirect = req.getContextPath() + "/domain2/rp/auth";
            // redirect += "?token=" + token;
            UrlBuilder redirect = new UrlBuilder(req.getContextPath() + "/domain2/rp/auth");
            redirect.appendQueryParam("token", token);
            resp.sendRedirect(redirect.build());
            return;
        }
    }
}
