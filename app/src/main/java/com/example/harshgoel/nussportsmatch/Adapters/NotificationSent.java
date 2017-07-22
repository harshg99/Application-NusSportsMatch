package com.example.harshgoel.nussportsmatch.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.harshgoel.nussportsmatch.Logic.Request;
import com.example.harshgoel.nussportsmatch.R;

import java.util.List;

/**
 * Created by Harsh Goel on 7/22/2017.
 */

public class NotificationSent extends ArrayAdapter<Request> {
    public NotificationSent(@NonNull Context context, @NonNull List<Request> Requests) {
        super(context, R.layout.request_sent,Requests);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater viewinflate=LayoutInflater.from(getContext());
        final View custom=viewinflate.inflate(R.layout.request_sent,parent,false);
        final Request singlereq=getItem(position);
        final TextView name =(TextView) custom.findViewById(R.id.Name);
        name.setText(singlereq.namerecieve);
        TextView Date =(TextView) custom.findViewById(R.id.date);
        Date.setText(singlereq.getDate());
        TextView sporttext =(TextView) custom.findViewById(R.id.Sport);
        sporttext.setText(singlereq.Sport);
        TextView Time=(TextView) custom.findViewById(R.id.time);
        Time.setText(singlereq.getTime());
        TextView state=(TextView) custom.findViewById(R.id.Status);
        if(singlereq.accepted==0){
            state.setText("Decision Pending");
            custom.setBackgroundColor(Color.LTGRAY);
        }
        else if(singlereq.accepted==1){
            state.setText("Accepted");
            custom.setBackgroundColor(Color.GREEN);
        }
        else{
            state.setText("Declined");
            custom.setBackgroundColor(Color.RED);
        }
        return custom;
    }
}
