package com.example.instagram_clone;

import java.io.Serializable;

public class DtoProfile implements Serializable {

    private String profile_num;
    private String profile_name;
    private String profile_picture;
    private String follower;
    private String following;
    private String feed_folder;


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

}
