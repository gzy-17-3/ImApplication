package com.gzy.imapplication.module.message;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gzy.imapplication.R;
import com.gzy.imapplication.core.Auth;
import com.gzy.imapplication.model.Chat;
import com.gzy.imapplication.model.Token;
import com.gzy.imapplication.module.base.BaseActivity;
import com.gzy.imapplication.net.MessageApi;
import com.gzy.imapplication.net.URLSet;
import com.gzy.imapplication.net.core.XXModelCallback;
import com.gzy.imapplication.net.core.XXModelListCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;

public class ChatActivity extends BaseActivity {

    public static final String KEY_session_id = "session_id";
    public static final String KEY_title = "title";
    public static final String KEY_to_id = "to_id";

    Long session_id;
    Long to_id;
    String title;
    private TextView nav_title;
    private EditText edt_sendtext;

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    private ChatAdapter adapter;
    private RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        session_id = getIntent().getLongExtra(KEY_session_id,0);
        to_id = getIntent().getLongExtra(KEY_to_id,0);

        title = getIntent().getStringExtra(KEY_title);

        if (session_id == 0 || to_id == 0){
            Toast.makeText(this, "数据出错，请重新打开app", Toast.LENGTH_SHORT).show();
            return;
        }

        init();
    }


    Long lastChatid;

    private void init() {
        nav_title = findViewById(R.id.nav_title);
        edt_sendtext = findViewById(R.id.edt_sendtext);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        recyclerView = findViewById(R.id.recyclerView);

        swipeRefreshLayout.setOnRefreshListener(()->{
            loadData(0L);


        });

        recyclerView.setLayoutManager(layoutManager);
        adapter = new ChatAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.post(()->{
            loadData(0L);

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Log.e("TAG", "定时器：" );
                    runOnUiThread(()->{
                        loadMore(lastChatid);
                    });
                }
            },1000,2000);
        });

        findViewById(R.id.btn_send).setOnClickListener((v)->{
            sendText();
        });

        refreshUI();
    }

    private void sendText() {
        String trim = edt_sendtext.getText().toString().trim();
        edt_sendtext.setText("");

        if (TextUtils.isEmpty(trim)){
            return;
        }

        MessageApi.send(Auth.getTokenValue(getContext()), session_id + "", to_id + "", trim, new XXModelCallback<Chat>(Chat.class) {
            @Override
            public void onFailure2(Call call, IOException e, ErrType type, String message) {
                Toast.makeText(ChatActivity.this, "发送失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponseData(Call call, Chat model) {
//                loadMore(lastChatid);
            }
        });

    }

    private void loadData(Long lastChatid) {
        Token token = Auth.loadToken(getContext());
        MessageApi.loadChatList(token.getToken(), session_id + "", lastChatid + "", new XXModelListCallback<Chat>(Chat.class) {
            @Override
            public void onFailure2(Call call, IOException e, ErrType type, String message) {
                Toast.makeText(ChatActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onResponseData(Call call, List<Chat> modelList) {

                for (Chat chat : modelList) {
                    if (token.getUserid() == chat.getFromaid()){
                        chat.setItemType(Chat.Me);
                    }else{
                        chat.setItemType(Chat.Other);
                    }
                }

                swipeRefreshLayout.setRefreshing(false);
                adapter.setNewData(modelList);
                if (modelList.size() > 0){
                    recyclerView.scrollToPosition(modelList.size() - 1);
                    ChatActivity.this.lastChatid = Long.valueOf(modelList.get(modelList.size() - 1).getId());
                }
            }
        });
    }

    private void loadMore(Long lastChatid) {

        Token token = Auth.loadToken(getContext());

        MessageApi.loadChatList(token.getToken(), session_id + "", lastChatid + "", new XXModelListCallback<Chat>(Chat.class) {
            @Override
            public void onFailure2(Call call, IOException e, ErrType type, String message) {
                Toast.makeText(ChatActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponseData(Call call, List<Chat> modelList) {

                for (Chat chat : modelList) {
                    if (token.getUserid() == chat.getFromaid()){
                        chat.setItemType(Chat.Me);
                    }else{
                        chat.setItemType(Chat.Other);
                    }
                }

                adapter.addData(modelList);
                if (modelList.size() > 0){
//                    recyclerView.scrollToPosition(modelList.size() - 1);
                    ChatActivity.this.lastChatid = Long.valueOf(modelList.get(modelList.size() - 1).getId());
                    layoutManager.scrollToPosition(adapter.getItemCount() - 1);
                }
            }
        });
    }

    private void refreshUI(){
        nav_title.setText(title);
    }
}
