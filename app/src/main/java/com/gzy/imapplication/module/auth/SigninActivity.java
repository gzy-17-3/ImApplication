package com.gzy.imapplication.module.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.gzy.imapplication.R;
import com.gzy.imapplication.model.Account;
import com.gzy.imapplication.net.AuthApi;
import com.gzy.imapplication.net.core.XXModelCallback;

import java.io.IOException;

import okhttp3.Call;

public class SigninActivity extends AppCompatActivity {

    EditText et_phone;
    EditText et_password;
    EditText et_password2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);


        et_phone = findViewById(R.id.et_phone);
        et_password = findViewById(R.id.et_password);
        et_password2 = findViewById(R.id.et_password2);

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

        AuthApi.signin(phoneText,password, new XXModelCallback<Account>(Account.class) {

            @Override
            public void onFailure2(Call call, IOException e, ErrType type, String message) {
                Toast.makeText(SigninActivity.this, "" + message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponseData(Call call, Account model) {
                Toast.makeText(SigninActivity.this, "注册成功。。", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
