package com.gzy.imapplication.net.core;

import java.util.Map;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class XXURLUtils {
    public static  XXURLUtils shared = new XXURLUtils();

    OkHttpClient client = new OkHttpClient();

    public void post(String url, Map<String,String> parameter, Callback callback) {

        FormBody.Builder paraB = new FormBody.Builder();

        if (parameter != null && parameter.isEmpty() == false){
            for (Map.Entry<String, String> entry : parameter.entrySet()) {
                paraB.add(entry.getKey(),entry.getValue());
            }
        }

        RequestBody para = paraB.build();

        Request.Builder requestB = new Request.Builder();

        requestB.url(url)
                .post(para);

        Request request = requestB.build();

        client.newCall(request).enqueue(callback);
    }

}
