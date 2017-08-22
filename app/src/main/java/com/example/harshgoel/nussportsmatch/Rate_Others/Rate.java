package com.example.harshgoel.nussportsmatch.Rate_Others;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
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
import com.example.harshgoel.nussportsmatch.Logic.Game;
import com.example.harshgoel.nussportsmatch.Logic.Player;
import com.example.harshgoel.nussportsmatch.Logic.Rating;
import com.example.harshgoel.nussportsmatch.Logic.RatingCount;
import com.example.harshgoel.nussportsmatch.ProfileDataPackage.Questionaire;
import com.example.harshgoel.nussportsmatch.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by harsh on 22/08/2017.
 */

public class Rate extends AppCompatActivity{
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
    private String Gamekey;
    private DatabaseReference ref_count;
    private DatabaseReference ref_game;
    private RatingCount counts;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questionaire);

        skills=getIntent().getStringArrayListExtra("Skills");
        sport=getIntent().getStringExtra("sport");
        Gamekey=getIntent().getStringExtra("GameID");
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
        ref_count=FirebaseDatabase.getInstance().getReference().child("ratingcount");
        ref_game=FirebaseDatabase.getInstance().getReference().child("game");
        ref_game=ref_game.child(Gamekey);
        ref_count=ref_count.child(auth.getCurrentUser().getUid());

        ref_count.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                counts=dataSnapshot.getValue(RatingCount.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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

        AlertDialog.Builder info=new AlertDialog.Builder(Rate.this);
        info.setMessage("This is an assessment of your partner's skill in the sport.Please assess wisely as it determines"+
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
                Toast.makeText(Rate.this,"Rating At:"+awarevalue,Toast.LENGTH_SHORT);
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
                Toast.makeText(Rate.this,"Rating At:"+skill4value,Toast.LENGTH_SHORT);
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
                Toast.makeText(Rate.this,"Rating At:"+skill1value,Toast.LENGTH_SHORT);
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
                Toast.makeText(Rate.this,"Rating At:"+skill2value,Toast.LENGTH_SHORT);
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
                Toast.makeText(Rate.this,"Rating At:"+fitvalue,Toast.LENGTH_SHORT);
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
                Toast.makeText(Rate.this,"Rating At:"+skill3value,Toast.LENGTH_SHORT);
                buttonenabled();
            }
        });

    }
    public void Set(View V){
        double skillvalue=(skill1value+skill2value+skill3value+skill4value)/4;
        setRating();
        if(sport.equals("tennis")) {
            int currentcount=counts.getTenniscount();
            Rating rating=new Rating();
            rating.setRatingAwareness((userplayer.getTennis().getRating().getRatingAwareness()*currentcount+awarevalue)/(++currentcount));
            rating.setRatingFitness((userplayer.getTennis().getRating().getRatingFitness()*currentcount+fitvalue)/(currentcount));
            rating.setRatingSkill((userplayer.getTennis().getRating().getRatingSkill()*currentcount+skillvalue)/(currentcount));
            userplayer.getTennis().setRating(rating);
            counts.setTenniscount(currentcount);

        }
        else if(sport.equals("squash")){
            int currentcount=counts.getSquashcount();
            Rating rating=new Rating();
            rating.setRatingAwareness((userplayer.getSquash().getRating().getRatingAwareness()*currentcount+awarevalue)/(++currentcount));
            rating.setRatingFitness((userplayer.getSquash().getRating().getRatingFitness()*currentcount+fitvalue)/(currentcount));
            rating.setRatingSkill((userplayer.getSquash().getRating().getRatingSkill()*currentcount+skillvalue)/(currentcount));
            userplayer.getSquash().setRating(rating);
            counts.setSquashcount(currentcount);
        }
        else if(sport.equals("badminton")){
            int currentcount=counts.getBadmintoncount();
            Rating rating=new Rating();
            rating.setRatingAwareness((userplayer.getBadminton().getRating().getRatingAwareness()*currentcount+awarevalue)/(++currentcount));
            rating.setRatingFitness((userplayer.getBadminton().getRating().getRatingFitness()*currentcount+fitvalue)/(currentcount));
            rating.setRatingSkill((userplayer.getBadminton().getRating().getRatingSkill()*currentcount+skillvalue)/(currentcount));
            userplayer.getBadminton().setRating(rating);
            counts.setBadmintoncount(currentcount);
        }
        else if(sport.equals("tt")) {
            int currentcount=counts.getTtcount();
            Rating rating=new Rating();
            rating.setRatingAwareness((userplayer.getTt().getRating().getRatingAwareness()*currentcount+awarevalue)/(++currentcount));
            rating.setRatingFitness((userplayer.getTt().getRating().getRatingFitness()*currentcount+fitvalue)/(currentcount));
            rating.setRatingSkill((userplayer.getTt().getRating().getRatingSkill()*currentcount+skillvalue)/(currentcount));
            userplayer.getTt().setRating(rating);
            counts.setTtcount(currentcount);
        }
        userref.setValue(userplayer);
        ref_count.setValue(counts);
        ref_game.child()
        Intent k=new Intent().setClass(this, AppLoginPage.class);
        startActivity(k);
        this.finish();

    }
    //the rating that is parsed from the current user should be displayed to the other user
    public void setRating(){
        ref_game.child(auth.getCurrentUser().getUid()).child("awarevalue").setValue(awarevalue);
        ref_game.child(auth.getCurrentUser().getUid()).child("fitvalue").setValue(awarevalue);
        ref_game.child(auth.getCurrentUser().getUid()).child("skill1value").setValue(awarevalue);
        ref_game.child(auth.getCurrentUser().getUid()).child("skill2value").setValue(awarevalue);
        ref_game.child(auth.getCurrentUser().getUid()).child("skill3value").setValue(awarevalue);
        ref_game.child(auth.getCurrentUser().getUid()).child("skill4value").setValue(awarevalue);

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
}
