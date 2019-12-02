package com.gzy.imapplication.module.contacts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.gzy.imapplication.R;
import com.gzy.imapplication.module.base.BaseActivity;

public class SearchContactActivity extends BaseActivity {

    public static final String para_keyword_key = "keyword";

    TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_contact);


        tv_title = findViewById(R.id.tv_title);

        Intent intent = getIntent();
        String keyword = intent.getStringExtra(para_keyword_key);

        // 发起请求 获取搜索结果

        tv_title.setText(String.format("搜索结果（%s）", keyword));

    }
}
