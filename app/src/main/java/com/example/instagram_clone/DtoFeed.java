package com.example.instagram_clone;

import java.io.Serializable;

public class DtoFeed implements Serializable {

    private String feed_num;
    private String feed_picture;
    private String feed_contents;

    public String getFeed_num() {
        return feed_num;
    }

    public void setFeed_num(String feed_num) {
        this.feed_num = feed_num;
    }

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


}
