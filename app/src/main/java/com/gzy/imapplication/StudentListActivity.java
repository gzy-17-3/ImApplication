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

        // 设置布局管理器
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // 设置 适配器(adapter)
        recyclerView.setAdapter(adapter);


        // 设置监听 下拉刷新事件
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });

        // 设置监听 上拉加载更多的事件
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                // 加载下一页数据
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
                        // 加载更多失败
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


                // 拼接数据到原有数据后方
                dataList.addAll(studentList);

                // 加载成功，当前页码 + 1
                currentPage += 1;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (studentList.size() == 0) {
                            // 没有更多数据了，
                            adapter.loadMoreEnd();
                        }else{
                            // 加载数据成功
                            // 应该还有更多的数据
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
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);    // 加载失败也需要 结束下拉刷新空间的加载状态
                    }
                });
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

                dataList.clear();           //  清空数据，避免重复拼接
                dataList.addAll(studentList);   // 拼接从网络加载的数据

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        currentPage = 0;    // 重置页码
                        adapter.setNewData(dataList);   // 重置 adapter 的状态
                        swipeRefreshLayout.setRefreshing(false);    // 结束下拉刷新空间的加载状态
                    }
                });

                Log.w("TAG", "onResponse: " + studentList.size());

            }
        });


    }


}
