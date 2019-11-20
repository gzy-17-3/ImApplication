package com.gzy.imapplication.net;

import com.gzy.imapplication.model.Account;
import com.gzy.imapplication.model.Token;
import com.gzy.imapplication.net.core.XXModelCallback;
import com.gzy.imapplication.net.core.XXURLUtils;

import java.util.HashMap;
import java.util.Map;

public class AuthApi {

    public static void login(String phoneText, String password, XXModelCallback<Token> callback) {

    }

    public static void signin(String phoneText, String password, XXModelCallback<Token> callback) {
        Map<String, String> para = new HashMap<>();

        para.put("phone",phoneText);
        para.put("password",password);

        XXURLUtils.shared.post(URLSet.Auth.Signin, para, callback);
    }
}
