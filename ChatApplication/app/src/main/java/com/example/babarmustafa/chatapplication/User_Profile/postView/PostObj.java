package com.example.babarmustafa.chatapplication.User_Profile.postView;


/**
 * Created by muhammad imran on 05-Jan-17.
 */

public class PostObj {
    private String title;
    private String Desc;
    private String images;

    public PostObj(String title, String desc, String images) {
        this.title = title;
        Desc = desc;
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        this.Desc = desc;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

}
