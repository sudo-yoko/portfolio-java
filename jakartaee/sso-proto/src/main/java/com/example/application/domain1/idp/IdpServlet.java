package com.example.application.domain1.idp;

import java.io.IOException;
import java.util.Optional;

import com.example.application.domain1.LoginCookie;

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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // ログインクッキーの確認
        Optional<String> cookie = LoginCookie.SessionId.validate(req, resp);
        if (!cookie.isPresent()) {
            return;
        }

        // 同意確認されていない、または同意していない場合
        Boolean consent = AppSession.getConsent(req);
        if (consent == null || consent == false) {
            resp.sendRedirect(req.getContextPath() + "/domain1/idp/consent");
            return;
        }

        // 同意している場合、遷移する。
        resp.sendRedirect(req.getContextPath() + "/domain2/client/top");
    }
}
