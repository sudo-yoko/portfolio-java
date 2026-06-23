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
 * 同意確認の再試行
 */
@WebServlet("/domain1/idp/retry")
public class RetryServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(RetryServlet.class.getName());
    private static final String LOG_PREFIX = ">>> [IDP]: " + RetryServlet.class.getSimpleName() + ": ";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info(LOG_PREFIX + "doGet start.");

        // ログインクッキーの確認
        String sessionId = DomainCookie.SessionId.validate(req, resp);
        if (sessionId == null) {
            return;
        }
        // セッションの確認
        boolean valid = Session.validate(req, resp);
        if (!valid) {
            return;
        }
        // // セッションが無ければ作成する
        // AppSession.create(req);
        // 同意確認結果をクリアする
        Session.setConsent(req, null);

        resp.sendRedirect(req.getContextPath() + "/domain1/idp/auth");
    }

}
