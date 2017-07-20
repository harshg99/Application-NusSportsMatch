
package com.example.harshgoel.nussportsmatch.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.harshgoel.nussportsmatch.Logic.Player;
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

public class Fragment_match extends Fragment {
    private Spinner sportSpinner;
    private ArrayAdapter<CharSequence> SpinnerAdapter;
    Player[] matchgeneratedplayers;
    public TextView datetext;
    public Button generatematch;
    public TextView timetext;
    public Button dateselect;
    public Button timeselect;
    public ListView matchlist;
    public DatabaseReference dataref;
    public DatabaseReference datarefthis;
    public FirebaseAuth dataauth;
    public Player thisplayer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment_match=inflater.inflate(R.layout.sport_match,container,false);
        final ProgressDialog dialog=new ProgressDialog(getActivity());
        dialog.setMessage("Retrieving...");
        dialog.show();
        dataauth=FirebaseAuth.getInstance();
        dataref=FirebaseDatabase.getInstance().getReference();
        dataref= dataref.child("users");
        datarefthis=FirebaseDatabase.getInstance().getReference();
        datarefthis=datarefthis.child("users").child(dataauth.getCurrentUser().getUid());
        datarefthis.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                thisplayer=dataSnapshot.getValue(Player.class);
                dialog.cancel();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        sportSpinner=(Spinner)fragment_match.findViewById(R.id.SpinnerSportsSelect);
        datetext=(TextView)fragment_match.findViewById(R.id.textView3);
        timetext=(TextView)fragment_match.findViewById(R.id.textView7);
        dateselect=(Button)fragment_match.findViewById(R.id.button2);
        timeselect=(Button)fragment_match.findViewById(R.id.button3);
        matchlist=(ListView)fragment_match.findViewById(R.id.ListMatch);
        generatematch=(Button)fragment_match.findViewById(R.id.generate);

        SpinnerAdapter =
                ArrayAdapter.createFromResource(getActivity(),
                        R.array.SportsOptions,
                        android.R.layout.simple_spinner_item);
        SpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sportSpinner.setAdapter(SpinnerAdapter);
        sportSpinner.setSelection(SpinnerAdapter.getPosition("Select to Sport to Matchup"));

        return fragment_match;
    }
    private Player[] generatematches(){
        List<Player> matches=new ArrayList<Player>();
        dataref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return matches.toArray(new Player[matches.size()]);
    }
}
