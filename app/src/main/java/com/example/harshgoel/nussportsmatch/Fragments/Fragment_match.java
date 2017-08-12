
package com.example.harshgoel.nussportsmatch.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.harshgoel.nussportsmatch.Adapters.GenerateMatchListAdapter;
import com.example.harshgoel.nussportsmatch.Logic.Player;
import com.example.harshgoel.nussportsmatch.Logic.Rating;
import com.example.harshgoel.nussportsmatch.Logic.Request;
import com.example.harshgoel.nussportsmatch.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Harsh Goel on 7/16/2017.
 */

public class Fragment_match extends Fragment {
    private Spinner sportSpinner;
    private ArrayAdapter<CharSequence> SpinnerAdapter;
    private Player[] matchgeneratedplayers;
    private List<Request> requestgenerate;
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
    private class date{
        public int year;
        public int month;
        public int day;

        @Override
        public String toString() {
            return (day+"\\"+month+"\\"+year);
        }
    }
    private class time{
        public int hour;
        public int minute;

        @Override
        public String toString() {
            return (hour+":"+minute);
        }
    }
    private date selectdate;
    private time selecttime;
    private int sportSelect;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment_match=inflater.inflate(R.layout.sport_match,container,false);
        final ProgressDialog dialog=new ProgressDialog(getActivity());
        dialog.setMessage("Retrieving...");
        requestgenerate=new ArrayList<Request>();
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
                if(!thisplayer.getTennis().getisAdded()&&!thisplayer.getBadminton().getisAdded()&&!thisplayer.getSquash().getisAdded()
                        &&!thisplayer.getTt().getisAdded()){
                    sportSpinner.setEnabled(false);
                    generatematch.setEnabled(false);
                    Toast.makeText(getActivity(),"No Sport registered for",Toast.LENGTH_LONG);
                }
                else
                {
                    sportSpinner.setEnabled(true);
                    generatematch.setEnabled(true);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
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
        selectdate=new date();
        selecttime=new time();

        sportSpinner=(Spinner)fragment_match.findViewById(R.id.SpinnerSportsSelect);
        datetext=(TextView)fragment_match.findViewById(R.id.textView3);
        timetext=(TextView)fragment_match.findViewById(R.id.textView7);
        dateselect=(Button)fragment_match.findViewById(R.id.button2);
        timeselect=(Button)fragment_match.findViewById(R.id.button3);
        matchlist=(ListView)fragment_match.findViewById(R.id.ListMatch);
        generatematch=(Button)fragment_match.findViewById(R.id.generate);

        matchlist.setAdapter(null);
        SpinnerAdapter =
                ArrayAdapter.createFromResource(getActivity(),
                        R.array.SportsOptions,
                        android.R.layout.simple_spinner_item);
        SpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sportSpinner.setAdapter(SpinnerAdapter);
        sportSpinner.setSelection(SpinnerAdapter.getPosition("Select to Sport to Matchup"));
        generatematch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checksport=true;
                sportSelect = Arrays.asList(getResources().getStringArray(R.array.SportsOptions))
                        .indexOf(sportSpinner.getSelectedItem().toString());
                switch (sportSelect){
                    case 0:{
                        if(!thisplayer.getTennis().getisAdded()) {
                            checksport = false;
                        }
                        break;
                    }
                    case 1:{
                        if(!thisplayer.getBadminton().getisAdded()) {
                            checksport = false;
                        }
                        break;
                    }
                    case 2:{
                        if(!thisplayer.getSquash().getisAdded()) {
                            checksport = false;
                        }
                        break;
                    }
                    case 3:{
                        if(!thisplayer.getTt().getisAdded()) {
                            checksport = false;
                        }
                        break;
                    }
                }
                if(checksport) {
                    generatematches();
                }
                else{
                    AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                    alert.setMessage("Sport not registered")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    alert.create().show();
                }
            }
        });
        dateselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c= Calendar.getInstance();
                selectdate.day=c.get(Calendar.DAY_OF_MONTH);
                selectdate.month=c.get(Calendar.MONTH);
                selectdate.year=c.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog=new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        selectdate.day=dayOfMonth;
                        selectdate.month=month;
                        selectdate.year=year;
                        datetext.setText("Date: "+selectdate.toString());
                    }
                },selectdate.year,selectdate.month,selectdate.day);
                datePickerDialog.show();
            }
        });
        timeselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c= Calendar.getInstance();
                selecttime.hour=c.get(Calendar.HOUR_OF_DAY);
                selecttime.minute=c.get(Calendar.MINUTE);
                TimePickerDialog datePickerDialog=new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        selecttime.hour=hourOfDay;
                        selecttime.minute=minute;
                        timetext.setText("Time: "+selecttime.toString());
                    }
                },selecttime.hour,selecttime.minute, true);
                datePickerDialog.show();
            }
        });

        return fragment_match;
    }

    private void generatematches() {
        final List<Player> matches = new ArrayList<Player>();
        sportSelect = Arrays.asList(getResources().getStringArray(R.array.SportsOptions))
                .indexOf(sportSpinner.getSelectedItem().toString());

        if (datetext.getText().equals("Date") || timetext.getText().equals("Time")) {
            AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
            alert.setMessage("Please set Date and Time")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            alert.create().show();
        } else if (sportSelect == 4) {
            AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
            alert.setMessage("No Sport Selected")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            alert.create().show();
            matchgeneratedplayers = null;
        } else {
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Generating..");
            progressDialog.show();
            dataref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.d("Count: ", "" + dataSnapshot.getChildrenCount());
                    for (DataSnapshot players : dataSnapshot.getChildren()) {
                        Player otheruser = players.getValue(Player.class);
                        Log.d("User:", otheruser.toString());
                        if (!otheruser.toString().equals(thisplayer.toString())) {
                            if (otheruser.getGender().equals(thisplayer.getGender())) {
                                Rating otherUserRating;
                                Rating thisUserRating;
                                Request newreq = new Request();
                                newreq.setDate((String) datetext.getText());
                                newreq.setTime((String) timetext.getText());
                                newreq.setPlayersendID(thisplayer.getUserID());
                                newreq.setPlayerrecievedID(otheruser.getUserID());
                                newreq.namesender = thisplayer.getName();
                                newreq.namerecieve = otheruser.getName();
                                newreq.Sport = sportSpinner.getSelectedItem().toString();
                                newreq.accepted = 0;
                                switch (sportSelect) {
                                    case 0: {
                                        otherUserRating = otheruser.getTennis().getRating();
                                        thisUserRating = thisplayer.getTennis().getRating();
                                        newreq.netrating = thisplayer.getTennis().getRating().getRatingNetSkill();
                                        break;
                                    }
                                    case 1: {
                                        otherUserRating = otheruser.getBadminton().getRating();
                                        thisUserRating = thisplayer.getBadminton().getRating();
                                        newreq.netrating = thisplayer.getBadminton().getRating().getRatingNetSkill();
                                        break;
                                    }
                                    case 2: {
                                        otherUserRating = otheruser.getSquash().getRating();
                                        thisUserRating = thisplayer.getSquash().getRating();
                                        newreq.netrating = thisplayer.getSquash().getRating().getRatingNetSkill();
                                        break;
                                    }
                                    case 3: {
                                        otherUserRating = otheruser.getTt().getRating();
                                        thisUserRating = thisplayer.getTt().getRating();
                                        newreq.netrating = thisplayer.getTt().getRating().getRatingNetSkill();
                                        break;
                                    }
                                    default: {
                                        otherUserRating = thisUserRating = null;
                                    }
                                }
                                if (otherUserRating != null && thisUserRating != null) {

                                    if (Math.abs(otherUserRating.getRatingNetSkill() - thisUserRating.getRatingNetSkill()) <= 0.2
                                            && Math.abs(otherUserRating.getRatingSkill() - thisUserRating.getRatingSkill()) <= 0.5) {
                                        matches.add(otheruser);
                                        requestgenerate.add(newreq);
                                    }
                                }

                            }
                        }
                    }
                    if (matches.size() != 0) {
                        matchgeneratedplayers = matches.toArray(new Player[matches.size()]);
                        matchlist.setAdapter(new GenerateMatchListAdapter(getActivity(), matchgeneratedplayers,
                                sportSpinner.getSelectedItem().toString(), requestgenerate));
                        progressDialog.cancel();
                    } else {
                        Toast.makeText(getContext(), "Could not generate matches for the sport.", Toast.LENGTH_LONG).show();
                        progressDialog.cancel();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    progressDialog.cancel();
                    AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                    alert.setMessage("Cannot Connect to the internet")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    alert.create().show();


                }
            });
            Log.d("Matches", "" + matches.size());

        }

    }
    public class checkfields{
        private boolean flagthis;
        private boolean flaggender;
        private boolean flagrating;
        public boolean isFlagthis(){
            return flagthis;
        }

        public boolean isFlaggender() {
            return flaggender;
        }

        public boolean isFlagrating() {
            return flagrating;
        }

        public void setFlaggender(boolean flaggender) {
            this.flaggender = flaggender;
        }

        public void setFlagrating(boolean flagrating) {
            this.flagrating = flagrating;
        }

        public void setFlagthis(boolean flagthis) {
            this.flagthis = flagthis;
        }
    }
}
