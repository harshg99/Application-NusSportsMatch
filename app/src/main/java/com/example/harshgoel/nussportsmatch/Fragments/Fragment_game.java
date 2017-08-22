package com.example.harshgoel.nussportsmatch.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.harshgoel.nussportsmatch.Adapters.GameAdapter;
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
 * Created by Harsh Goel on 7/16/2017.
 */

public class Fragment_game extends Fragment{
    private DatabaseReference ref;
    private FirebaseAuth auth;
    private ListView listview;
    private List<Game> games;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View custom=inflater.inflate(R.layout.activity_calendar,container,false);

        ref= FirebaseDatabase.getInstance().getReference().child("game");
        auth=FirebaseAuth.getInstance();
        listview=(ListView)custom.findViewById(R.id.games_list);

        final String thisuid=auth.getCurrentUser().getUid();


        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                games=new ArrayList<Game>();
                for(DataSnapshot singledata:dataSnapshot.getChildren()) {
                    Game singlegame=singledata.getValue(Game.class);
                    if(singlegame!=null){
                        if(singlegame.getPlayer1id()!=null || singlegame.getPlayer2id()!=null) {
                            if (singlegame.getPlayer1id().equals(thisuid)
                                    ||singlegame.getPlayer2id().equals(thisuid)) {
                                games.add(singlegame);
                            }
                        }
                    }
                }

                if(getActivity()!=null){
                    listview.setAdapter(new GameAdapter(getContext(),games,getActivity()));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return custom;
    }
}
