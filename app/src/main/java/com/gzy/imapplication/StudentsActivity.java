package com.gzy.imapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class StudentsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    private StudentAdapter adapter;
    private List<Student> dataList = new ArrayList<>();

    int currentyPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);

        recyclerView = findViewById(R.id.recyclerView);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        // 监听下拉事件
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });

        adapter = new StudentAdapter(this,dataList);

        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.setHasFixedSize(true);

        adapter.onNeedLoadMoreListener = new StudentAdapter.OnNeedLoadMoreListener() {
            @Override
            public void loadMoreData() {
                // 加载更多
                loadMore(currentyPage + 1);
            }
        };

        // 异步
        loadData();
    }

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
                        // 在加载数据结束之后 关闭刷新
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(StudentsActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                Log.d("okhttp_success",json);
                JSONObject jsonObject = JSON.parseObject(json);
                JSONArray content = jsonObject.getJSONArray("content");
                List<Student> students = new ArrayList<>();

                for (int i = 0; i < content.size(); i++) {
                    JSONObject jsonObject1 = content.getJSONObject(i);
                    Student student = jsonObject1.toJavaObject(Student.class);
                    students.add(student);
                }

                dataList.addAll(students);
                currentyPage += 1;
                adapter.isOnNeedLoadMoreListener = false;

                if ((students.size() == 0)) {
                    adapter.isNoMoreData = true;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        // 在加载数据结束之后 关闭刷新
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });

                Log.e("TAG", "onResponse: " + students.size() );

            }
        });
    }


    void loadData(){
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
                        // 在加载数据结束之后 关闭刷新
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(StudentsActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                Log.d("okhttp_success",json);
                JSONObject jsonObject = JSON.parseObject(json);
                JSONArray content = jsonObject.getJSONArray("content");
                List<Student> students = new ArrayList<>();

                for (int i = 0; i < content.size(); i++) {
                    JSONObject jsonObject1 = content.getJSONObject(i);
                    Student student = jsonObject1.toJavaObject(Student.class);
                    students.add(student);
                }

                dataList.clear();
                dataList.addAll(students);
                adapter.isNoMoreData = false;
                currentyPage = 0;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        // 在加载数据结束之后 关闭刷新
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });

                Log.e("TAG", "onResponse: " + students.size() );

            }
        });
    }

    public void onClickloadDataButton(View view) {
        loadData();
    }
}
