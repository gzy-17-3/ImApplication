package com.gzy.imapplication;

import android.app.Application;
import android.content.Intent;
import android.widget.Toast;

import com.gzy.imapplication.core.Auth;
import com.gzy.imapplication.net.core.XXURLUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class AApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        XXURLUtils.shared.client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Response response = chain.proceed(chain.request());

                        if ((response.code() == 403)) {
                            Auth.clearToken(AApplication.this);
//                            startActivity(new Intent("jump.login"));
                            Toast.makeText(AApplication.this, "该账号已登出，请重新登录", Toast.LENGTH_SHORT).show();
                        }

                        return response;
                    }
                })
                .build();
    }
}
