package com.example.harshgoel.nussportsmatch.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harshgoel.nussportsmatch.Fragments.Fragment_match;
import com.example.harshgoel.nussportsmatch.Logic.Player;
import com.example.harshgoel.nussportsmatch.Logic.Request;
import com.example.harshgoel.nussportsmatch.ProfileDataPackage.profileView;
import com.example.harshgoel.nussportsmatch.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by Harsh Goel on 7/21/2017.
 */

public class GenerateMatchListAdapter extends ArrayAdapter<Player> {
    public String sport;
    public List<Request> req;
    private FirebaseAuth auth;
    private DatabaseReference ref;
    public Context context;
    public GenerateMatchListAdapter(Context context, Player[] listplayer,String sport,List<Request> req){
        super(context, R.layout.custom_generatematchlist,listplayer);
        this.sport=sport;
        auth=FirebaseAuth.getInstance();
        ref= FirebaseDatabase.getInstance().getReference();
        this.req=req;
        this.context=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater viewinflate=LayoutInflater.from(getContext());
        final View custom=viewinflate.inflate(R.layout.custom_generatematchlist,parent,false);
        final Player singleplayer=getItem(position);
        final Request singlereq=req.get(position);

        TextView name =(TextView) custom.findViewById(R.id.Name);
        TextView netrate= (TextView) custom.findViewById(R.id.NetSkill);


        Button send=(Button)custom.findViewById(R.id.Request);
        name.setText(singleplayer.getName());
        Log.d("Player "+position,singleplayer.toString());
        if(sport.equals("Tennis")) {
            netrate.setText("Net Skill :" +(float)((int)(singleplayer.getTennis().getRating().getRatingNetSkill()*100))/100);
        }
        else if(sport.equals("Squash")) {
            netrate.setText("Net Skill :" +(float)((int)( singleplayer.getSquash().getRating().getRatingNetSkill()*100))/100);
        }
        else if(sport.equals("Badminton")) {
            netrate.setText("Net Skill :" +(float)((int)( singleplayer.getBadminton().getRating().getRatingNetSkill()*100))/100);
        }
        else if(sport.equals("Table Tennis")) {
            netrate.setText("Net Skill :" +(float)((int)( singleplayer.getTt().getRating().getRatingNetSkill()*100))/100);
        }
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(),"Request Sent To "+singleplayer.getName(),Toast.LENGTH_SHORT).show();
                DatabaseReference ref2=FirebaseDatabase.getInstance().getReference();
                ref2=ref2.child("Request").push();
                singlereq.RequestID=ref2.getKey();
                ref2.setValue(singlereq);
                custom.setBackgroundColor(Color.WHITE);
                v.setEnabled(false);
            }
        });
        custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,profileView.class);
                intent.putExtra("Player",singlereq.getPlayerrecievedID());
                context.startActivity(intent);
            }
        });
        return custom;
    }
}
