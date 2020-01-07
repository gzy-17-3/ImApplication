package com.gzy.imapplication.model;

public class ChatSession {

    /**
     * id : 1
     * account : {"id":7,"phone":"1991","name":"phone+1991","bio":null,"avatar":null,"birthday":null,"gender":null,"createdDate":"2019-11-20T14:34:00"}
     * createdDate : 2020-01-07T15:19:24
     * lastModifiedDate : 2020-01-07T15:20:14
     * lastChat : {"id":2,"sessionid":1,"fromaid":3,"toaid":7,"content":"hehehe777","date":"2020-01-07T15:20:14"}
     */

    private int id;
    private Account account;
    private String createdDate;
    private String lastModifiedDate;
    private Chat lastChat;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Chat getLastChat() {
        return lastChat;
    }

    public void setLastChat(Chat lastChat) {
        this.lastChat = lastChat;
    }
}
