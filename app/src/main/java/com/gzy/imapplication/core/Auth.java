package com.gzy.imapplication.core;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.gzy.imapplication.model.Token;
import com.gzy.imapplication.module.main.SplashActivity;
import com.gzy.imapplication.utils.SharedPreferencesUtils;

public class Auth {

    /**
     * 判断是否已经登陆
     * @param context
     * @return
     */
    public static boolean isLogin(Context context) {
        Token token = loadToken(context);
        if (token == null){
            return false;
        }
        String tokenStr = token.getToken();
        return !TextUtils.isEmpty(tokenStr);
    }
    /**
     * 加载 token 对象
     */
    public static Token loadToken(Context context){
        // 1 读取 json 字符串
        String tokenjsonstr = SharedPreferencesUtils.getValue(context,"tokenjsonstr");

        // 转成对象
        return JSON.parseObject(tokenjsonstr, Token.class);
    }

    /**
     * 保存到本地 token 对象， 登陆成功之后设置
     */
    public static void saveToken(Context context,Token token){

        String jsonString = JSON.toJSONString(token);
        SharedPreferencesUtils.setValue(context,"tokenjsonstr",jsonString);

    }

    /**
     * 注销
     */
    public static void clearToken(Context context){
        SharedPreferencesUtils.setValue(context,"tokenjsonstr","");
    }
}
