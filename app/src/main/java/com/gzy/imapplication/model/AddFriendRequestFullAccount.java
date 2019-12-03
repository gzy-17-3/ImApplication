package com.gzy.imapplication.model;

import androidx.annotation.NonNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public OperationEnum getOperationEnum() {
        return OperationEnum.get(getOperation());
    }

    public enum OperationEnum{

        unknown(-1,"未知"),
        notOperating(0,"未操作"),
        accepted(1,"已接受"),
        ignore(2,"忽略"),
        reject(3,"拒绝");


        private int iValue;
        private String defStr;

        OperationEnum(int iValue,String defStr) {
            this.iValue = iValue;
            this.defStr = defStr;
        }

        public static OperationEnum get(Integer operation) {
            if (operation == null){
                return OperationEnum.notOperating;
            }
            if (operation == 0){
                return OperationEnum.notOperating;
            }
            if (operation == 1){
                return OperationEnum.accepted;
            }
            if (operation == 2){
                return OperationEnum.ignore;
            }
            if (operation == 3){
                return OperationEnum.reject;
            }
            return OperationEnum.unknown;
        }

        public static List<OperationEnum> canOperation() {
            return Arrays.asList(accepted, ignore, reject);
        }

        @NonNull
        @Override
        public String toString() {
            return defStr;
        }

        public int getiValue() {
            return iValue;
        }
    }
}
