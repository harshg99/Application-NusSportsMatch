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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.example.harshgoel.nussportsmatch.Adapters.register_new_sportsAdapter;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Harsh Goel on 7/18/2017.
 */

public class setSports extends AppCompatActivity {


    public Toolbar tool;
    private FirebaseAuth auth;
    private DatabaseReference userref;
    public Player userplayer;
    private ListView regunregsportlist;
    private List<sportsPlayer>listofsports;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setsportsnew);
        tool=(Toolbar)findViewById(R.id.set_Bar);
        setSupportActionBar(tool);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        final ProgressDialog dialog=new ProgressDialog(this);
        dialog.setMessage("Retrieving...");
        dialog.show();
        auth=FirebaseAuth.getInstance();
        userref= FirebaseDatabase.getInstance().getReference();
        userref=userref.child("users").child(auth.getCurrentUser().getUid());
        regunregsportlist=(ListView)findViewById(R.id.RegisterListView);
        listofsports=new ArrayList<sportsPlayer>();


        userref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                dialog.cancel();
                userplayer=dataSnapshot.getValue(Player.class);
                Log.d("UserPlayer:",userplayer.toString());
                listofsports.add(userplayer.getTennis());
                listofsports.add(userplayer.getSquash());
                listofsports.add(userplayer.getBadminton());
                listofsports.add(userplayer.getTt());
                regunregsportlist.setAdapter(new register_new_sportsAdapter(setSports.this,setSports.this,listofsports));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Database Reference:","Read ERROR");
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                Intent i=new Intent(this,AppLoginPage.class);
                startActivity(i);
                return true;
            default:return super.onOptionsItemSelected(item);

        }

    }
}
