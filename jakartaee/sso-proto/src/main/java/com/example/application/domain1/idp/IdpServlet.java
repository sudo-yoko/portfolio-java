package com.example.application.domain1.idp;

import java.io.IOException;
import java.io.PrintWriter;
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
        Optional<LoginCookie.Data> cookie = LoginCookie.validate(req, resp);
        if (!cookie.isPresent()) {
            return;
        }

        // セッションが無ければ作成する

        // 同意確認されていない場合は、同意選択ボタンを表示する
        resp.sendRedirect(req.getContextPath() + "/domain1/idp/consent");

        // 同意しない場合、閉じる

        // 同意している場合、遷移する。

        // Optional<AppSession.Data> optSession = AppSession.validate(req, resp);
        // AppSession.Data session = null;
        // if(!optSession.isPresent()){
        // AppSession.create(req);
        // }else{
        // session = optSession.get();
        // }

    }

}
