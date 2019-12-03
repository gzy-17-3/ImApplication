package com.gzy.imapplication.module.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

import com.gzy.imapplication.R;
import com.gzy.imapplication.core.Auth;
import com.gzy.imapplication.module.auth.LoginActivity;
import com.gzy.imapplication.module.auth.LoginSucceedBroadcast;
import com.gzy.imapplication.module.auth.LogoutSucceedBroadcast;
import com.gzy.imapplication.module.base.BaseActivity;
import com.gzy.imapplication.module.contacts.ContactsFragment;
import com.gzy.imapplication.module.message.MessageFragment;
import com.gzy.imapplication.module.mine.MineFragment;

public class HomeActivity extends BaseActivity {

    Fragment contactsFragment;
    Fragment messageFragment;
    Fragment mineFragment;
    private IntentFilter intentFilter;
    LogoutSucceedBroadcast logoutSucceedBroadcast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initFragment(R.id.item_tabbar_message);

        registerEvent();
    }

    private  void  registerEvent() {
        intentFilter = new IntentFilter();
        intentFilter.addAction(LogoutSucceedBroadcast.KEY);
        logoutSucceedBroadcast = new LogoutSucceedBroadcast();

        logoutSucceedBroadcast.runnable = new Runnable() {
            @Override
            public void run() {
                finish();
            }
        };

        registerReceiver(logoutSucceedBroadcast,intentFilter);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(logoutSucceedBroadcast);
        super.onDestroy();
    }

    public void onClickLogout(View view) {
        Auth.clearToken(this);

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

        runOnBackgroundThread(100, new Runnable() {
            @Override
            public void run() {
                finish();
            }
        });
    }



    private void initFragment(int startShow) {

        /*获取manager*/
        FragmentManager manager = this.getSupportFragmentManager();

        /*通过findFragmentById获取Fragment*/
        contactsFragment =  manager.findFragmentById(R.id.f_ContactsFragment);
        messageFragment =  manager.findFragmentById(R.id.f_MessageFragment);
        mineFragment =  manager.findFragmentById(R.id.f_MineFragment);

        // 默认隐藏所有的
        contactsFragment.getView().setVisibility(View.GONE);
        messageFragment.getView().setVisibility(View.GONE);
        mineFragment.getView().setVisibility(View.GONE);

        View view = findViewById(startShow);
        onClickTabbar(view);
    }


    View currentTabbarItemView;
    Fragment currentFragment;


    public void onClickTabbar(View view) {

        //  view  的 切换
        if (currentTabbarItemView != null) {
            currentTabbarItemView.setSelected(false);
        }

        view.setSelected(true);

        currentTabbarItemView = view;


        //  Fragment  的 切换
        if (currentFragment != null) {
            currentFragment.getView().setVisibility(View.GONE);
        }


        switch (view.getId() ){
            case R.id.item_tabbar_contacts:
                contactsFragment.getView().setVisibility(View.VISIBLE);
                currentFragment = contactsFragment;
                break;
            case R.id.item_tabbar_message:
                messageFragment.getView().setVisibility(View.VISIBLE);
                currentFragment = messageFragment;
                break;
            case R.id.item_tabbar_mine:
                mineFragment.getView().setVisibility(View.VISIBLE);
                currentFragment = mineFragment;
                break;
        }

    }
}
