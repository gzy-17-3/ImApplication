package com.gzy.imapplication.module.contacts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gzy.imapplication.R;
import com.gzy.imapplication.core.Auth;
import com.gzy.imapplication.model.Account;
import com.gzy.imapplication.module.base.BaseActivity;
import com.gzy.imapplication.net.ContactsApi;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AddFriendRequestActivity extends BaseActivity {

    private ContactsAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend_request);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        
        recyclerView = findViewById(R.id.recyclerView);

        swipeRefreshLayout.setOnRefreshListener(()->{
            loadData();
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ContactsAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.post(()->{
            loadData();
        });

    }

    void loadData(){
        KProgressHUD hud = KProgressHUD.create(getContext())
                .show();

        String token = Auth.loadToken(this).getToken();
        ContactsApi.loadAddFriendRequest(token, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(()->{
                    hud.dismiss();
                    Toast.makeText(getContext(), "加载失败："+e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()){
                    runOnUiThread(()->{
                        hud.dismiss();
                        Toast.makeText(getContext(), "加载失败~", Toast.LENGTH_SHORT).show();
                    });
                    return;
                }
                String jsonString = response.body().string();
                List<Account> accounts = JSON.parseArray(jsonString, Account.class);
                runOnUiThread(()->{
                    hud.dismiss();
                    adapter.replaceData(accounts);
                });
            }
        });
    }
}
