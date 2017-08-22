package com.example.harshgoel.nussportsmatch.Rate_Others;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.harshgoel.nussportsmatch.Logic.Game;
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

public class ViewRate extends AppCompatActivity {

    public class ratings{
        private float awarevalue;
        private float fitvalue;
        private float skill4value;
        private float skill1value;
        private float skill3value;
        private float skill2value;

        public float getAwarevalue() {
            return awarevalue;
        }

        public void setAwarevalue(float awarevalue) {
            this.awarevalue = awarevalue;
        }

        public float getFitvalue() {
            return fitvalue;
        }

        public void setFitvalue(float fitvalue) {
            this.fitvalue = fitvalue;
        }

        public float getSkill4value() {
            return skill4value;
        }

        public void setSkill4value(float skill4value) {
            this.skill4value = skill4value;
        }

        public float getSkill1value() {
            return skill1value;
        }

        public void setSkill1value(float skill1value) {
            this.skill1value = skill1value;
        }

        public float getSkill3value() {
            return skill3value;
        }

        public void setSkill3value(float skill3value) {
            this.skill3value = skill3value;
        }

        public float getSkill2value() {
            return skill2value;
        }

        public void setSkill2value(float skill2value) {
            this.skill2value = skill2value;
        }
    }
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
    private FirebaseAuth auth;
    private DatabaseReference userref;

    public List<Float> value;
    private String sport;
    private ArrayList<String> skills;
    private List<TextView>textfields;
    private String Gamekey;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questionaire);

        skills=getIntent().getStringArrayListExtra("Skills");
        sport=getIntent().getStringExtra("sport");
        Gamekey=getIntent().getStringExtra("GameID");
        assignskilltext();


        rating_awareness=(TextView)findViewById(R.id.awarerating) ;
        rating_skill4=(TextView)findViewById(R.id.ratingtennis) ;
        rating_skill1=(TextView)findViewById(R.id.ratingtabletennis) ;
        rating_skill3=(TextView)findViewById(R.id.ratingsquash) ;
        rating_skill2=(TextView)findViewById(R.id.ratingbadminton) ;
        rating_fitness=(TextView)findViewById(R.id.fitnessrating) ;

        awarerating=(SeekBar)findViewById(R.id.seekBar2) ;
        skill1=(SeekBar)findViewById(R.id.seekBar4) ;
        skill4=(SeekBar)findViewById(R.id.seekBar7);
        skill3=(SeekBar)findViewById(R.id.seekBar6);
        skill2=(SeekBar)findViewById(R.id.seekBar5);
        fitnessrating=(SeekBar)findViewById(R.id.seekBar3) ;

        note=(Button)findViewById(R.id.Note);
        set=(Button) findViewById(R.id.Set);
        set.setEnabled(false);
        note.setEnabled(false);
        note.setAlpha(0);
        set.setAlpha(0);

        userref= FirebaseDatabase.getInstance().getReference().child("game").child(Gamekey);
        auth=FirebaseAuth.getInstance();

        userref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Game game=dataSnapshot.getValue(Game.class);
                String ref_uid;
                if(game.getPlayer1id().equals(auth.getCurrentUser().getUid())){
                    ref_uid=game.getPlayer2id();
                }
                else{
                    ref_uid=game.getPlayer1id();
                }
                DatabaseReference dataref=userref.child(ref_uid);
                dataref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ratings rating=dataSnapshot.getValue(ratings.class);
                        setFields(rating);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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
    private void setFields(ratings rating){
        rating_skill4.setText(rating.getSkill4value()+"/5.00");
        rating_awareness.setText(rating.getAwarevalue()+"/5.00");
        rating_fitness.setText(rating.getFitvalue()+"/5.00");
        rating_skill1.setText(rating.getSkill1value()+"/5.00");
        rating_skill2.setText(rating.getSkill2value()+"/5.00");
        rating_skill3.setText(rating.getSkill3value()+"/5.00");

        awarerating.setProgress((int)rating.getAwarevalue()*20);
        skill1.setProgress((int)rating.getSkill1value());
        skill4.setProgress((int)rating.getSkill4value());
        skill3.setProgress((int)rating.getSkill3value());
        skill2.setProgress((int)rating.getSkill2value());
        fitnessrating.setProgress((int)rating.getFitvalue()*20) ;
        awarerating.setEnabled(false);
        skill4.setEnabled(false);
        skill1.setEnabled(false);
        skill3.setEnabled(false);
        skill2.setEnabled(false);
        fitnessrating.setEnabled(false);
    }
}
