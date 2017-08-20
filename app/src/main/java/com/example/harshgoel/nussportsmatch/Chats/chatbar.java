package com.example.harshgoel.nussportsmatch.Chats;

/**
 * Created by Harsh Goel on 7/22/2017.
 */

public class chatbar {
    private String player1;//sender of the request
    private String player2;//reciever of  the request
    private String player1id;
    private String player2id;
    private String key;
    private String lastmessage;
    private String time;


    public String getLastmessage() {
        return lastmessage;
    }

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public String getPlayer1id() {
        return player1id;
    }

    public void setPlayer1id(String player1id) {
        this.player1id = player1id;
    }

    public String getPlayer2id() {
        return player2id;
    }

    public void setPlayer2id(String player2id) {
        this.player2id = player2id;
    }

    public String getTime() {
        return time;
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

    public void setTime(String time) {
        this.time = time;
    }


}
