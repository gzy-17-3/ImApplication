package com.gzy.imapplication.net.core;

import com.alibaba.fastjson.JSON;

import java.util.Map;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class XXURLUtils {
    public static  XXURLUtils shared = new XXURLUtils();

    OkHttpClient client = new OkHttpClient();

    public void post(String url, Map<String,String> parameter, Callback callback) {


        RequestBody para = RequestBody.create(MediaType.parse("application/json"), JSON.toJSONString(parameter));

        Request.Builder requestB = new Request.Builder();

        requestB.url(url)
                .post(para);

        Request request = requestB.build();

        client.newCall(request).enqueue(callback);
    }

}
