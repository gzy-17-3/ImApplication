package com.gzy.imapplication.module.contacts;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.gzy.imapplication.R;
import com.gzy.imapplication.core.Auth;
import com.gzy.imapplication.model.Account;
import com.gzy.imapplication.module.base.BaseFragment;
import com.gzy.imapplication.net.ContactsApi;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsFragment extends BaseFragment {


    private ContactsAdapter adapter;
    private View headerView;

    public ContactsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contacts, container, false);
    }


    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.btn_add).setOnClickListener((v)->{
            jumpAdd();            
        });




        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        recyclerView = view.findViewById(R.id.recyclerView);

        swipeRefreshLayout.setOnRefreshListener(()->{
            loadData();
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ContactsAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);
        headerView = View.inflate(getContext(),R.layout.headview_new_cintact,null);
        adapter.addHeaderView(headerView);

        swipeRefreshLayout.post(()->{
            loadData();
        });

        headerView.setOnClickListener((v)->{
            jumpAddFriendRequest();
        });
    }

    private void jumpAddFriendRequest() {
        startActivity(new Intent(getContext(),AddFriendRequestActivity.class));
    }

    private void loadData() {

    }


    private void jumpAdd() {
        Intent intent = new Intent(this.getContext(), PreAddFriendActivity.class);
        startActivity(intent);
    }
}
