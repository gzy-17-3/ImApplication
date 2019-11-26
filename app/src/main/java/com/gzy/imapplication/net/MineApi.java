package com.gzy.imapplication.net;

import com.gzy.imapplication.model.Account;
import com.gzy.imapplication.model.Token;
import com.gzy.imapplication.net.core.XXModelCallback;
import com.gzy.imapplication.net.core.XXURLUtils;

import java.util.HashMap;
import java.util.Map;

public class MineApi {
    public static void loadMineInfo(String userid, String token, XXModelCallback<Account> callback) {

        String url = URLSet.Mine.PATH + "/" +userid;

        Map<String, String> header = new HashMap<>();
        Map<String, String> para = new HashMap<>();

        header.put("Authorization","Bearer "+token);

        XXURLUtils.shared.get(url,header,para,callback);
    }

}
