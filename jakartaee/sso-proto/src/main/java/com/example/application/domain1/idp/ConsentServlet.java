package com.example.application.domain1.idp;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import com.example.application.CallbackUrl;
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
        boolean valid = Session.validate(req, resp);
        if (!valid) {
            return;
        }

        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head><title>同意確認</title></head>");
            out.println("<body style='background-color: #FFFFE0;'>");
            out.println("<h2>同意確認</h2>");
            String consent = req.getContextPath() + "/domain1/idp/consent";
            CallbackUrl callback = new CallbackUrl(req);
            if (callback.hasValue()) {
                consent += "?" + callback.toQueryString();
            }
            out.println("<form action='" + consent + "' method='POST'>");
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

        String consent = req.getParameter("consent");
        if ("approve".equals(consent)) {
            Session.setConsent(req, true);
        } else if ("deny".equals(consent)) {
            Session.setConsent(req, false);
        } else {

        }
        String redirect = req.getContextPath() + "/domain1/idp/auth";

        CallbackUrl callback = new CallbackUrl(req);
        if (callback.hasValue()) {
            redirect += "?" + callback.toQueryString();
        }
        resp.sendRedirect(redirect);
    }

}
