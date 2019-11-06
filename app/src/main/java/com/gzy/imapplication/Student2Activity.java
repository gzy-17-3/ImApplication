package com.gzy.imapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
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
import com.chad.library.adapter.base.BaseViewHolder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Student2Activity extends AppCompatActivity {


    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    private List<Student> dataList = new ArrayList<>();

    QKAdapter qkAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        recyclerView = findViewById(R.id.recyclerView);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setHasFixedSize(true);

        qkAdapter = new QKAdapter(dataList);
        recyclerView.setAdapter(qkAdapter);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });

        loadData();
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
                        swipeRefreshLayout.setRefreshing(false);
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


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        qkAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });

                Log.e("TAG", "onResponse: " + students.size() );
            }
        });
    }
}


class QKAdapter extends BaseQuickAdapter<Student, BaseViewHolder>{
    public QKAdapter(@Nullable List<Student> data) {
        super(R.layout.item_student_avatar, data);
    }
    @Override
    protected void convert(@NonNull BaseViewHolder helper, Student item) {

        String genderMsg = "";

        if (item.getGender() == null){
            genderMsg = "~";
        }else if (item.getGender() == 1){
            genderMsg = "女";
        }else if (item.getGender() == 2){
            genderMsg = "男";
        }else{
            genderMsg = "~";
        }

        String otherMsg = "年龄：" + item.getAge() + " 性别：" + genderMsg + " 学号：" +item.getSid();
        helper.setText(R.id.tv_name,item.getName());
        helper.setText(R.id.tv_other,otherMsg);

    }
}

