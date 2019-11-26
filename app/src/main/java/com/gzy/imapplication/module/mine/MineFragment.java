package com.gzy.imapplication.module.mine;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gzy.imapplication.R;
import com.gzy.imapplication.core.Auth;
import com.gzy.imapplication.model.Account;
import com.gzy.imapplication.model.Token;
import com.gzy.imapplication.net.MineApi;
import com.gzy.imapplication.net.URLSet;
import com.gzy.imapplication.net.core.XXModelCallback;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.io.IOException;

import okhttp3.Call;


public class MineFragment extends Fragment {

    public MineFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_mine, container, false);
    }

    TextView tv_name;
    ImageView iv_avatar;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_name = view.findViewById(R.id.tv_name);
        iv_avatar = view.findViewById(R.id.iv_avatar);
    }

    @Override
    public void onStart() {
        super.onStart();
        loadData();
    }

    public void loadData() {
        // 加载数据操作

        Token token1 = Auth.loadToken(this.getContext());

        String userid = token1.getUserid() + "";
        String token = token1.getToken();
        MineApi.loadMineInfo(userid, token, new XXModelCallback<Account>(Account.class) {


            @Override
            public void onResponseData(Call call, Account model) {
                refreshUI(model);
            }

            @Override
            public void onFailure2(Call call, IOException e, ErrType type, String message) {

                KProgressHUD.create(getContext())
                        .setLabel("请求失败")
                        .setDetailsLabel(message)
                        .setAutoDismiss(true)
                        .show();

            }
        });
    }

    private void refreshUI(Account account) {
        // 从 acc 中获取需要显示的数据， 并且设置到 View 上

        tv_name.setText(account.getName());



        if (TextUtils.isEmpty(account.getAvatar())) {

            Glide
                    .with(this)
                    .load(R.drawable.ic_tabbar_mine_n)
                    .into(iv_avatar);

            return;
        }

        String url = URLSet.File.PATH + "/" + account.getAvatar();
        Glide
                .with(this)
                .load(url)
                .into(iv_avatar);

    }
}
