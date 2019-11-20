package com.gzy.imapplication.net.core;


import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;



public abstract class XXModelCallback<T> extends XXCallBack {

    private Class<T> modelClass;

    public XXModelCallback(Class<T> modelClass) {
        this.modelClass = modelClass;
    }

    @Override
    public void onResponse(final Call call, Response response) throws IOException {

        if (!response.isSuccessful()){
            this.onFailure(call,new XXJSONException("操作失败."));
            return;
        }

        String json = response.body().string();
        if (json == null || json.length() == 0){
            this.onFailure(call,new XXJSONException("返回数据为空."));
            return;
        }

        final T t = JSON.parseObject(json, this.modelClass);

        task = new AsyncToMainTask(this,new AsyncToMainTask.MainCall() {
            @Override
            public void runOnMain() {
                onResponseData(call,t);
            }
        });

        task.execute();
    }

    public abstract void onResponseData(Call call, T model);

}
