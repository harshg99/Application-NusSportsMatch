package com.example.harshgoel.nussportsmatch.Fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.harshgoel.nussportsmatch.Logic.Player;
import com.example.harshgoel.nussportsmatch.ProfileDataPackage.EditProfile;
import com.example.harshgoel.nussportsmatch.ProfileDataPackage.handlephoto;
import com.example.harshgoel.nussportsmatch.ProfileDataPackage.setSports;
import com.example.harshgoel.nussportsmatch.R;
import com.example.harshgoel.nussportsmatch.ThreadsForApp.Init_Sports_Background;
import com.example.harshgoel.nussportsmatch.ThreadsForApp.Init_aboutyou_background;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Harsh Goel on 7/17/2017.
 */

public class Fragment_profile extends Fragment {
    //
    public ImageView profilephoto;
    public Button addphoto;
    public TextView profilename;
    public Button editprofile;
    public Button setsports;
    public Player userplayer;
    public static final int photo=10;
    private FirebaseAuth auth;
    private DatabaseReference userref;
    private ExpandableListView sportsreg;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       final  View fragment_profile =inflater.inflate(R.layout.activity_profile,container,false);
        profilephoto=(ImageView)fragment_profile.findViewById(R.id.ProfilePhoto);
        final ProgressDialog dialog=new ProgressDialog(getActivity());
        dialog.setMessage("Retrieving...");
        dialog.show();
        auth=FirebaseAuth.getInstance();
        sportsreg=(ExpandableListView)fragment_profile.findViewById(R.id.SportsList);
        profilename=(TextView)fragment_profile.findViewById(R.id.Name);
        userref= FirebaseDatabase.getInstance().getReference();
        userref=userref.child("users").child(auth.getCurrentUser().getUid());
        userref.keepSynced(true);

        userref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                userplayer=dataSnapshot.getValue(Player.class);
                profilename.setText(userplayer.getName());
                if(userplayer.getGender().equals("Male")){
                    profilephoto.setImageDrawable(getResources().getDrawable(R.drawable.male));
                    profilephoto.setScaleType(ImageView.ScaleType.FIT_XY);
                }
                else{
                    profilephoto.setImageDrawable(getResources().getDrawable(R.drawable.female));
                }
                new Init_aboutyou_background(getActivity(),getContext(),R.id.whoareyoulayout,userplayer)
                        .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                new Init_Sports_Background(getContext(),dialog,sportsreg,userplayer)
                        .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


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



        addphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent=new Intent()
                            .setClass(getActivity(),handlephoto.class);
                    startActivity(intent);

            }
        });
        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent()
                        .setClass(getActivity(), EditProfile.class);
                startActivity(intent);

            }
        });
        setsports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent()
                        .setClass(getActivity(), setSports.class);
                startActivity(intent);
            }
        });

        return fragment_profile;

    }
}