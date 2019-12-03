package com.gzy.imapplication.module.auth;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class LogoutSucceedBroadcast extends BroadcastReceiver {

    public static String KEY = "com.gzy.imapplication.module.auth.LogoutSucceedBroadcast";

    public Runnable runnable;

    // 广播 会有很多个
    @Override
    public void onReceive(Context context, Intent intent) {
        if (runnable != null) {
            runnable.run();
        }
    }
}