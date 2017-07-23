package com.example.harshgoel.nussportsmatch.Chats;

/**
 * Created by Harsh Goel on 7/22/2017.
 */

public class chatbar {
    private String recieverofmessage;
    private String senderofmessagename;
    private String getRecieverofmessageid;
    private String senderofmessageid;
    private String key;
    private String lastmessage;
    private String time;
    public String getRecieverofmessage() {
        return recieverofmessage;
    }

    public String getLastmessage() {
        return lastmessage;
    }

    public String getGetRecieverofmessageid() {
        return getRecieverofmessageid;
    }

    public String getSenderofmessagename() {
        return senderofmessagename;
    }

    public String getTime() {
        return time;
    }

    public String getSenderofmessageid() {
        return senderofmessageid;
    }

    public void setGetRecieverofmessageid(String getRecieverofmessageid) {
        this.getRecieverofmessageid = getRecieverofmessageid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setLastmessage(String lastmessage) {
        this.lastmessage = lastmessage;
    }

    public void setRecieverofmessage(String recieverofmessage) {
        this.recieverofmessage = recieverofmessage;
    }

    public void setSenderofmessageid(String senderofmessageid) {
        this.senderofmessageid = senderofmessageid;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setSenderofmessagename(String senderofmessagename) {
        this.senderofmessagename = senderofmessagename;
    }
}
