package com.gzy.imapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class StudentListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    private List<Student> dataList = new ArrayList<>();
    private StudentListAdapter adapter;

    int currentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        recyclerView = findViewById(R.id.recyclerView);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);



        adapter = new StudentListAdapter(dataList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);



        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });

        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore(currentPage+1);
            }
        },recyclerView);

        loadData();
    }

    // 翻页
    // 1. 加载失败
    // 2. 加载成功
    //      1. 没有数据了 0
    //      2. 有数据  不是最后一页
    private void loadMore(int page) {
        //异步请求
        OkHttpClient okHttpClient=new OkHttpClient();
        final Request request=new Request.Builder()
                .url("http://crudtest.rdxer.com/student?page="+page)
                .get()
                .build();
        final Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("okhttp_error",e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.loadMoreFail();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String json = response.body().string();
                JSONObject jsonObject = JSON.parseObject(json);
                JSONArray jsonArray = jsonObject.getJSONArray("content");

                final List<Student> studentList = new ArrayList<>();

                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject r = jsonArray.getJSONObject(i);
                    Student student = r.toJavaObject(Student.class);
                    studentList.add(student);
                }

                // 数据有了
                // 如果没有数据  则  .size() = 0

                dataList.addAll(studentList);

                currentPage += 1;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (studentList.size() == 0) {
                            adapter.loadMoreEnd();
                        }else{
                            adapter.loadMoreComplete();
                        }

                    }
                });

                Log.w("TAG", "onResponse: " + studentList.size());

            }
        });
    }

    private void loadData() {

        //异步请求
        OkHttpClient okHttpClient=new OkHttpClient();
        final Request request=new Request.Builder()
                .url("http://crudtest.rdxer.com/student")
                .get()
                .build();
        final Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("okhttp_error",e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String json = response.body().string();
                JSONObject jsonObject = JSON.parseObject(json);
                JSONArray jsonArray = jsonObject.getJSONArray("content");

                List<Student> studentList = new ArrayList<>();

                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject r = jsonArray.getJSONObject(i);
                    Student student = r.toJavaObject(Student.class);
                    studentList.add(student);
                }

                // 数据有了
                // 如果没有数据  则  .size() = 0

                dataList.clear();
                dataList.addAll(studentList);


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        currentPage = 0;
                        adapter.setNewData(dataList);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });

                Log.w("TAG", "onResponse: " + studentList.size());

            }
        });


    }


}
