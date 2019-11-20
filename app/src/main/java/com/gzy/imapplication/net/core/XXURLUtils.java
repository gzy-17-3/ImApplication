package com.gzy.imapplication.net.core;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class XXURLUtils {
    public static XXURLUtils shared = new XXURLUtils();

    public OkHttpClient client = new OkHttpClient();

    private MediaType MediaTypeJSON = MediaType.parse("application/json");

    // post //////////////////////////////////////////

    /**
     * @param url       请求的链接
     * @param header    请求头
     * @param parameter 请求体
     * @param callback  回调
     */
    public void post(String url, Map<String, String> header, Map<String, String> parameter, Callback callback) {

        RequestBody para = RequestBody.create(MediaTypeJSON, JSON.toJSONString(parameter));

        Request.Builder reqBuild = new Request.Builder();

        reqBuild.url(url)
                .post(para);
        if (header != null) {
            for (Map.Entry<String, String> entry : header.entrySet()) {
                reqBuild.addHeader(entry.getKey(), entry.getValue());
            }
        }

        Request request = reqBuild.build();

        client.newCall(request).enqueue(callback);
    }

    /**
     * @param url       请求的链接
     * @param parameter 请求体
     * @param callback  回调
     */
    public void post(String url, Map<String, String> parameter, Callback callback) {
        post(url, new HashMap<String, String>(), parameter, callback);
    }


    // get //////////////////////////////////////////


    /**
     * get 请求
     *
     * @param url      url
     * @param header   请求头参数
     * @param query    参数
     * @param callback 回调
     */
    public void get(String url, Map<String, String> header, Map<String, String> query, Callback callback) throws Exception {

        Request.Builder reqBuild = new Request.Builder();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(url)
                .newBuilder();
        if (query != null) {
            for (Map.Entry<String, String> entry : query.entrySet()) {
                urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
            }
        }
        if (header != null) {
            for (Map.Entry<String, String> entry : header.entrySet()) {
                reqBuild.addHeader(entry.getKey(), entry.getValue());
            }
        }

        reqBuild.url(urlBuilder.build());
        Request request = reqBuild.build();

        client.newCall(request).enqueue(callback);
    }

    /**
     * get 请求
     *
     * @param url      url
     * @param query    参数
     * @param callback 回调
     */
    public void get(String url, Map<String, String> query, Callback callback) throws Exception {
        get(url, new HashMap<String, String>(), query, callback);
    }


    // put //////////////////////////////////////////

    /**
     * @param url       请求的链接
     * @param header    请求头
     * @param parameter 请求体
     * @param callback  回调
     */
    public void put(String url, Map<String, String> header, Map<String, String> parameter, Callback callback) {

        RequestBody para = RequestBody.create(MediaTypeJSON, JSON.toJSONString(parameter));

        Request.Builder reqBuild = new Request.Builder();

        reqBuild.url(url)
                .put(para);
        if (header != null) {
            for (Map.Entry<String, String> entry : header.entrySet()) {
                reqBuild.addHeader(entry.getKey(), entry.getValue());
            }
        }

        Request request = reqBuild.build();

        client.newCall(request).enqueue(callback);
    }

    /**
     * @param url       请求的链接
     * @param parameter 请求体
     * @param callback  回调
     */
    public void put(String url, Map<String, String> parameter, Callback callback) {
        put(url, new HashMap<String, String>(), parameter, callback);
    }


    // delete //////////////////////////////////////////

    /**
     * get 请求
     *
     * @param url      url
     * @param header   请求头参数
     * @param query    参数
     * @param callback 回调
     */
    public void delete(String url, Map<String, String> header, Map<String, String> query, Callback callback) throws Exception {

        Request.Builder reqBuild = new Request.Builder();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(url)
                .newBuilder();

        if (query != null) {
            for (Map.Entry<String, String> entry : query.entrySet()) {
                urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
            }
        }

        if (header != null) {
            for (Map.Entry<String, String> entry : header.entrySet()) {
                reqBuild.addHeader(entry.getKey(), entry.getValue());
            }
        }
        reqBuild.url(urlBuilder.build());
        reqBuild.delete();

        Request request = reqBuild.build();

        client.newCall(request).enqueue(callback);
    }

    /**
     * get 请求
     *
     * @param url      url
     * @param query    参数
     * @param callback 回调
     */
    public void delete(String url, Map<String, String> query, Callback callback) throws Exception {
        delete(url, new HashMap<String, String>(), query, callback);
    }

}
