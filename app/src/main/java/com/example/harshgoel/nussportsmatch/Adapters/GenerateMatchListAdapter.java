package com.example.harshgoel.nussportsmatch.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harshgoel.nussportsmatch.Logic.Player;
import com.example.harshgoel.nussportsmatch.R;

/**
 * Created by Harsh Goel on 7/21/2017.
 */

class GenerateMatchListAdapter extends ArrayAdapter<Player> {
    public String sport;
    GenerateMatchListAdapter(Context context, Player[] listplayer,String sport){
        super(context, R.layout.custom_generatematchlist,listplayer);
        this.sport=sport;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater viewinflate=LayoutInflater.from(getContext());
        View custom=viewinflate.inflate(R.layout.custom_generatematchlist,parent,false);
        final Player singleplayer=getItem(position);
        TextView name =(TextView) custom.findViewById(R.id.Name);
        TextView fitrate =(TextView) custom.findViewById(R.id.Fitness);
        TextView awarerate=(TextView) custom.findViewById(R.id.Aware);
        TextView netrate= (TextView) custom.findViewById(R.id.NetSkill);
        TextView skillrate= (TextView) custom.findViewById(R.id.Skill);
        Button send=(Button)custom.findViewById(R.id.Request);
        name.setText(singleplayer.getName());
        if(sport.equals("tennis")) {
            fitrate.setText("Fitness :" + singleplayer.getTennis().getRating().getRatingFitness());
            netrate.setText("Net Skill :" + singleplayer.getTennis().getRating().getRatingNetSkill());
            awarerate.setText("Fitness :" + singleplayer.getTennis().getRating().getRatingAwareness());
            skillrate.setText("Skill :" + singleplayer.getTennis().getRating().getRatingSkill());
        }
        else if(sport.equals("squash")) {
            fitrate.setText("Fitness :" + singleplayer.getSquash().getRating().getRatingFitness());
            netrate.setText("Net Skill :" + singleplayer.getSquash().getRating().getRatingNetSkill());
            awarerate.setText("Fitness :" + singleplayer.getSquash().getRating().getRatingAwareness());
            skillrate.setText("Skill :" + singleplayer.getSquash().getRating().getRatingSkill());
        }
        else if(sport.equals("badminton")) {
            fitrate.setText("Fitness :" + singleplayer.getBadminton().getRating().getRatingFitness());
            netrate.setText("Net Skill :" + singleplayer.getBadminton().getRating().getRatingNetSkill());
            awarerate.setText("Fitness :" + singleplayer.getBadminton().getRating().getRatingAwareness());
            skillrate.setText("Skill :" + singleplayer.getBadminton().getRating().getRatingSkill());
        }
        else if(sport.equals("tabletennis")) {
            fitrate.setText("Fitness :" + singleplayer.getTt().getRating().getRatingFitness());
            netrate.setText("Net Skill :" + singleplayer.getTt().getRating().getRatingNetSkill());
            awarerate.setText("Fitness :" + singleplayer.getTt().getRating().getRatingAwareness());
            skillrate.setText("Skill :" + singleplayer.getTt().getRating().getRatingSkill());
        }
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Request Sent To "+singleplayer.getName(),Toast.LENGTH_SHORT).show();
            }
        });
        return custom;
    }
}
