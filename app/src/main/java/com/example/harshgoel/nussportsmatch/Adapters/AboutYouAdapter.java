package com.example.harshgoel.nussportsmatch.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.harshgoel.nussportsmatch.Chats.chatbar;
import com.example.harshgoel.nussportsmatch.R;

import java.util.List;

/**
 * Created by Harsh Goel on 7/31/2017.
 */

public class AboutYouAdapter extends ArrayAdapter<String> {
    public AboutYouAdapter(Context context, @NonNull List<String> Fields){
        super(context, R.layout.list_aboutyou_itemlayout,Fields);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater viewinflate=LayoutInflater.from(getContext());
        View custom=viewinflate.inflate(R.layout.list_aboutyou_itemlayout,parent,false);
        TextView fieldtype=(TextView)custom.findViewById(R.id.Content);
        TextView fieldvalue=(TextView)custom.findViewById(R.id.value);
        fieldvalue.setText(getItem(position));
        switch (position){
            case 0:
                fieldtype.setText("Email");
                break;
            case 1:
                fieldtype.setText("Address");
                break;
            case 2:
                fieldtype.setText("Faculty");
                break;
            case 3:
                fieldtype.setText("Major");
                break;
            case 4:
                fieldtype.setText("Year");
        }
        return custom;
    }
}
