package com.example.application.domain1.idp;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import com.example.application.CallbackBuilder;
import com.example.application.ClientIdBuilder;
import com.example.application.UrlBuilder;
import com.example.application.domain1.DomainCookie;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Idpアプリ 同意確認ページ
 */
@WebServlet("/domain1/idp/consent")
public class ConsentServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(ConsentServlet.class.getName());
    private static final String LOG_PREFIX = ">>> [IDP]: " + ConsentServlet.class.getSimpleName() + ": ";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info(LOG_PREFIX
                + String.format("doGet start. uri->%s, query->%s", req.getRequestURI(), req.getQueryString()));

        // ログインクッキーの確認
        String sessionId = DomainCookie.SessionId.validate(req, resp);
        if (sessionId == null) {
            return;
        }
        // セッションの確認
        boolean valid = IdpSessionManager.validate(req, resp);
        if (!valid) {
            return;
        }

        // クライアントID
        ClientIdBuilder clientId = new ClientIdBuilder(req);
        // 同意確認URL
        UrlBuilder consent = new UrlBuilder(req.getContextPath() + "/domain1/idp/consent");
        if (clientId.hasValue()) {
            consent.appendQueryString(clientId.buildQueryString());
        }
        CallbackBuilder callback = new CallbackBuilder(req);
        if (callback.hasValue()) {
            // 同意後に遷移するURL
            consent.appendQueryString(callback.buildQueryString());
        }

        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head><title>同意確認</title></head>");
            out.println("<body style='background-color: #FFFFE0;'>");
            out.println("<h2>同意確認</h2>");
            out.println("<p>" + clientId.getValue() + "にログインします。</p>");
            out.println("<form action='" + consent.build() + "' method='POST'>");
            out.println("<button type='submit' name='consent' value='approve'>同意する</button>");
            out.println("<button type='submit' name='consent' value='deny'>同意しない</button>");
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info(LOG_PREFIX
                + String.format("doPost start. uri->%s, query->%s", req.getRequestURI(), req.getQueryString()));

        ClientIdBuilder clientId = new ClientIdBuilder(req).require();

        String consent = req.getParameter("consent");
        if ("approve".equals(consent)) {
            IdpSessionManager.setConsent(req, clientId.getValue(), true);
        } else if ("deny".equals(consent)) {
            IdpSessionManager.setConsent(req, clientId.getValue(), false);
        } else {

        }
        // String redirect = req.getContextPath() + "/domain1/idp/auth";
        UrlBuilder redirect = new UrlBuilder(req.getContextPath() + "/domain1/idp/auth");

        redirect.appendQueryString(clientId.buildQueryString());

        // CallbackUrl callback = new CallbackUrl(req);
        CallbackBuilder callback = new CallbackBuilder(req);
        if (callback.hasValue()) {
            // redirect += "?" + callback.toQueryString();
            redirect.appendQueryString(callback.buildQueryString());
        }

        resp.sendRedirect(redirect.build());
    }

}
