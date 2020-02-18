package com.example.instagram_clone.models;

import java.io.Serializable;

public class DtoProfile implements Serializable {

    private String profile_num;
    private String profile_name;
    private String account_ID;
    private String account_password;
    private String profile_picture;
    private String feed_folder;
    private String follower;
    private String following;

    public String getProfile_num() {
        return profile_num;
    }

    public void setProfile_num(String profile_num) {
        this.profile_num = profile_num;
    }

    public String getProfile_name() {
        return profile_name;
    }

    public void setProfile_name(String profile_name) {
        this.profile_name = profile_name;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public String getFollower() {
        return follower;
    }

    public void setFollower(String follower) {
        this.follower = follower;
    }

    public String getFollowing() {
        return following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }

    public String getFeed_folder() {
        return feed_folder;
    }

    public void setFeed_folder(String feed_folder) {
        this.feed_folder = feed_folder;
    }

    public String getAccount_ID() {
        return account_ID;
    }

    public void setAccount_ID(String account_ID) {
        this.account_ID = account_ID;
    }

    public String getAccount_password() {
        return account_password;
    }

    public void setAccount_password(String account_password) {
        this.account_password = account_password;
    }
}
