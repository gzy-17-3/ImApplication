package com.gzy.imapplication.module.contacts;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gzy.imapplication.R;
import com.gzy.imapplication.core.Auth;
import com.gzy.imapplication.model.AddFriendRequestFullAccount;
import com.gzy.imapplication.module.base.BaseActivity;
import com.gzy.imapplication.net.ContactsApi;
import com.gzy.imapplication.utils.javaExtension.ListEX;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AddFriendRequestActivity extends BaseActivity {

    private AddFriendRequestAdapter adapter;
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
        adapter = new AddFriendRequestAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);


        swipeRefreshLayout.post(()->{
            swipeRefreshLayout.setRefreshing(true);
            loadData();
        });

//        Kotli
//        ListEX.generate(
//                AddFriendRequestFullAccount.OperationEnum.canOperation(), new IEXValueCompose<AddFriendRequestFullAccount.OperationEnum,String>() {
//                    @Override
//                    public String compose(AddFriendRequestFullAccount.OperationEnum obj) {
//                        return null;
//                    }
//                }).toArray();

        List<AddFriendRequestFullAccount.OperationEnum> enumList = AddFriendRequestFullAccount.OperationEnum.canOperation();


        List<String> stringList = ListEX.generate(enumList, (v) -> {
            return v.toString();
        });
//        List<String> stringList = ListEX.generate(enumList, AddFriendRequestFullAccount.OperationEnum::toString);


        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                AddFriendRequestFullAccount account = (AddFriendRequestFullAccount) adapter.getData().get(position);

                if (enumList.contains(account.getOperationEnum())) {
                    return;
                }

                new AlertView.Builder().setContext(getContext())
                        .setStyle(AlertView.Style.ActionSheet)
                        .setTitle("请确认")
                        .setMessage("")
                        .setCancelText("取消")
                        .setOthers(
                            stringList.toArray(new String[]{})
                        )
                        .setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(Object o, int position) {
                                AddFriendRequestFullAccount.OperationEnum operationEnum = enumList.get(position);
                                exec(account,operationEnum);
                            }
                        })
                        .build().show();
            }
        });
    }

    private void exec(AddFriendRequestFullAccount account, AddFriendRequestFullAccount.OperationEnum operationEnum) {
        ContactsApi.reply(Auth.getTokenValue(this), account.getId(), operationEnum, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(()->{
                    Toast.makeText(AddFriendRequestActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                runOnUiThread(()->{
                    if (!response.isSuccessful()) {
                        Toast.makeText(AddFriendRequestActivity.this, "发起请求失败", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Toast.makeText(AddFriendRequestActivity.this, "发起请求成功", Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(true);
                    loadData();
                });
            }
        });
    }

    void loadData(){

        String token = Auth.loadToken(this).getToken();
        ContactsApi.loadAddFriendRequest(token, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(()->{
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getContext(), "加载失败："+e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()){
                    runOnUiThread(()->{
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(getContext(), "加载失败~", Toast.LENGTH_SHORT).show();
                    });
                    return;
                }
                String jsonString = response.body().string();
                List<AddFriendRequestFullAccount> accounts = JSON.parseArray(jsonString, AddFriendRequestFullAccount.class);
                runOnUiThread(()->{
                    swipeRefreshLayout.setRefreshing(false);
                    adapter.replaceData(accounts);
                });
            }
        });
    }
}
