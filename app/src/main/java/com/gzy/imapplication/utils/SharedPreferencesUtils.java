package com.gzy.imapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;


public class SharedPreferencesUtils {
    public static String getValue(Context context, String key){
        SharedPreferences appdata = context.getSharedPreferences("appdata", Context.MODE_PRIVATE);
        return appdata.getString(key,null);
    }

    public static void setValue(Context context,String key, String value) {
        SharedPreferences appdata = context.getSharedPreferences("appdata", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = appdata.edit();
        edit.putString(key, value);
        edit.commit();
    }
}
