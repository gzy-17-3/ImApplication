package com.gzy.gzylibrary.core;


import android.os.AsyncTask;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;

public abstract class XXCallBack implements Callback {
    public enum ErrType{
        unknown
    }

    AsyncToMainTask task;

    @Override
    public void onFailure(final Call call, final IOException e) {
        if (e instanceof XXNetException){
            task = new AsyncToMainTask(this, new AsyncToMainTask.MainCall() {
                @Override
                public void runOnMain() {
                    onFailure2(call, e, ErrType.unknown, e.getMessage());
                }
            });
            task.execute();
        }else{
            task = new AsyncToMainTask(this,new AsyncToMainTask.MainCall() {
                @Override
                public void runOnMain() {
                    onFailure2(call,e,ErrType.unknown,"您的网络不给力");
                }
            });
            task.execute();
        }
    }

    public abstract void onFailure2(Call call, IOException e,ErrType type,String message);

    public static class  AsyncToMainTask extends AsyncTask {

        public XXCallBack callBack;
        MainCall mainCall;

        interface MainCall{
            void runOnMain();
        }

        public AsyncToMainTask(XXCallBack callBack, MainCall mainCall) {
            this.callBack = callBack;
            this.mainCall = mainCall;
        }

        @Override
        protected Object doInBackground(Object[] params) {
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            mainCall.runOnMain();
            callBack = null;
        }
    }
}
