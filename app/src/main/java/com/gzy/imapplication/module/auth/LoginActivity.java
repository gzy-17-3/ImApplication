package com.gzy.imapplication.module.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.gzy.imapplication.R;
import com.gzy.imapplication.core.Auth;
import com.gzy.imapplication.model.Token;
import com.gzy.imapplication.module.base.BaseActivity;
import com.gzy.imapplication.module.home.HomeActivity;
import com.gzy.imapplication.net.AuthApi;
import com.gzy.imapplication.net.core.XXModelCallback;

import java.io.IOException;

import okhttp3.Call;

public class LoginActivity extends BaseActivity {


    private IntentFilter intentFilter;
    private LoginSucceedBroadcast loginSucceedBroadcast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        registerEvent();
    }

    private  void  registerEvent() {
        intentFilter = new IntentFilter();
        intentFilter.addAction(LoginSucceedBroadcast.KEY);
        loginSucceedBroadcast = new LoginSucceedBroadcast();

        loginSucceedBroadcast.runnable = new Runnable() {
            @Override
            public void run() {
                finish();
            }
        };

        registerReceiver(loginSucceedBroadcast,intentFilter);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(loginSucceedBroadcast);
        super.onDestroy();
    }

    public void onClickSignin(View view) {
        Intent intent = new Intent(this,SigninActivity.class);
        startActivity(intent);
    }

    public void onClickLogin(View view) {
        EditText et_phone = findViewById(R.id.et_phone);
        EditText et_password = findViewById(R.id.et_password);

        String phone = et_phone.getText().toString();
        String password = et_password.getText().toString();

        if (TextUtils.isEmpty(phone)){
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }


        AuthApi.login(phone, password, new XXModelCallback<Token>(Token.class) {
            @Override
            public void onResponseData(Call call, Token model) {
                Toast.makeText(LoginActivity.this, "登陆成功。。", Toast.LENGTH_SHORT).show();

                Auth.saveToken(LoginActivity.this,model);

                jumpHome();
            }

            @Override
            public void onFailure2(Call call, IOException e, ErrType type, String message) {
                Toast.makeText(LoginActivity.this, "" + message, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void jumpHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);

        final Intent broadcastIntent = new Intent(LoginSucceedBroadcast.KEY);

        runOnBackgroundThread(100, new Runnable() {
            @Override
            public void run() {
                sendBroadcast(broadcastIntent);
            }
        });
    }
}
