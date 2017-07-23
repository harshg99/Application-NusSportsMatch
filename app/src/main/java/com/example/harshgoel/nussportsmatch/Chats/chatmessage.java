package com.example.harshgoel.nussportsmatch.Chats;

/**
 * Created by Harsh Goel on 7/22/2017.
 */

public class chatmessage {
    private String message;
    private String senderid;
    private long time;
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
    public String getSender() {
        return senderid;
    }

    public void setSender(String sender) {
        this.senderid = sender;
    }

    public long getTime() {
        return time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
