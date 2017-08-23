package com.example.harshgoel.nussportsmatch.Rate_Others;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.harshgoel.nussportsmatch.AppLoginPage;
import com.example.harshgoel.nussportsmatch.Logic.Game;
import com.example.harshgoel.nussportsmatch.Logic.ratings;
import com.example.harshgoel.nussportsmatch.ProfileDataPackage.handlephoto;
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

        Toolbar k=(Toolbar)findViewById(R.id.q_bar);
        k.setTitle(sport.toUpperCase());
        setSupportActionBar(k);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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
                if(dataref!=null) {
                    dataref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            ratings rating = dataSnapshot.getValue(ratings.class);
                            if(rating!=null) {
                                setFields(rating);
                            }
                            else{
                                AlertDialog.Builder notice=new AlertDialog.Builder(ViewRate.this);
                                notice.setTitle("Guide");
                                notice.setMessage(" You have not been rated yet.")
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        });
                                notice.create().show();
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

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
        skill1.setProgress((int)rating.getSkill1value()*20);
        skill4.setProgress((int)rating.getSkill4value()*20);
        skill3.setProgress((int)rating.getSkill3value()*20);
        skill2.setProgress((int)rating.getSkill2value()*20);
        fitnessrating.setProgress((int)rating.getFitvalue()*20) ;
        awarerating.setEnabled(false);
        skill4.setEnabled(false);
        skill1.setEnabled(false);
        skill3.setEnabled(false);
        skill2.setEnabled(false);
        fitnessrating.setEnabled(false);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                Intent c = new Intent(ViewRate.this, AppLoginPage.class);
                startActivity(c);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
