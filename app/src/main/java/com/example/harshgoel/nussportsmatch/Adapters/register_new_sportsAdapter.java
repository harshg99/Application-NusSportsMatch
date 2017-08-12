package com.example.harshgoel.nussportsmatch.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.harshgoel.nussportsmatch.Logic.sportsPlayer;
import com.example.harshgoel.nussportsmatch.ProfileDataPackage.Questionaire;
import com.example.harshgoel.nussportsmatch.ProfileDataPackage.setSports;
import com.example.harshgoel.nussportsmatch.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Harsh Goel on 8/4/2017.
 */

public class register_new_sportsAdapter extends ArrayAdapter<sportsPlayer> {
    private Context context;
    private AppCompatActivity activity;
    private FirebaseAuth auth;
    private DatabaseReference userref;
    private List<String> sport=new ArrayList<String>();
    private Map<String,ArrayList<String>> sportskills=new HashMap<String, ArrayList<String>>();

    public register_new_sportsAdapter(AppCompatActivity activity, @NonNull Context context, @NonNull List<sportsPlayer> sports) {
        super(context, R.layout.register_for_sports_middle,sports);
        this.context=context;
        this.activity=activity;
        auth=FirebaseAuth.getInstance();
        userref= FirebaseDatabase.getInstance().getReference();
        userref=userref.child("users").child(auth.getCurrentUser().getUid());
        datainit();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater viewinflate=LayoutInflater.from(getContext());
        View custom=viewinflate.inflate(R.layout.register_for_sports_middle,parent,false);
        final sportsPlayer singlesport=getItem(position);

        TextView sporthead=(TextView)custom.findViewById(R.id.HeadingSport);
        Button reg=(Button)custom.findViewById(R.id.Register);
        Button unreg=(Button)custom.findViewById(R.id.UnRegister);

        sporthead.setText(sport.get(position));
        if(singlesport.getisAdded()){
            reg.setEnabled(false);
            unreg.setEnabled(true);
            reg.setAlpha(0.1f);
        }
        else{
            reg.setEnabled(true);
            unreg.setEnabled(false);
            unreg.setAlpha(0.1f);
        }


        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(singlesport.isQuestionaireCompleted()){
                    userref.child(sport.get(position).toLowerCase()).child("isAdded").setValue(true);
                }
                else{
                    userref.child(sport.get(position).toLowerCase()).child("isAdded").setValue(true);
                    activity.finish();
                    Intent intent = new Intent().setClass(context, Questionaire.class);
                    intent.putExtra("sport",sport.get(position).toLowerCase());
                    intent.putExtra("Skills",sportskills.get(sport.get(position)));
                    context.startActivity(intent);
                }
            }
        });
        unreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userref.child(sport.get(position).toLowerCase()).child("isAdded").setValue(false);
            }
        });



        return custom;
    }
    private void datainit(){

        sport.add("Tennis");sport.add("Squash");sport.add("Badminton");sport.add("TT");

        String skillt[]={"Backhand","Forehand","Serve","Smash"};
        String skilltt[]={"Backhand Drive","Forehand Drive","Backhand Push","Forehand Push"};
        String skillb[]={"Clear","Drop","Smash","Drive"};
        String skills[]={"Drive","Drop","Boast","Lob"};

        ArrayList<String>tennisskill=new ArrayList<String>(Arrays.asList(skillt));
        ArrayList<String>squashskill=new ArrayList<String>(Arrays.asList(skills));
        ArrayList<String>ttskill=new ArrayList<String>(Arrays.asList(skilltt));
        ArrayList<String>badmintonskill=new ArrayList<String>(Arrays.asList(skillb));

        sportskills.put("Tennis",tennisskill);
        sportskills.put("Squash",squashskill);
        sportskills.put("Badminton",badmintonskill);
        sportskills.put("TT",ttskill);
    }
}
