package com.example.harshgoel.nussportsmatch.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.harshgoel.nussportsmatch.Logic.sportsPlayer;
import com.example.harshgoel.nussportsmatch.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Harsh Goel on 7/31/2017.
 */

public class SportsRegAdapter extends BaseExpandableListAdapter {
    Context context;
    Map<String,sportsPlayer>values ;
    List<String>heads;
    public SportsRegAdapter(Context context, Map<String,sportsPlayer>values , List<String>heads)
    {
        this.context=context;
        this.values=values;
        this.heads=heads;
    }
    @Override
    public int getGroupCount() {
        return heads.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return heads.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return values.get(heads.get(groupPosition));
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if(groupPosition==1){
            isExpanded=true;
        }
        if(convertView==null){
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.list_parent_sportsreg_layout,parent,false);
        }
        TextView sport= (TextView) convertView.findViewById(R.id.HeadingSport);
        Boolean status=((sportsPlayer)getChild(groupPosition,0)).getisAdded();
        TextView statustext= (TextView) convertView.findViewById(R.id.Status);
        sport.setText(heads.get(groupPosition));
        if(status){
            statustext.setText("Registered");
            statustext.setTextColor(Color.CYAN);
        }
        else
        {
            statustext.setText("UnRegistered");
            statustext.setTextColor(Color.RED);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        sportsPlayer childsport= (sportsPlayer) getChild(groupPosition,childPosition);
        if(convertView==null){
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.list_child_sportsreg_layout,parent,false);
        }
        TextView ratingga= (TextView) convertView.findViewById(R.id.ratingga);
        TextView ratingf= (TextView) convertView.findViewById(R.id.ratingf);
        TextView ratings= (TextView) convertView.findViewById(R.id.ratings);
        ratingga.setText((float)((int)(childsport.getRating().getRatingAwareness()*100))/100+"");
        ratingf.setText((float)((int)(childsport.getRating().getRatingFitness()*100))/100+"");
        ratings.setText((float)((int)(childsport.getRating().getRatingSkill()*100))/100+"");
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
