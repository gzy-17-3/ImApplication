package com.gzy.imapplication.module.mine;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gzy.imapplication.R;
import com.gzy.imapplication.core.Auth;
import com.gzy.imapplication.model.Account;
import com.gzy.imapplication.model.Token;
import com.gzy.imapplication.module.base.BaseActivity;
import com.gzy.imapplication.net.MineApi;
import com.gzy.imapplication.net.URLSet;
import com.gzy.imapplication.net.core.XXModelCallback;
import com.mingle.sweetpick.CustomDelegate;
import com.mingle.sweetpick.SweetSheet;

import java.io.IOException;

import okhttp3.Call;

public class MyInfoActivity extends BaseActivity {

    ImageView iv_avatar;
    TextView tv_name;
    TextView tv_gender;
    private Account account;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        iv_avatar = findViewById(R.id.iv_avatar);
        tv_name = findViewById(R.id.tv_name);
        tv_gender = findViewById(R.id.tv_gender);

//        Token token = Auth.loadToken(this);

//        dialogs  对话框

//        Alert   弹框
//        AlertSheet  底部弹出的弹框


    }

    @Override
    protected void onStart() {
        super.onStart();
        loadData();
    }

    public void loadData() {
        // 加载数据操作

        Token token1 = Auth.loadToken(this);

        String userid = token1.getUserid() + "";
        String token = token1.getToken();
        MineApi.loadMineInfo(userid, token, new XXModelCallback<Account>(Account.class) {

            @Override
            public void onResponseData(Call call, Account model) {
                MyInfoActivity.this.account = model;
                refreshUI(model);
            }

            @Override
            public void onFailure2(Call call, IOException e, ErrType type, String message) {
                Toast.makeText(getContext(), "" + message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void refreshUI(Account account) {
        // 从 acc 中获取需要显示的数据， 并且设置到 View 上

        tv_name.setText(account.getName());

        String genderStr = "未知";
        if (account.getGender() == 1) {
            genderStr = "女";
        } else if (account.getGender() == 2) {
            genderStr = "男";
        }

        tv_gender.setText(genderStr);

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

    public void onClickRowAvatar(View view) {

        if (account == null) {
            return;
        }



    }

    public void onClickRowName(View view) {

    }

    public void onClickRowGender(View view) {

    }
}
