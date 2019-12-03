package com.gzy.imapplication.net;

import com.gzy.imapplication.model.AddFriendRequestFullAccount;
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
    /**
     * 申请
     */
    public static void apply(String token, Integer toUid, String verifiInfo, Callback callback) {

        String url = URLSet.Friend.APPLY;


        Map<String, String> header = new HashMap<>();
        Map<String, String> para = new HashMap<>();

        header.put("Authorization","Bearer "+token);

        para.put("toUid",toUid.toString());
        para.put("verifiInfo",verifiInfo);

        XXURLUtils.shared.post(url,header,para,callback);
    }

    /**
     * 应答
     * @param token
     * @param requestid
     * @param opt
     * @param callback
     */
    public static void reply(String token, Long requestid, AddFriendRequestFullAccount.OperationEnum opt, Callback callback) {

        String url = URLSet.Friend.REPLY;

        Map<String, String> header = new HashMap<>();
        Map<String, String> para = new HashMap<>();

        header.put("Authorization","Bearer "+token);

        para.put("requestid",requestid.toString());
        para.put("opt",opt.getiValue()+"");

        XXURLUtils.shared.post(url,header,para,callback);
    }


    public static void loadAddFriendRequest(String token, Callback callback) {
        String url = URLSet.Friend.ADD_FRIEND_REQUEST;

        Map<String, String> header = new HashMap<>();
        Map<String, String> para = new HashMap<>();

        header.put("Authorization","Bearer "+token);

        XXURLUtils.shared.get(url,header,para,callback);
    }
}
