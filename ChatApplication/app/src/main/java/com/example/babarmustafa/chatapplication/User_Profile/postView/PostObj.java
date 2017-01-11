package com.example.babarmustafa.chatapplication.User_Profile.postView;



/**
 * Created by muhammad imran on 05-Jan-17.
 */

public class PostObj {

    private String Text;
    private String Url;


    public PostObj() {
    }

    public PostObj(String text, String url) {
        Text = text;
        Url = url;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }


}
