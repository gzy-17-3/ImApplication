package com.gzy.imapplication.module.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.gzy.imapplication.R;
import com.gzy.imapplication.core.Auth;
import com.gzy.imapplication.model.Token;
import com.gzy.imapplication.module.base.BaseActivity;
import com.gzy.imapplication.utils.SharedPreferencesUtils;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        // 等待  1. 2.   1
        runOnUiThread(1000, new Runnable() {
            @Override
            public void run() {
                //
                jump();
            }
        });
    }

    private void jump() {
        // 判断是否已经登陆
        // 读取本地的数据  如果有登陆了之后的 token 则 为已经登录过

        // 判断 token 是否为空 如果 为空
        if (Auth.isLogin(this)){
            //  如果已经登陆 跳主界面
            Toast.makeText(this, "已登录", Toast.LENGTH_SHORT).show();
        }else{
            //  未登录 否则跳转到 登陆界面
            Toast.makeText(this, "未登录", Toast.LENGTH_SHORT).show();
        }








    }

}
