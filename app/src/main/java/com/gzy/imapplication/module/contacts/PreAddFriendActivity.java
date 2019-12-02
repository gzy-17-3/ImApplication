package com.gzy.imapplication.module.contacts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.gzy.imapplication.R;
import com.gzy.imapplication.module.base.BaseActivity;

public class PreAddFriendActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_add_friend);

    }

    public void onClickFind(View view) {
        EditText edt_keyword = findViewById(R.id.edt_keyword);
        String text = edt_keyword.getText().toString().trim();
        if (TextUtils.isEmpty(text)) {
            Toast.makeText(this, "请输入关键词", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, SearchContactActivity.class);

        intent.putExtra(SearchContactActivity.para_keyword_key,text);

        startActivity(intent);
    }
}
