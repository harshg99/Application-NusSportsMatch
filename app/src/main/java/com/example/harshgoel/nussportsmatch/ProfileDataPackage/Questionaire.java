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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Harsh Goel on 7/19/2017.
 */

public class Questionaire extends AppCompatActivity {
    public TextView rating_awareness;
    public TextView rating_skill4;
    public TextView rating_skill1;
    public TextView rating_skill2;
    public TextView rating_skill3;
    public TextView rating_fitness;
    public SeekBar awarerating;
    public SeekBar skill4;
    public SeekBar skill1;
    public SeekBar skill3;
    public SeekBar skill2;
    public SeekBar fitnessrating;
    public Button note;
    public Button set;
    private float awarevalue;
    private float fitvalue;
    private float skill4value;
    private float skill1value;
    private float skill3value;
    private float skill2value;
    private FirebaseAuth auth;
    private DatabaseReference userref;
    private Player userplayer;
    public List<Float> value;
    private String sport;
    private ArrayList<String> skills;
    private List<TextView>textfields;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.questionaire);

        skills=getIntent().getStringArrayListExtra("Skills");
        sport=getIntent().getStringExtra("sport");
        assignskilltext();

        value=new ArrayList<Float>();

        rating_awareness=(TextView)findViewById(R.id.awarerating) ;
        rating_skill4=(TextView)findViewById(R.id.ratingtennis) ;
        rating_skill1=(TextView)findViewById(R.id.ratingtabletennis) ;
        rating_skill3=(TextView)findViewById(R.id.ratingsquash) ;
        rating_skill2=(TextView)findViewById(R.id.ratingbadminton) ;
        rating_fitness=(TextView)findViewById(R.id.fitnessrating) ;

        final ProgressDialog dialog=new ProgressDialog(this);
        dialog.setMessage("Retrieving...");
        dialog.show();

        awarerating=(SeekBar)findViewById(R.id.seekBar2) ;
        skill1=(SeekBar)findViewById(R.id.seekBar4) ;
        skill4=(SeekBar)findViewById(R.id.seekBar7);
        skill3=(SeekBar)findViewById(R.id.seekBar6);
        skill2=(SeekBar)findViewById(R.id.seekBar5);
        fitnessrating=(SeekBar)findViewById(R.id.seekBar3) ;

        note=(Button)findViewById(R.id.Note);
        set=(Button) findViewById(R.id.Set);
        set.setEnabled(false);

        Toolbar k=(Toolbar)findViewById(R.id.q_bar);
        k.setTitle(sport.toUpperCase());
        setSupportActionBar(k);


        auth=FirebaseAuth.getInstance();
        userref= FirebaseDatabase.getInstance().getReference();
        userref=userref.child("users").child(auth.getCurrentUser().getUid());

        userref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userplayer=dataSnapshot.getValue(Player.class);
                dialog.cancel();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        AlertDialog.Builder info=new AlertDialog.Builder(Questionaire.this);
        info.setMessage("This Questionaire is a personal assessment of your skill in the sport.Please assess wisely as they determine"+
                "as it is going to be used for matching up. "
        );
        info.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        info.show();
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
        skill4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                skill4value=(float)progress/20;
                rating_skill4.setText(skill4value+"/5.00");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(Questionaire.this,"Rating At:"+skill4value,Toast.LENGTH_SHORT);
                buttonenabled();
            }
        });
        skill1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                skill1value=(float)progress/20;
                rating_skill1.setText(skill1value+"/5.00");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(Questionaire.this,"Rating At:"+skill1value,Toast.LENGTH_SHORT);
                buttonenabled();
            }
        });
        skill2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                skill2value=(float)progress/20;
                rating_skill2.setText(skill2value+"/5.00");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(Questionaire.this,"Rating At:"+skill2value,Toast.LENGTH_SHORT);
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
        skill3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                skill3value=(float)progress/20;
                rating_skill3.setText(skill3value+"/5.00");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(Questionaire.this,"Rating At:"+skill3value,Toast.LENGTH_SHORT);
                buttonenabled();
            }
        });
    }
    public void Set(View V){
        double skillvalue=(skill1value+skill2value+skill3value+skill4value)/4;
        if(sport.equals("tennis")) {
            userplayer.getTennis().setRating(new Rating(awarevalue, skillvalue, fitvalue));
            userplayer.getTennis().setAdded(true);
            userplayer.getTennis().setQuestionaireCompleted(true);
        }
        else if(sport.equals("squash")){
            userplayer.getSquash().setRating(new Rating(awarevalue, skillvalue, fitvalue));
            userplayer.getSquash().setAdded(true);
            userplayer.getSquash().setQuestionaireCompleted(true);
        }
        else if(sport.equals("badminton")){
            userplayer.getBadminton().setRating(new Rating(awarevalue, skillvalue, fitvalue));
            userplayer.getBadminton().setAdded(true);
            userplayer.getBadminton().setQuestionaireCompleted(true);
        }
        else if(sport.equals("tt")) {
            userplayer.getTt().setRating(new Rating(awarevalue, skillvalue, fitvalue));
            userplayer.getTt().setAdded(true);
            userplayer.getTt().setQuestionaireCompleted(true);
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
            if(skill4.isEnabled()){
                value.add(skill4value);
            }
            if(skill3.isEnabled()){
                value.add(skill3value);
            }

            if(skill2.isEnabled()) {
                value.add(skill2value);
            }
            if(skill1.isEnabled()) {
                value.add(skill1value);
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
        notice.setTitle("Guide");
        notice.setMessage("There are two broad categories for assessment. In the General category you rate yourself based on factors generally needed to play the sport well" +
                ".The Skill category consists of those skills which are considered to be essential for that sport. A rating of 5 means that you are best in the university with regards to that ability." +
                "A rating of 4 @ above means that " +
                "you are University level. A rating of above 3 means that you are faculty level and so on. ")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        notice.create().show();
    }
    private void assignskilltext(){
        textfields=new ArrayList<TextView>();
        textfields.add((TextView)findViewById(R.id.textView10));
        textfields.add((TextView)findViewById(R.id.textView11));
        textfields.add((TextView)findViewById(R.id.textView12));
        textfields.add((TextView)findViewById(R.id.textView5));

        for(int i=0;i<textfields.size();i++){
            textfields.get(i).setText(skills.get(i));
        }
    }
}
