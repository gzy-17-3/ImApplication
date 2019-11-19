package com.gzy.imapplication.module.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.gzy.imapplication.R;
import com.gzy.imapplication.module.base.BaseActivity;

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }


    public void onClickSignin(View view) {
        Intent intent = new Intent(this,SigninActivity.class);
        startActivity(intent);
    }
}