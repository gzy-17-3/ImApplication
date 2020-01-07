package com.gzy.imapplication.net;

import com.gzy.imapplication.model.AddFriendRequestFullAccount;
import com.gzy.imapplication.model.ChatSession;
import com.gzy.imapplication.net.core.XXModelListCallback;
import com.gzy.imapplication.net.core.XXURLUtils;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Callback;

public class MessageApi {

    public static void send(String token, String sessionId, String toaid,String content,Callback callback) {

        String url = URLSet.Chat.INDEX + sessionId;


        Map<String, String> header = new HashMap<>();
        Map<String, String> para = new HashMap<>();

        header.put("Authorization","Bearer "+token);

        para.put("toaid",toaid);
        para.put("content",content);
//        {
//            "toaid":4,
//                "content":"hehehe777"
//        }

        XXURLUtils.shared.post(url,header,para,callback);
    }

    public static void loadChatList(String token, String sessionId, String lastChatId, Callback callback) {

        String url = URLSet.Chat.INDEX + sessionId;


        Map<String, String> header = new HashMap<>();
        Map<String, String> para = new HashMap<>();

        header.put("Authorization","Bearer "+token);

        para.put("lastChatId",lastChatId);

        XXURLUtils.shared.get(url,header,para,callback);
    }

    public static void loadSession(String token, XXModelListCallback<ChatSession> callback) {
        String url = URLSet.ChatSession.INDEX;


        Map<String, String> header = new HashMap<>();
        Map<String, String> para = new HashMap<>();

        header.put("Authorization","Bearer "+token);


        XXURLUtils.shared.get(url,header,para,callback);
    }

    public static void createSessionIfneed(String tokenValue, Integer id, Callback callback) {
        String url = URLSet.ChatSession.INDEX;


        Map<String, String> header = new HashMap<>();
        Map<String, String> para = new HashMap<>();

        header.put("Authorization","Bearer "+tokenValue);

        para.put("userid",id + "");

        XXURLUtils.shared.post(url,header,para,callback);
    }
}
