package com.example.application.domain1.idp;

import java.io.IOException;
import java.util.logging.Logger;

import com.example.application.CallbackBuilder;
import com.example.application.UrlBuilder;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Idpアプリ ログアウト
 */
@WebServlet("/domain1/idp/logout")
public class LogoutServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(LogoutServlet.class.getName());
    private static final String LOG_PREFIX = ">>> [IDP]: " + LogoutServlet.class.getSimpleName() + ": ";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info(LOG_PREFIX
                + String.format("doGet start. uri->%s, query->%s", req.getRequestURI(), req.getQueryString()));

        // ユーザーセッションを破棄
        Session.invalidate(req);

        // CallbackUrl callback = new CallbackUrl(req);
        CallbackBuilder callback = new CallbackBuilder(req);
        if (callback.hasValue()) {
            // String redirect = req.getContextPath() + "/domain2/rp/logout";
            UrlBuilder redirect = new UrlBuilder(req.getContextPath() + "/domain2/rp/logout");
            // redirect += "?" + callback.toQueryString();
            redirect.appendQueryString(callback.toQueryString());
            resp.sendRedirect(redirect.build());
            return;
        }
    }

}
