package com.gzy.imapplication.model;

import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.gzy.imapplication.net.URLSet;

public class Account {

    /**
     * id : 1
     * phone : 18611
     * name : phone+18611
     * bio : null
     * avatar : null
     * birthday : null
     * gender : null
     * createdDate : 2019-11-19T16:16:23.181917
     */

    private int id;
    private String phone;
    private String name;
    private String bio;
    private String avatar;
    private String birthday;
    private int gender;
    private String createdDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }




    public String getAvatarUrlString() {

        if (TextUtils.isEmpty(getAvatar())){
            return null;
        }

        if (getAvatar().startsWith("http")) {
            return getAvatar();
        }

        String url = URLSet.File.PATH + "/" + getAvatar();

        return url;
    }
}
