package com.gzy.imapplication.module.message;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gzy.imapplication.R;
import com.gzy.imapplication.core.Auth;
import com.gzy.imapplication.model.Account;
import com.gzy.imapplication.model.ChatSession;
import com.gzy.imapplication.module.base.BaseFragment;
import com.gzy.imapplication.net.ContactsApi;
import com.gzy.imapplication.net.MessageApi;
import com.gzy.imapplication.net.core.XXModelListCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;

import static com.gzy.imapplication.module.message.ChatActivity.KEY_session_id;
import static com.gzy.imapplication.module.message.ChatActivity.KEY_title;
import static com.gzy.imapplication.module.message.ChatActivity.KEY_to_id;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends BaseFragment {


    private MessageAdapter adapter;

    public MessageFragment() {
        // Required empty public constructor
    }

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        recyclerView = view.findViewById(R.id.recyclerView);

        swipeRefreshLayout.setOnRefreshListener(()->{
            loadData();
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MessageAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.post(()->{

            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Log.e("TAG", "定时器：" );
                    runOnUiThread(()->{
                        loadData();
                    });
                }
            },0,2000);
        });

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle para = new Bundle();
                ChatSession item = (ChatSession) adapter.getItem(position);

                para.putLong(KEY_session_id,item.getId());
                para.putLong(KEY_to_id,item.getAccount().getId());
                para.putString(KEY_title,item.getAccount().getName());

                MessageFragment.this.startActivity(ChatActivity.class, para);
            }
        });

    }

    Timer timer;
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void loadData() {
        String token = Auth.getTokenValue(getContext());
        if (token == null){
            timer.cancel();
            timer = null;
            return;
        }
        MessageApi.loadSession(token, new XXModelListCallback<ChatSession>(ChatSession.class) {
            @Override
            public void onFailure2(Call call, IOException e, ErrType type, String message) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getContext(), ""+message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponseData(Call call, List<ChatSession> modelList) {
                swipeRefreshLayout.setRefreshing(false);
                adapter.setNewData(modelList);
            }
        });
    }
}
