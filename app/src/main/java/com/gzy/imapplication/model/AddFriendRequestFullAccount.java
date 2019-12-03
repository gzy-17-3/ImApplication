package com.gzy.imapplication.model;

import java.time.LocalDateTime;

public class AddFriendRequestFullAccount {
    private Long id;

    private Long aid;
    private Account account;

    private Long toaid;
    private String verifiInfo;
    private Integer operation;
    //            未操作
    //		0
    //    接受
    //		1
    //    忽略
    //		2
    //    拒绝
    //		3

    private String createdDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAid() {
        return aid;
    }

    public void setAid(Long aid) {
        this.aid = aid;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Long getToaid() {
        return toaid;
    }

    public void setToaid(Long toaid) {
        this.toaid = toaid;
    }

    public String getVerifiInfo() {
        return verifiInfo;
    }

    public void setVerifiInfo(String verifiInfo) {
        this.verifiInfo = verifiInfo;
    }

    public Integer getOperation() {
        return operation;
    }

    public void setOperation(Integer operation) {
        this.operation = operation;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
