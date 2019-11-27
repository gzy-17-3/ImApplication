package com.gzy.imapplication.net.core;

import android.util.Log;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;
import okio.Source;

public class XXUploadFileUtils {

    private static final OkHttpClient okHttpClient = new OkHttpClient();

    public static void uploadFile(String url,File file){
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        FileRequestBody fileRequestBody = new FileRequestBody(requestFile, new FileRequestBody.LoadingListener() {
            @Override
            public void onProgress(long currentLength, long contentLength) {
                //获取上传的比例
                Log.d("Tag---", currentLength + "/" + contentLength);
            }
        });
        //files是与服务器对应的key
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("files", file.getName(), fileRequestBody);
//        RetrofitHelper.userApi().uploadHeadPic(body).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<UploadImgBean>() {
//                    @Override
//                    public void call(UploadImgBean uploadImgBean) {
//                        //上传成功
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        //上传失败
//                    }
//                });
        Request request = new Request.Builder()
                .url(url) //地址
                .post(fileRequestBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("","上传失败" + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("response.body().string() = " + response.body().string());
                Log.e("","上传成功");
            }
        });


    }
}


class FileRequestBody extends RequestBody {
    private RequestBody mRequestBody;
    private LoadingListener mLoadingListener;
    private long mContentLength;

    public FileRequestBody(RequestBody requestBody, LoadingListener loadingListener) {
        mRequestBody = requestBody;
        mLoadingListener = loadingListener;
    }

    //文件的总长度
    @Override
    public long contentLength() {
        try {
            if (mContentLength == 0)
                mContentLength = mRequestBody.contentLength();
            return mContentLength;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public MediaType contentType() {
        return mRequestBody.contentType();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        ByteSink byteSink = new ByteSink(sink);
        BufferedSink mBufferedSink = Okio.buffer(byteSink);
        mRequestBody.writeTo(mBufferedSink);
        mBufferedSink.flush();
    }


    private final class ByteSink extends ForwardingSink {
        //已经上传的长度
        private long mByteLength = 0L;

        ByteSink(Sink delegate) {
            super(delegate);
        }

        @Override
        public void write(Buffer source, long byteCount) throws IOException {
            super.write(source, byteCount);
            mByteLength += byteCount;
            mLoadingListener.onProgress(mByteLength, contentLength());
        }
    }

    public interface LoadingListener {
        void onProgress(long currentLength, long contentLength);
    }
}