package com.gzy.imapplication;

class User {

// \"id\": 2558050,\n" +
//         "      \"slug\": \"d99a7dfae9e4\",\n" +
//         "      \"nickname\": \"阿栈\",\n" +
//         "      \"avatar_source\": \"http://upload.jianshu.io/users/upload_avatars/2558050/7761b285-2805-4534-9870-ba7dcc7538ec.jpg\",\n" +
//         "      \"total_likes_count\": 1559,\n" +
//         "      \"total_wordage\": 472764,\n" +
//         "      \"is_following_user\": false\n" +

    private int id;
    private String slug;
    private String nickname;
    private String avatar_source;
    private long total_likes_count;
    private long total_wordage;
    private boolean is_following_user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar_source() {
        return avatar_source;
    }

    public void setAvatar_source(String avatar_source) {
        this.avatar_source = avatar_source;
    }

    public long getTotal_likes_count() {
        return total_likes_count;
    }

    public void setTotal_likes_count(long total_likes_count) {
        this.total_likes_count = total_likes_count;
    }

    public long getTotal_wordage() {
        return total_wordage;
    }

    public void setTotal_wordage(long total_wordage) {
        this.total_wordage = total_wordage;
    }

    public boolean isIs_following_user() {
        return is_following_user;
    }

    public void setIs_following_user(boolean is_following_user) {
        this.is_following_user = is_following_user;
    }
}
