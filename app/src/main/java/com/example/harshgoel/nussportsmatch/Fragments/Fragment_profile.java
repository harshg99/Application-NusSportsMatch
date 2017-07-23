package com.example.harshgoel.nussportsmatch.Fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.harshgoel.nussportsmatch.Logic.Player;
import com.example.harshgoel.nussportsmatch.Logic.sportsPlayer;
import com.example.harshgoel.nussportsmatch.ProfileDataPackage.EditProfile;
import com.example.harshgoel.nussportsmatch.ProfileDataPackage.handlephoto;
import com.example.harshgoel.nussportsmatch.ProfileDataPackage.setSports;
import com.example.harshgoel.nussportsmatch.R;
import com.example.harshgoel.nussportsmatch.SignIn;
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
 * Created by Harsh Goel on 7/17/2017.
 */

public class Fragment_profile extends Fragment {
    public ImageView profilephoto;
    public Button addphoto;
    public TextView profilename;
    public Button editprofile;
    public Button setsports;
    public Button Logout;
    public Player userplayer;
    public static final int photo=10;
    private FirebaseAuth auth;
    private DatabaseReference userref;
    public Button plays;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View fragment_profile =inflater.inflate(R.layout.activity_profile,container,false);
        profilephoto=(ImageView)fragment_profile.findViewById(R.id.ProfilePhoto);
        final ProgressDialog dialog=new ProgressDialog(getActivity());
        dialog.setMessage("Retrieving...");
        dialog.show();
        auth=FirebaseAuth.getInstance();
        profilename=(TextView)fragment_profile.findViewById(R.id.Name);
        userref= FirebaseDatabase.getInstance().getReference();
        userref=userref.child("users").child(auth.getCurrentUser().getUid());
        userref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                userplayer=dataSnapshot.getValue(Player.class);
                profilename.setText(userplayer.getName());
                if(userplayer.getGender().equals("Male")){
                    profilephoto.setImageDrawable(getResources().getDrawable(R.drawable.male));
                }
                else{
                    profilephoto.setImageDrawable(getResources().getDrawable(R.drawable.female));
                }
                     dialog.cancel();

                Log.d("UserPlayer:",userplayer.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Database Reference:","Read ERROR");
                dialog.cancel();
                AlertDialog.Builder SelectSports=new AlertDialog.Builder(getActivity());
                SelectSports.setTitle("No Internet Connection")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        getActivity().finish();
                    }
                });
                SelectSports.create().show();
            }
        });
        addphoto=(Button)fragment_profile.findViewById(R.id.addphoto);
        editprofile=(Button)fragment_profile.findViewById(R.id.edit_profile);
        setsports=(Button)fragment_profile.findViewById(R.id.selectsports);
        Logout=(Button)fragment_profile.findViewById(R.id.logout);
        plays=(Button)fragment_profile.findViewById(R.id.Plays);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                getActivity().finish();
                Intent intent=new Intent()
                        .setClass(getActivity(),SignIn.class);
                startActivity(intent);
            }
        });
        addphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    getActivity().finish();
                    Intent intent=new Intent()
                            .setClass(getActivity(),handlephoto.class);
                    startActivity(intent);

            }
        });
        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
                Intent intent=new Intent()
                        .setClass(getActivity(), EditProfile.class);
                startActivity(intent);

            }
        });
        setsports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
                Intent intent=new Intent()
                        .setClass(getActivity(), setSports.class);
                startActivity(intent);
            }
        });
        plays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<CharSequence>sportsel=new ArrayList<CharSequence>();
                if(userplayer.getTennis().getisAdded()) {
                    sportsel.add("Tennis");
                }
                if(userplayer.getSquash().getisAdded()) {
                    sportsel.add("Squash");
                }
                if(userplayer.getBadminton().getisAdded()) {
                    sportsel.add("Badminton");
                }
                if(userplayer.getTt().getisAdded()) {
                    sportsel.add("Table Tennis");
                }
                CharSequence[] sports=sportsel.toArray(new CharSequence[sportsel.size()]);
                AlertDialog.Builder SelectSports=new AlertDialog.Builder(getActivity());
                SelectSports.setTitle("Select sports")
                        .setItems(sports, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                    }
                });
                SelectSports.create().show();
            }
        });
        return fragment_profile;
    }

}