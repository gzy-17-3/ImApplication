package com.gzy.imapplication.module.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.gzy.imapplication.R;
import com.gzy.imapplication.core.Auth;
import com.gzy.imapplication.module.auth.LoginActivity;
import com.gzy.imapplication.module.base.BaseActivity;

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void onClickLogout(View view) {
        Auth.clearToken(this);

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

        runOnBackgroundThread(100, new Runnable() {
            @Override
            public void run() {
                finish();
            }
        });
    }
}
