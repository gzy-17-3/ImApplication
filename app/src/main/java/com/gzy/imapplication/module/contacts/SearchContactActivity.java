package com.gzy.imapplication.module.contacts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.gzy.imapplication.R;
import com.gzy.imapplication.core.Auth;
import com.gzy.imapplication.model.Account;
import com.gzy.imapplication.model.Token;
import com.gzy.imapplication.module.base.BaseActivity;
import com.gzy.imapplication.module.mine.MineFragment;
import com.gzy.imapplication.net.ContactsApi;
import com.gzy.imapplication.net.MineApi;
import com.gzy.imapplication.net.core.XXModelCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SearchContactActivity extends BaseActivity {

    public static final String para_keyword_key = "keyword";

    TextView tv_title;

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;

    SearchContactAdapter adapter;
    String keyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_contact);


        // 初始化 View
        tv_title = findViewById(R.id.tv_title);
        recyclerView = findViewById(R.id.recyclerView);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        swipeRefreshLayout.setOnRefreshListener(()->{
            loadData(keyword);
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new SearchContactAdapter(new ArrayList<>());

        recyclerView.setAdapter(adapter);

        // 获取参数
        Intent intent = getIntent();

        keyword = intent.getStringExtra(para_keyword_key);

        // 设置 参数到界面
        tv_title.setText(String.format("搜索结果（%s）", keyword));

        // 发起请求 获取搜索结果
        swipeRefreshLayout.post(()->{
            loadData(keyword);
        });
    }

    private void loadData(String keyword) {
        Token token1 = Auth.loadToken(this.getContext());

        String token = token1.getToken();

        ContactsApi.find(token, keyword, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(()->{
                    Toast.makeText(SearchContactActivity.this, "获取数据失败："+e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (!response.isSuccessful()) {
                    runOnUiThread(()->{
                        Toast.makeText(SearchContactActivity.this, "请求失败。", Toast.LENGTH_SHORT).show();
                        swipeRefreshLayout.setRefreshing(false);
                    });
                    return;
                }

                String jsonString = response.body().string();

                List<Account> accounts = JSON.parseArray(jsonString, Account.class);

                runOnUiThread(()->{
                    adapter.replaceData(accounts);
                    swipeRefreshLayout.setRefreshing(false);
                });

            }
        });



//        MineApi.loadMineInfo(userid, token, new XXModelCallback<Account>(Account.class) {
//
//            @Override
//            public void onResponseData(Call call, Account model) {
//                MineFragment.this.account = model;
//                refreshUI(model);
//            }
//
//            @Override
//            public void onFailure2(Call call, IOException e, ErrType type, String message) {
//
//                Toast.makeText(getContext(), ""+message, Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}
