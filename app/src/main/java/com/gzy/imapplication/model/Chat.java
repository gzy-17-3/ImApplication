package com.gzy.imapplication.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.chad.library.adapter.base.entity.MultiItemEntity;

public class Chat implements MultiItemEntity {

    public static final int Me = 1;
    public static final int Other = 2;




    private int id;
    private int sessionid;
    private int fromaid;
    private int toaid;
    private String content;
    private String date;


    /**
     * from : {"id":3,"phone":"186111","name":"phone+186111","bio":null,"avatar":null,"birthday":null,"gender":null,"createdDate":"2019-11-20T14:13:13"}
     * to : {"id":4,"phone":"199","name":"666666","bio":null,"avatar":"9d503133-f41f-49c0-ba95-63ed3d9a44d7.png","birthday":null,"gender":null,"createdDate":"2019-11-20T14:30:53"}
     */

    private Account from;
    private Account to;
    private int itemType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSessionid() {
        return sessionid;
    }

    public void setSessionid(int sessionid) {
        this.sessionid = sessionid;
    }

    public int getFromaid() {
        return fromaid;
    }

    public void setFromaid(int fromaid) {
        this.fromaid = fromaid;
    }

    public int getToaid() {
        return toaid;
    }

    public void setToaid(int toaid) {
        this.toaid = toaid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Account getFrom() {
        return from;
    }

    public void setFrom(Account from) {
        this.from = from;
    }

    public Account getTo() {
        return to;
    }

    public void setTo(Account to) {
        this.to = to;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}
