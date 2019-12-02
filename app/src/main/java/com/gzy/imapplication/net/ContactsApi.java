package com.gzy.imapplication.net;

import com.gzy.imapplication.model.Account;
import com.gzy.imapplication.net.core.XXModelCallback;
import com.gzy.imapplication.net.core.XXURLUtils;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Callback;

public class ContactsApi {
    public static void find(String token, String keyword, Callback callback) {

        String url = URLSet.Friend.FIND;


        Map<String, String> header = new HashMap<>();
        Map<String, String> para = new HashMap<>();

        header.put("Authorization","Bearer "+token);

        para.put("name",keyword);

        XXURLUtils.shared.get(url,header,para,callback);
    }
}
