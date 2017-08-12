package com.example.harshgoel.nussportsmatch.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.harshgoel.nussportsmatch.Logic.Request;
import com.example.harshgoel.nussportsmatch.ProfileDataPackage.EditContent;
import com.example.harshgoel.nussportsmatch.ProfileDataPackage.EditProfile;
import com.example.harshgoel.nussportsmatch.ProfileDataPackage.EditSecurity;
import com.example.harshgoel.nussportsmatch.R;
import com.example.harshgoel.nussportsmatch.ThreadsForApp.Init_Securitycontent;

import java.util.List;

/**
 * Created by Harsh Goel on 8/1/2017.
 */

public class SecurityAdapter extends ArrayAdapter<String> {
    private Context context;
    private AppCompatActivity activity;
    public SecurityAdapter(AppCompatActivity activity,@NonNull Context context, @NonNull List<String> security) {
        super(context, R.layout.list_profilecontents_parent,security);
        this.context=context;
        this.activity=activity;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater viewinflate=LayoutInflater.from(getContext());
        View custom=viewinflate.inflate(R.layout.list_profilecontents_parent,parent,false);
        final String content=getItem(position);
        TextView contentsec=(TextView)custom.findViewById(R.id.Contenttype);
        Button edit=(Button)custom.findViewById(R.id.Edit);
        contentsec.setText(content);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent().setClass(context, EditSecurity.class);
                i.putExtra("Content",content);
                context.startActivity(i);
                activity.finish();
            }
        });
        return custom;
    }
}
