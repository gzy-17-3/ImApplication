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
import com.gzy.imapplication.model.Account;
import com.gzy.imapplication.model.Token;
import com.gzy.imapplication.module.base.BaseActivity;
import com.gzy.imapplication.module.home.HomeActivity;
import com.gzy.imapplication.net.AuthApi;
import com.gzy.imapplication.net.core.XXModelCallback;

import java.io.IOException;

import okhttp3.Call;

public class SigninActivity extends BaseActivity {

    EditText et_phone;
    EditText et_password;
    EditText et_password2;

    private IntentFilter intentFilter;
    private LoginSucceedBroadcast loginSucceedBroadcast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);


        et_phone = findViewById(R.id.et_phone);
        et_password = findViewById(R.id.et_password);
        et_password2 = findViewById(R.id.et_password2);

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
        // 执行登陆

        String phoneText = et_phone.getText().toString();

        if (TextUtils.isEmpty(phoneText)){
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }

        String password = et_password.getText().toString();
        if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }

        String password2 = et_password2.getText().toString();

        if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "请输入重复输入的密码", Toast.LENGTH_SHORT).show();
            return;
        }


        if (!password.equals(password2)){
            Toast.makeText(this, "请输入一致的密码", Toast.LENGTH_SHORT).show();
            return;
        }

        AuthApi.signin(phoneText,password, new XXModelCallback<Token>(Token.class) {

            @Override
            public void onFailure2(Call call, IOException e, ErrType type, String message) {
                Toast.makeText(SigninActivity.this, "" + message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponseData(Call call, Token model) {
                Toast.makeText(SigninActivity.this, "注册成功。。", Toast.LENGTH_SHORT).show();

                Auth.saveToken(SigninActivity.this,model);

                jumpHome();
            }
        });

    }

    private void jumpHome() {
        Intent intent = new Intent(SigninActivity.this, HomeActivity.class);
        startActivity(intent);

        final Intent broadcastIntent = new Intent(LoginSucceedBroadcast.KEY);

        runOnUiThread(100, new Runnable() {
            @Override
            public void run() {
                sendBroadcast(broadcastIntent);
            }
        });
    }
}
