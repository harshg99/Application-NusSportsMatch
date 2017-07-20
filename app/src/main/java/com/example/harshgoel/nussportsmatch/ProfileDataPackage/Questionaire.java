package com.example.harshgoel.nussportsmatch.ProfileDataPackage;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harshgoel.nussportsmatch.AppLoginPage;
import com.example.harshgoel.nussportsmatch.Logic.Player;
import com.example.harshgoel.nussportsmatch.Logic.Rating;
import com.example.harshgoel.nussportsmatch.Logic.sportsPlayer;
import com.example.harshgoel.nussportsmatch.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Harsh Goel on 7/19/2017.
 */

public class Questionaire extends AppCompatActivity {
    public TextView rating_awareness;
    public TextView rating_tennis;
    public TextView rating_tt;
    public TextView rating_badminton;
    public TextView rating_squash;
    public TextView rating_fitness;
    public SeekBar awarerating;
    public SeekBar tennisrating;
    public SeekBar ttrating;
    public SeekBar squashrating;
    public SeekBar badmintonrating;
    public SeekBar fitnessrating;
    public Button note;
    public Button set;
    private float awarevalue;
    private float fitvalue;
    private float tennisvalue;
    private float ttvalue;
    private float squashvalue;
    private float badmintonvalue;
    private FirebaseAuth auth;
    private DatabaseReference userref;
    private Player userplayer;
    public List<Float> value;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questionaire);
        awarevalue=0;
        fitvalue=0;
        tennisvalue=0;
        ttvalue=0;
        squashvalue=0;
        badmintonvalue=0;
        value=new ArrayList<Float>();
        rating_awareness=(TextView)findViewById(R.id.awarerating) ;
        rating_tennis=(TextView)findViewById(R.id.ratingtennis) ;
        rating_tt=(TextView)findViewById(R.id.ratingtabletennis) ;
        rating_squash=(TextView)findViewById(R.id.ratingsquash) ;
        rating_badminton=(TextView)findViewById(R.id.ratingbadminton) ;
        rating_fitness=(TextView)findViewById(R.id.fitnessrating) ;
        final ProgressDialog dialog=new ProgressDialog(this);
        dialog.setMessage("Retrieving...");
        dialog.show();

        awarerating=(SeekBar)findViewById(R.id.seekBar2) ;
        ttrating=(SeekBar)findViewById(R.id.seekBar4) ;
        tennisrating=(SeekBar)findViewById(R.id.seekBar7);
        squashrating=(SeekBar)findViewById(R.id.seekBar6);
        badmintonrating=(SeekBar)findViewById(R.id.seekBar5);
        fitnessrating=(SeekBar)findViewById(R.id.seekBar3) ;

        note=(Button)findViewById(R.id.Note);
        set=(Button) findViewById(R.id.Set);
        set.setEnabled(false);
        Toolbar k=(Toolbar)findViewById(R.id.q_bar);
        setSupportActionBar(k);

        auth=FirebaseAuth.getInstance();
        userref= FirebaseDatabase.getInstance().getReference();
        userref=userref.child("users").child(auth.getCurrentUser().getUid());
        userref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                dialog.cancel();
                userplayer=dataSnapshot.getValue(Player.class);
                if(userplayer.getTennis().getisAdded()&&!userplayer.getTennis().isQuestionaireCompleted()){
                    tennisrating.setEnabled(true);
                    userplayer.getTennis().setQuestionaireCompleted(true);
                }
                else{
                    tennisrating.setEnabled(false);
                    tennisrating.setProgress((int)(userplayer.getTennis().getRating().getRatingSkill()*20));
                }
                if(userplayer.getSquash().getisAdded()&&!userplayer.getSquash().isQuestionaireCompleted()){
                    squashrating.setEnabled(true);
                    userplayer.getSquash().setQuestionaireCompleted(true);
                }
                else{
                    squashrating.setEnabled(false);
                    squashrating.setProgress((int)(userplayer.getSquash().getRating().getRatingSkill()*20));
                }

                if(userplayer.getBadminton().getisAdded()&&!userplayer.getBadminton().isQuestionaireCompleted()){
                    badmintonrating.setEnabled(true);
                    userplayer.getBadminton().setQuestionaireCompleted(true);
                }
                else{
                    badmintonrating.setEnabled(false);
                    badmintonrating.setProgress((int)(userplayer.getBadminton().getRating().getRatingSkill()*20));
                }
                if(userplayer.getTt().getisAdded()&&!userplayer.getTt().isQuestionaireCompleted()){
                    ttrating.setEnabled(true);
                    userplayer.getTt().setQuestionaireCompleted(true);
                }
                else{
                    ttrating.setEnabled(false);
                    ttrating.setProgress((int)(userplayer.getTt().getRating().getRatingSkill()*20));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Database Reference:","Read ERROR");
            }
        });

        awarerating.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                awarevalue=(float)progress/20;
                rating_awareness.setText(awarevalue+"/5.00");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(Questionaire.this,"Rating At:"+awarevalue,Toast.LENGTH_SHORT);
                buttonenabled();
            }
        });
        tennisrating.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tennisvalue=(float)progress/20;
                rating_tennis.setText(tennisvalue+"/5.00");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(Questionaire.this,"Rating At:"+tennisvalue,Toast.LENGTH_SHORT);
                buttonenabled();
            }
        });
        ttrating.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ttvalue=(float)progress/20;
                rating_tt.setText(ttvalue+"/5.00");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(Questionaire.this,"Rating At:"+ttvalue,Toast.LENGTH_SHORT);
                buttonenabled();
            }
        });
        badmintonrating.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                badmintonvalue=(float)progress/20;
                rating_badminton.setText(badmintonvalue+"/5.00");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(Questionaire.this,"Rating At:"+badmintonvalue,Toast.LENGTH_SHORT);
                buttonenabled();
            }
        });
        fitnessrating.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                fitvalue=(float)progress/20;
                rating_fitness.setText(fitvalue+"/5.00");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(Questionaire.this,"Rating At:"+fitvalue,Toast.LENGTH_SHORT);
                buttonenabled();
            }
        });
        squashrating.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                squashvalue=(float)progress/20;
                rating_squash.setText(squashvalue+"/5.00");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(Questionaire.this,"Rating At:"+squashvalue,Toast.LENGTH_SHORT);
                buttonenabled();
            }
        });
    }
    public void Set(View V){
        if(tennisrating.isEnabled()){
            userplayer.getTennis().setRating(new Rating(awarevalue,tennisvalue,fitvalue));
        }
        if(squashrating.isEnabled()){
            value.add(squashvalue);
            userplayer.getSquash().setRating(new Rating(awarevalue,squashvalue,fitvalue));
        }

        if(badmintonrating.isEnabled()) {
            value.add(badmintonvalue);
            userplayer.getBadminton().setRating(new Rating(awarevalue,badmintonvalue,fitvalue));
        }
        if(ttrating.isEnabled()) {
            value.add(ttvalue);
            userplayer.getTt().setRating(new Rating(awarevalue,ttvalue,fitvalue));
        }
        userref.setValue(userplayer);
        Intent k=new Intent().setClass(this, AppLoginPage.class);
        startActivity(k);
        this.finish();

    }
    public void buttonenabled(){
        value.clear();
        if(fitvalue>0.8 && awarevalue>0.8){
            boolean val=true;
            if(tennisrating.isEnabled()){
                value.add(tennisvalue);
            }
            if(squashrating.isEnabled()){
                value.add(squashvalue);
            }

            if(badmintonrating.isEnabled()) {
                value.add(badmintonvalue);
            }
            if(ttrating.isEnabled()) {
                value.add(ttvalue);
            }
            Log.d("Size :",value.size()+"");
            for(int i=value.size()-1;i>=0;i--){
                float k=value.remove(i);
                Log.d("VALUE"+i+": ",k+"");
                if(k<0.8)
                    val=false;
            }
                set.setEnabled(val);

        }
        else{
            set.setEnabled(false);
        }


    }
    public void note(View v){
        AlertDialog.Builder notice=new AlertDialog.Builder(this);
        notice.setMessage("This is a Questionaire to determine your initial matchup. Please be frank with your ratings. Ratings must be atleast 0.8. This cannot be changed later.")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        notice.create().show();
    }
}
