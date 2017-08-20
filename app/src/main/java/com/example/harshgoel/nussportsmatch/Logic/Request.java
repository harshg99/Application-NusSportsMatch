package com.example.harshgoel.nussportsmatch.Logic;

/**
 * Created by Harsh Goel on 7/22/2017.
 */

public class Request {
    private String playersendID;
    public String namesender;//original person who sent the request
    public String namerecieve;//person who recieves the request
    public double netrating;
    private String playerrecievedID;
    private String date;
    private String time;
    public String Sport;
    public String RequestID;
    public int accepted;
    public String getplayersendID(){
        return playersendID;
    }

    public String getPlayerrecievedID() {
        return playerrecievedID;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
    public void setPlayersendID(String playersendID){
        this.playersendID=playersendID;
    }

    public void setPlayerrecievedID(String playerrecievedID) {
        this.playerrecievedID = playerrecievedID;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public void setTime(String time){
        this.time=time;
    }


    @Override
    public String toString() {
        return (playersendID+playerrecievedID+date+time+Sport);
    }
}
