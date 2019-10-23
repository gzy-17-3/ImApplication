package com.gzy.imapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 解析 json
        JSONObject jsonObject = JSON.parseObject(json);
        // 读取json 里的users 数组
        JSONArray jsonArray = jsonObject.getJSONArray("users");

        // 遍历数组
        List<User> userList = new ArrayList<>();

        for (int i = 0; i < jsonArray.size(); i++) {
            User user = jsonArray.getJSONObject(i).toJavaObject(User.class);
            userList.add(user);
        }
        // 解析完成

        Toast.makeText(this, "解析完成:" + userList.size(), Toast.LENGTH_SHORT).show();

    }




    String json = "{\n" +
            "  \"users\": [\n" +
            "    {\n" +
            "      \"id\": 2558050,\n" +
            "      \"slug\": \"d99a7dfae9e4\",\n" +
            "      \"nickname\": \"阿栈\",\n" +
            "      \"avatar_source\": \"http://upload.jianshu.io/users/upload_avatars/2558050/7761b285-2805-4534-9870-ba7dcc7538ec.jpg\",\n" +
            "      \"total_likes_count\": 1559,\n" +
            "      \"total_wordage\": 472764,\n" +
            "      \"is_following_user\": false\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 278,\n" +
            "      \"slug\": \"paQcNW\",\n" +
            "      \"nickname\": \"邓哲\",\n" +
            "      \"avatar_source\": \"http://upload.jianshu.io/users/upload_avatars/278/3d103a3cbb96.jpg\",\n" +
            "      \"total_likes_count\": 1272,\n" +
            "      \"total_wordage\": 412683,\n" +
            "      \"is_following_user\": false\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 13213889,\n" +
            "      \"slug\": \"080bb4eac1c9\",\n" +
            "      \"nickname\": \"无限猴子\",\n" +
            "      \"avatar_source\": \"http://upload.jianshu.io/users/upload_avatars/13213889/7314c5cc-ca7f-4542-b914-2c8dffaf324d.jpg\",\n" +
            "      \"total_likes_count\": 2221,\n" +
            "      \"total_wordage\": 412681,\n" +
            "      \"is_following_user\": false\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 52841,\n" +
            "      \"slug\": \"8f5b45499715\",\n" +
            "      \"nickname\": \"闫泽华\",\n" +
            "      \"avatar_source\": \"http://upload.jianshu.io/users/upload_avatars/52841/251f5481-67b6-4bf0-86f4-faac9768beb3.jpeg\",\n" +
            "      \"total_likes_count\": 4438,\n" +
            "      \"total_wordage\": 252039,\n" +
            "      \"is_following_user\": false\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 2558050,\n" +
            "      \"slug\": \"d99a7dfae9e4\",\n" +
            "      \"nickname\": \"阿栈\",\n" +
            "      \"avatar_source\": \"http://upload.jianshu.io/users/upload_avatars/2558050/7761b285-2805-4534-9870-ba7dcc7538ec.jpg\",\n" +
            "      \"total_likes_count\": 1559,\n" +
            "      \"total_wordage\": 472764,\n" +
            "      \"is_following_user\": false\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 278,\n" +
            "      \"slug\": \"paQcNW\",\n" +
            "      \"nickname\": \"邓哲\",\n" +
            "      \"avatar_source\": \"http://upload.jianshu.io/users/upload_avatars/278/3d103a3cbb96.jpg\",\n" +
            "      \"total_likes_count\": 1272,\n" +
            "      \"total_wordage\": 412683,\n" +
            "      \"is_following_user\": false\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 13213889,\n" +
            "      \"slug\": \"080bb4eac1c9\",\n" +
            "      \"nickname\": \"无限猴子\",\n" +
            "      \"avatar_source\": \"http://upload.jianshu.io/users/upload_avatars/13213889/7314c5cc-ca7f-4542-b914-2c8dffaf324d.jpg\",\n" +
            "      \"total_likes_count\": 2221,\n" +
            "      \"total_wordage\": 412681,\n" +
            "      \"is_following_user\": false\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 52841,\n" +
            "      \"slug\": \"8f5b45499715\",\n" +
            "      \"nickname\": \"闫泽华\",\n" +
            "      \"avatar_source\": \"http://upload.jianshu.io/users/upload_avatars/52841/251f5481-67b6-4bf0-86f4-faac9768beb3.jpeg\",\n" +
            "      \"total_likes_count\": 4438,\n" +
            "      \"total_wordage\": 252039,\n" +
            "      \"is_following_user\": false\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 2558050,\n" +
            "      \"slug\": \"d99a7dfae9e4\",\n" +
            "      \"nickname\": \"阿栈\",\n" +
            "      \"avatar_source\": \"http://upload.jianshu.io/users/upload_avatars/2558050/7761b285-2805-4534-9870-ba7dcc7538ec.jpg\",\n" +
            "      \"total_likes_count\": 1559,\n" +
            "      \"total_wordage\": 472764,\n" +
            "      \"is_following_user\": false\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 278,\n" +
            "      \"slug\": \"paQcNW\",\n" +
            "      \"nickname\": \"邓哲\",\n" +
            "      \"avatar_source\": \"http://upload.jianshu.io/users/upload_avatars/278/3d103a3cbb96.jpg\",\n" +
            "      \"total_likes_count\": 1272,\n" +
            "      \"total_wordage\": 412683,\n" +
            "      \"is_following_user\": false\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 13213889,\n" +
            "      \"slug\": \"080bb4eac1c9\",\n" +
            "      \"nickname\": \"无限猴子\",\n" +
            "      \"avatar_source\": \"http://upload.jianshu.io/users/upload_avatars/13213889/7314c5cc-ca7f-4542-b914-2c8dffaf324d.jpg\",\n" +
            "      \"total_likes_count\": 2221,\n" +
            "      \"total_wordage\": 412681,\n" +
            "      \"is_following_user\": false\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 52841,\n" +
            "      \"slug\": \"8f5b45499715\",\n" +
            "      \"nickname\": \"闫泽华\",\n" +
            "      \"avatar_source\": \"http://upload.jianshu.io/users/upload_avatars/52841/251f5481-67b6-4bf0-86f4-faac9768beb3.jpeg\",\n" +
            "      \"total_likes_count\": 4438,\n" +
            "      \"total_wordage\": 252039,\n" +
            "      \"is_following_user\": false\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 2558050,\n" +
            "      \"slug\": \"d99a7dfae9e4\",\n" +
            "      \"nickname\": \"阿栈\",\n" +
            "      \"avatar_source\": \"http://upload.jianshu.io/users/upload_avatars/2558050/7761b285-2805-4534-9870-ba7dcc7538ec.jpg\",\n" +
            "      \"total_likes_count\": 1559,\n" +
            "      \"total_wordage\": 472764,\n" +
            "      \"is_following_user\": false\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 278,\n" +
            "      \"slug\": \"paQcNW\",\n" +
            "      \"nickname\": \"邓哲\",\n" +
            "      \"avatar_source\": \"http://upload.jianshu.io/users/upload_avatars/278/3d103a3cbb96.jpg\",\n" +
            "      \"total_likes_count\": 1272,\n" +
            "      \"total_wordage\": 412683,\n" +
            "      \"is_following_user\": false\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 13213889,\n" +
            "      \"slug\": \"080bb4eac1c9\",\n" +
            "      \"nickname\": \"无限猴子\",\n" +
            "      \"avatar_source\": \"http://upload.jianshu.io/users/upload_avatars/13213889/7314c5cc-ca7f-4542-b914-2c8dffaf324d.jpg\",\n" +
            "      \"total_likes_count\": 2221,\n" +
            "      \"total_wordage\": 412681,\n" +
            "      \"is_following_user\": false\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 52841,\n" +
            "      \"slug\": \"8f5b45499715\",\n" +
            "      \"nickname\": \"闫泽华\",\n" +
            "      \"avatar_source\": \"http://upload.jianshu.io/users/upload_avatars/52841/251f5481-67b6-4bf0-86f4-faac9768beb3.jpeg\",\n" +
            "      \"total_likes_count\": 4438,\n" +
            "      \"total_wordage\": 252039,\n" +
            "      \"is_following_user\": false\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 7416466,\n" +
            "      \"slug\": \"e7dd0b3c7b70\",\n" +
            "      \"nickname\": \"老沈1\",\n" +
            "      \"avatar_source\": \"http://upload.jianshu.io/users/upload_avatars/7416466/fc1a1a0d-e3c7-4bca-9720-028c5c9914f3.jpg\",\n" +
            "      \"total_likes_count\": 700,\n" +
            "      \"total_wordage\": 133741,\n" +
            "      \"is_following_user\": false\n" +
            "    }\n" +
            "  ],\n" +
            "  \"total_count\": 39415\n" +
            "}";
}
