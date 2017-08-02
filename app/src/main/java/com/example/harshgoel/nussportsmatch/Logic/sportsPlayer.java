package com.example.harshgoel.nussportsmatch.Logic;

/**
 * Created by Harsh Goel on 7/13/2017.
 */

public class sportsPlayer {
    private Rating SportsRate;
    private boolean isAdded;
    private boolean questionaireCompleted;
    public sportsPlayer(){SportsRate=new Rating();isAdded=false;questionaireCompleted=false;}
    public void setRating(Rating a){
        SportsRate=a;
    }
    public boolean getisAdded(){return isAdded;}

    public boolean isQuestionaireCompleted() {
        return questionaireCompleted;
    }
    public Rating getRating(){return SportsRate;}
    public void setAdded(boolean a){
        isAdded=a;
    }
    public void setQuestionaireCompleted(boolean a){
        questionaireCompleted=a;
    }
    public void setSportsRate(Rating SportsRate){
        this.SportsRate=SportsRate;
    }
    @Override
    public String toString() {
        return ("SportsRate: "+SportsRate.toString()+" add: "+getisAdded()+" qc: "+isQuestionaireCompleted());
    }
}
