package com.example.instagram_clone.models;

import java.io.Serializable;

public class DtoFeed implements Serializable {

//    private String feed_num;
    private String feed_picture;
    private String feed_contents;
    private String profile_name;

    public String getFeed_picture() {
        return feed_picture;
    }

    public void setFeed_picture(String feed_picture) {
        this.feed_picture = feed_picture;
    }

    public String getFeed_contents() {
        return feed_contents;
    }

    public void setFeed_contents(String feed_contents) {
        this.feed_contents = feed_contents;
    }

    public String getProfile_name(){
        return profile_name;
    }

    public void setProfile_name(String profile_name) {
        this.profile_name = profile_name;
    }
}
