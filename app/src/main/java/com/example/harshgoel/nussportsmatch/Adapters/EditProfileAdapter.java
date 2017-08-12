package com.example.harshgoel.nussportsmatch.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.harshgoel.nussportsmatch.ProfileDataPackage.EditContent;
import com.example.harshgoel.nussportsmatch.ProfileDataPackage.EditSecurity;
import com.example.harshgoel.nussportsmatch.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Harsh Goel on 8/1/2017.
 */

public class EditProfileAdapter extends BaseExpandableListAdapter {
    Context context;
    Map<String,String> values;
    List<String> header;
    AppCompatActivity activity;
    public EditProfileAdapter(AppCompatActivity activity,Context context, Map<String,String> values, List<String>header){
        this.context=context;
        this.values=values;
        this.header=header;
        this.activity=activity;
    }

    @Override
    public int getGroupCount() {
        return header.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return header.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return values.get(getGroup(groupPosition));
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
        if(convertView==null){
            LayoutInflater inflator=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflator.inflate(R.layout.list_profilecontents_parent,parent,false);
        }
        final TextView content=(TextView)convertView.findViewById(R.id.Contenttype);
        Button editcontent= (Button) convertView.findViewById(R.id.Edit);
        content.setText((String)getGroup(groupPosition));
        editcontent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
                Intent i=new Intent().setClass(context, EditContent.class);
                i.putExtra("Content",content.getText());
                context.startActivity(i);
            }
        });
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if(convertView==null){
            LayoutInflater inflator=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflator.inflate(R.layout.list_profilecontents_child,parent,false);
        }
        TextView content=(TextView)convertView.findViewById(R.id.profilevalue);
        content.setText((String)getChild(groupPosition,childPosition));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
