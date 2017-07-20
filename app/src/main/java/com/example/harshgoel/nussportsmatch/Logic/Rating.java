package com.example.harshgoel.nussportsmatch.Logic;

/**
 * Created by Harsh Goel on 7/5/2017.
 */
//abstract class that is base class for the rating
public class Rating {
    private double ratingFitness;
    private double ratingAwareness;
    private double ratingSkill;
    private double ratingNetSkill;
    public Rating(){
        ratingAwareness=0.0;
        ratingFitness=0.0;
        ratingSkill=0.0;
        setRatingNetSkill();
    }
    public Rating(double awareness,double skill,double fitness){
        setRatingAwareness(awareness);
        setRatingFitness(fitness);
        setRatingSkill(skill);
        setRatingNetSkill();
    }
    public void setRatingFitness(double a){
        ratingFitness=a;
    }
    public void setRatingSkill(double c){
        ratingSkill=c;
    }
    public void setRatingAwareness(double b){
        ratingAwareness=b;
    }
    public void setRatingNetSkill(){
        ratingNetSkill=0.25*ratingFitness+0.20*ratingAwareness+0.55*ratingSkill;
    }
    public double RatingNetSkillwithWeight(float wf1,float wa2,float ws3){
        return(wf1*ratingFitness+wa2*ratingAwareness+ws3*ratingSkill);
    }
    public double getRatingNetSkill(){
        return ratingNetSkill;
    }

    public double getRatingSkill() {
        return ratingSkill;
    }

    public double getRatingAwareness() {
        return ratingAwareness;
    }

    public double getRatingFitness() {
        return ratingFitness;
    }

    @Override
    public String toString() {
        return (" rs: "+getRatingSkill()+" ra: "+getRatingAwareness()+" rf: "+getRatingFitness()+" rns: "+getRatingNetSkill());
    }
}
