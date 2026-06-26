package com.example.application.domain2.rp;

import java.io.IOException;
import java.util.logging.Logger;

import com.example.application.CallbackBuilder;
import com.example.application.UrlBuilder;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * RPアプリ ユーザーセッション
 */
public class Session {
    private static final Logger logger = Logger.getLogger(Session.class.getName());
    private static final String LOG_PREFIX = ">>> [RP]: " + Session.class.getSimpleName() + ": ";

    private static final String TOKEN = "token";

    /**
     * ユーザーセッションを作成する
     */
    protected static void create(HttpServletRequest req, String token) {
        HttpSession session = req.getSession(true);
        session.setAttribute(TOKEN, token);
    }

    /**
     * ユーザーセッションを破棄する
     */
    protected static void invalidate(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            logger.info(LOG_PREFIX + "session invalidated.");
            session.invalidate();
        }
    }

    /**
     * ユーザーセッションを検証する
     */
    protected static String validate(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute(TOKEN) == null) {
            logger.severe(LOG_PREFIX + "session invalid.");
            String clientId = "appB";

            // String authorization = req.getContextPath() + "/domain1/idp/auth";
            // String redirect = authorization + "?clientId=" + clientId;
            // CallbackUrl callback = new CallbackUrl(req.getContextPath() +
            // "/domain2/rp/auth");
            // redirect += "&" + callback.toQueryString();
            // resp.sendRedirect(redirect);
            UrlBuilder authorization = new UrlBuilder(req.getContextPath() + "/domain1/idp/auth");
            UrlBuilder redirect = new UrlBuilder(authorization.build());
            redirect.appendQueryParam("clientId", clientId);
            CallbackBuilder callback = new CallbackBuilder(req.getContextPath() + "/domain2/rp/auth");
            redirect.appendQueryString(callback.toQueryString());
            resp.sendRedirect(redirect.build());

            return null;
        }
        return (String) session.getAttribute(TOKEN);
    }

}
