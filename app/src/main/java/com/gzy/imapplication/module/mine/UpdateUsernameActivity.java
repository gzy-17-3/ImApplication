package com.gzy.imapplication.module.mine;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.gzy.imapplication.R;
import com.gzy.imapplication.core.Auth;
import com.gzy.imapplication.model.Token;
import com.gzy.imapplication.module.base.BaseActivity;
import com.gzy.imapplication.net.MineApi;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class UpdateUsernameActivity extends BaseActivity {

    EditText et_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_username);

        et_username = findViewById(R.id.et_username);
    }

    public void onClickCommit(View view) {

        String trim = et_username.getText().toString().trim();
        if (TextUtils.isEmpty(trim)){
            Toast.makeText(this, "请输入 姓名", Toast.LENGTH_SHORT).show();
            return;
        }
        Token token = Auth.loadToken(this);
        Map<String, String> para = new HashMap<>();

        para.put("name",trim);

        MineApi.updateUserInfo(token.getUserid()+"", token.getToken(), para, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(()->{
                    Toast.makeText(UpdateUsernameActivity.this, "网络异常："+e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    runOnUiThread(()->{
                        Toast.makeText(UpdateUsernameActivity.this, "修改成功！", Toast.LENGTH_SHORT).show();
                        runOnUiThread(1000,()->finish());
                    });
                }else{
                    runOnUiThread(()->{
                        Toast.makeText(UpdateUsernameActivity.this, "修改失败："+response.code(), Toast.LENGTH_SHORT).show();
                    });
                }
            }
        });

    }
}
