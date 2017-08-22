package com.example.harshgoel.nussportsmatch.Logic;

/**
 * Created by harsh on 22/08/2017.
 */

public class Game {
    private String player1id;
    private String player2id;

    private boolean reminderplayer1;
    private boolean reminderplayer2;

    private boolean ratingotherplayer1;
    private boolean ratingotherplayer2;

    private String date;
    private String time;

    private long datetimemillis;
    private String key;

    private String sport;

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
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

    public boolean isReminderplayer1() {
        return reminderplayer1;
    }

    public void setReminderplayer1(boolean reminderplayer1) {
        this.reminderplayer1 = reminderplayer1;
    }

    public boolean isReminderplayer2() {
        return reminderplayer2;
    }

    public void setReminderplayer2(boolean reminderplayer2) {
        this.reminderplayer2 = reminderplayer2;
    }

    public boolean isRatingotherplayer1() {
        return ratingotherplayer1;
    }

    public void setRatingotherplayer1(boolean ratingotherplayer1) {
        this.ratingotherplayer1 = ratingotherplayer1;
    }

    public boolean isRatingotherplayer2() {
        return ratingotherplayer2;
    }

    public void setRatingotherplayer2(boolean ratingotherplayer2) {
        this.ratingotherplayer2 = ratingotherplayer2;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getDatetimemillis() {
        return datetimemillis;
    }

    public void setDatetimemillis(long datetimemillis) {
        this.datetimemillis = datetimemillis;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
