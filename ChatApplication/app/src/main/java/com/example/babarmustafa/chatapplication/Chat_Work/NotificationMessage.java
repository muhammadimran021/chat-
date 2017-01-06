package com.example.babarmustafa.chatapplication.Chat_Work;

/**
 * Created by BabarMustafa on 10/24/2016.
 */

public class NotificationMessage {
    private String message;
    private String pushId;
    private String UUID;
    private String Time;


    public NotificationMessage(String message) {
        this.message = message;
    }

    public NotificationMessage(String message, String time) {
        this.message = message;
        Time = time;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public NotificationMessage() {
    }

    public NotificationMessage(String message, String pushId, String UUID, String time) {
        this.message = message;
        this.pushId = pushId;
        this.UUID = UUID;
        Time = time;
    }

    public NotificationMessage(String message, String pushId, String UUID) {
        this.message = message;
        this.pushId = pushId;
        this.UUID = UUID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }
}
