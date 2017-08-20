package com.example.harshgoel.nussportsmatch.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.harshgoel.nussportsmatch.Logic.Request;
import com.example.harshgoel.nussportsmatch.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by Harsh Goel on 7/22/2017.
 */

public class NotificationSent extends ArrayAdapter<Request> {
    private Context context;
    public NotificationSent(@NonNull Context context, @NonNull List<Request> Requests) {
        super(context, R.layout.request_sent2,Requests);
        this.context=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater viewinflate=LayoutInflater.from(getContext());
        final View custom=viewinflate.inflate(R.layout.request_sent2,parent,false);
        final Request singlereq=getItem(position);
        final ImageView sport =(ImageView) custom.findViewById(R.id.sport);
        final TextView textcontent=(TextView) custom.findViewById(R.id.CONTENT);

        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("users").child(singlereq.getplayersendID()).child("name");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String namep=(String)dataSnapshot.getValue();
                String content1="Game request to ";
                String content2=namep;
                String content3=" for ";
                String content4=singlereq.Sport;
                String content5=" on ";
                String content6=singlereq.getDate();
                String content7=" at ";
                String content8=singlereq.getTime();
                String content10=" is ";
                String content9;
                if(singlereq.accepted==0){
                    content9="Pending";
                    custom.setBackgroundColor(context.getResources().getColor(R.color.ltltgrey));
                }
                else if(singlereq.accepted==1){
                    content9="Accepted";
                    custom.setBackgroundColor(context.getResources().getColor(R.color.ltgreen));
                }
                else{
                    content9="Declined";
                    custom.setBackgroundColor(context.getResources().getColor(R.color.ltred));
                }
                String content=content1+content2+content3+content4+content5+content6+content7+content8+content10+content9;
                Spannable sb = new SpannableString( content );
                sb.setSpan(new StyleSpan(android.graphics.Typeface.BOLD),content.indexOf(namep), content.indexOf(namep)+ content2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); //bold
                sb.setSpan(new StyleSpan(android.graphics.Typeface.BOLD),content.indexOf(content4), content.indexOf(content4)+ content4.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                sb.setSpan(new StyleSpan(android.graphics.Typeface.BOLD),content.indexOf(content6), content.indexOf(content6)+ content6.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                sb.setSpan(new StyleSpan(android.graphics.Typeface.BOLD),content.indexOf(content8), content.indexOf(content8)+ content8.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                sb.setSpan(new StyleSpan(android.graphics.Typeface.BOLD),content.indexOf(content9), content.indexOf(content9)+ content9.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                textcontent.setText(sb);
                setSport(sport,singlereq);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return custom;
    }
    private void setSport(ImageView image,Request singlereq){
        if(singlereq.Sport.equals("Tennis")){
            image.setImageDrawable(context.getResources().getDrawable(R.drawable.tennis));
            image.setScaleType(ImageView.ScaleType.FIT_XY);
        }
        if(singlereq.Sport.equals("Badminton")){
            image.setImageDrawable(context.getResources().getDrawable(R.drawable.badminton));
            image.setScaleType(ImageView.ScaleType.FIT_XY);
        }
        if(singlereq.Sport.equals("Table Tennis")){
            image.setImageDrawable(context.getResources().getDrawable(R.drawable.tt));
            image.setScaleType(ImageView.ScaleType.FIT_XY);
        }
        if(singlereq.Sport.equals("Squash")){
            image.setImageDrawable(context.getResources().getDrawable(R.drawable.squash));
            image.setScaleType(ImageView.ScaleType.FIT_XY);
        }

    }
}
