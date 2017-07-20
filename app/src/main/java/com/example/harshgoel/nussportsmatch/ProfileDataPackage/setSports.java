package com.example.harshgoel.nussportsmatch.ProfileDataPackage;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.harshgoel.nussportsmatch.AppLoginPage;
import com.example.harshgoel.nussportsmatch.Logic.Player;
import com.example.harshgoel.nussportsmatch.Logic.sportsPlayer;
import com.example.harshgoel.nussportsmatch.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Harsh Goel on 7/18/2017.
 */

public class setSports extends AppCompatActivity {

    public CheckBox tennisbox;
    public CheckBox Badmintonbox;
    public CheckBox squashbox;
    public CheckBox ttbox;
    public Button confirmBUT;
    private FirebaseAuth auth;
    private DatabaseReference userref;
    public Player userplayer;
    private int tenniscomp;
    private int ttcomp;
    private int badcomp;
    private int squashcomp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_sports);
        confirmBUT=(Button)findViewById(R.id.Confirm);
        tennisbox=(CheckBox)findViewById(R.id.Tennis);
        Badmintonbox=(CheckBox)findViewById(R.id.badminton);
        ttbox=(CheckBox)findViewById(R.id.TableTennis);
        tenniscomp=0;
        ttcomp=0;
        badcomp=0;
        squashcomp=0;
        squashbox=(CheckBox)findViewById(R.id.Squash);
        final ProgressDialog dialog=new ProgressDialog(this);
        dialog.setMessage("Retrieving...");
        dialog.show();
        auth=FirebaseAuth.getInstance();
        userref= FirebaseDatabase.getInstance().getReference();
        userref=userref.child("users").child(auth.getCurrentUser().getUid());
        userref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                dialog.cancel();
                userplayer=dataSnapshot.getValue(Player.class);
                Log.d("UserPlayer:",userplayer.toString());
                if(userplayer.getTennis().getisAdded()){
                    tennisbox.setChecked(true);
                }
                if(userplayer.getSquash().getisAdded()){
                    squashbox.setChecked(true);
                }
                if(userplayer.getBadminton().getisAdded()){
                    Badmintonbox.setChecked(true);
                }
                if(userplayer.getTt().getisAdded()){
                    ttbox.setChecked(true);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Database Reference:","Read ERROR");
            }
        });

    }
    public void ConfirmSports(View v) {
        if (tennisbox.isChecked())
            userplayer.getTennis().setAdded(true);
        else
            userplayer.getTennis().setAdded(false);
        if (squashbox.isChecked())
            userplayer.getSquash().setAdded(true);
        else
            userplayer.getSquash().setAdded(false);
        if (ttbox.isChecked())
            userplayer.getTt().setAdded(true);
        else
            userplayer.getTt().setAdded(false);
        if (Badmintonbox.isChecked())
            userplayer.getBadminton().setAdded(true);
        else
            userplayer.getBadminton().setAdded(false);

        userref.child("tennis").child("isAdded").setValue(userplayer.getTennis().getisAdded());
        userref.child("squash").child("isAdded").setValue(userplayer.getSquash().getisAdded());
        userref.child("badminton").child("isAdded").setValue(userplayer.getBadminton().getisAdded());
        userref.child("tt").child("isAdded").setValue(userplayer.getTt().getisAdded());
        if ((!userplayer.getTennis().isQuestionaireCompleted()&&userplayer.getTennis().getisAdded())
                || (!userplayer.getBadminton().isQuestionaireCompleted()&&userplayer.getBadminton().getisAdded())
                || (!userplayer.getSquash().isQuestionaireCompleted()&&userplayer.getSquash().getisAdded())
                || (!userplayer.getTt().isQuestionaireCompleted()&&userplayer.getTt().getisAdded())) {
            this.finish();
            Intent intent = new Intent().setClass(setSports.this, Questionaire.class);
            startActivity(intent);
        } else {
            this.finish();
            Intent intent = new Intent().setClass(this, AppLoginPage.class);
            startActivity(intent);
        }
    }

}
