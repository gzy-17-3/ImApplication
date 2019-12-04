package com.gzy.imapplication.net.core;


import com.alibaba.fastjson.JSON;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;



public abstract class XXNormalCallBack extends XXCallBack {

    @Override
    public void onResponse(final Call call, Response response) throws IOException {
        task = new AsyncToMainTask(this,new AsyncToMainTask.MainCall() {
            @Override
            public void runOnMain() {
                onResponseDoMain(call,response);
            }
        });

        task.execute();
    }

    public abstract void onResponseDoMain(final Call call, Response response);

}
