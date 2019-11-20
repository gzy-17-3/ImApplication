package com.gzy.imapplication.module.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

import com.gzy.imapplication.R;
import com.gzy.imapplication.module.base.BaseActivity;

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
}
