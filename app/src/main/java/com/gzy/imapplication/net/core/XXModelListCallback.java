package com.gzy.imapplication.net.core;


import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;


public abstract class XXModelListCallback<T> extends XXCallBack {

    private Class<T> modelClass;

    public XXModelListCallback(Class<T> modelClass) {
        this.modelClass = modelClass;
    }

    @Override
    public void onResponse(final Call call, Response response) throws IOException {

        if (!response.isSuccessful()){
            this.onFailure(call,new XXNetException("请求失败."));
            return;
        }

        String json = response.body().string();
        if (json == null || json.length() == 0){
            this.onFailure(call,new XXNetException("返回数据为空."));
            return;
        }

        final List<T> t = JSON.parseArray(json, this.modelClass);

        task = new AsyncToMainTask(this,new AsyncToMainTask.MainCall() {
            @Override
            public void runOnMain() {
                onResponseData(call,t);
            }
        });

        task.execute();
    }

    public abstract void onResponseData(Call call, List<T> modelList);

}
