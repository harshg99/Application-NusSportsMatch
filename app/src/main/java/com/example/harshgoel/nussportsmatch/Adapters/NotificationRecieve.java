package com.example.harshgoel.nussportsmatch.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harshgoel.nussportsmatch.Chats.chatbar;
import com.example.harshgoel.nussportsmatch.Logic.Game;
import com.example.harshgoel.nussportsmatch.Logic.Player;
import com.example.harshgoel.nussportsmatch.Logic.Request;
import com.example.harshgoel.nussportsmatch.ProfileDataPackage.profileView;
import com.example.harshgoel.nussportsmatch.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Harsh Goel on 7/22/2017.
 */

public class NotificationRecieve extends ArrayAdapter<Request> {
    private DatabaseReference ref;
    private FirebaseAuth auth;
    private  Context context;
    public NotificationRecieve(Context context, List<Request> listreq){
        super(context, R.layout.recieved_requests,listreq);
        ref= FirebaseDatabase.getInstance().getReference();
        auth=FirebaseAuth.getInstance();
        this.context=context;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater viewinflate=LayoutInflater.from(getContext());

        final View custom=viewinflate.inflate(R.layout.recieved_requests,parent,false);
        final Request singlereq=getItem(position);


        final DatabaseReference reference=FirebaseDatabase.getInstance().getReference()
                .child("Request").child(singlereq.RequestID).child("accepted");


        final Button accept=(Button)custom.findViewById(R.id.accept);
        final Button decline=(Button)custom.findViewById(R.id.decline);
        final TextView Textcontent=(TextView)custom.findViewById(R.id.Content);
        final TextView status=(TextView)custom.findViewById(R.id.status);


        if(singlereq.accepted==1){
            accept.setEnabled(false);
            accept.setAlpha(0);
            decline.setAlpha(0);
            decline.setEnabled(false);
            status.setText("Accepted");
            status.setTextColor(context.getResources().getColor(R.color.green));
        }
        else if(singlereq.accepted==2){
            accept.setEnabled(false);
            accept.setAlpha(0);
            decline.setAlpha(0);
            decline.setEnabled(false);
            status.setText("Declined");
            status.setTextColor(context.getResources().getColor(R.color.ltred));
        }

        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("users").child(singlereq.getplayersendID()).child("name");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String namep=(String)dataSnapshot.getValue();
                String content1="Game request from ";
                String content2=namep;
                String content3=" for ";
                String content4=singlereq.Sport;
                String content5=" on ";
                String content6=singlereq.getDate();
                String content7=" at ";
                String content8=singlereq.getTime();
                String content=content1+content2+content3+content4+content5+content6+content7+content8;
                Spannable sb = new SpannableString( content );
                sb.setSpan(new StyleSpan(android.graphics.Typeface.BOLD),content.indexOf(namep), content.indexOf(namep)+ content2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); //bold
                sb.setSpan(new StyleSpan(android.graphics.Typeface.BOLD),content.indexOf(content4), content.indexOf(content4)+ content4.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                sb.setSpan(new StyleSpan(android.graphics.Typeface.BOLD),content.indexOf(content6), content.indexOf(content6)+ content6.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                sb.setSpan(new StyleSpan(android.graphics.Typeface.BOLD),content.indexOf(content8), content.indexOf(content8)+ content8.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                Textcontent.setText(sb);
                setSport(((ImageView)custom.findViewById(R.id.imageView3)),singlereq);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.setValue(Integer.valueOf(1));
                Toast.makeText(getContext(),"Request Accepted",Toast.LENGTH_SHORT).show();
                final DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("chats");
                final chatbar newchatbar1=createchat(singlereq);

                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<chatbar>chatsall=new ArrayList<chatbar>();
                        boolean contains=false;
                        for(DataSnapshot alldata:dataSnapshot.getChildren()){
                            chatbar singlechat=alldata.getValue(chatbar.class);
                            if(checkforchat(singlechat,newchatbar1)){
                                contains=true;
                            }
                        }
                        if(!contains){
                            DatabaseReference refer=ref;
                            refer=refer.push();
                            newchatbar1.setKey(refer.getKey());
                            refer.setValue(newchatbar1);
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                DatabaseReference ref2=FirebaseDatabase.getInstance().getReference().child("game");
                ref2=ref2.push();
                Game newgame=new Game();
                {

                    newgame.setPlayer1id(singlereq.getplayersendID());
                    newgame.setPlayer2id(singlereq.getPlayerrecievedID());
                    newgame.setDatetimemillis(singlereq.getDatetimemillis());
                    newgame.setKey(ref2.getKey());
                    newgame.setDate(singlereq.getDate());
                    newgame.setTime(singlereq.getTime());
                    newgame.setSport(singlereq.Sport);
                    newgame.setReminderplayer1(false);
                    newgame.setRatingotherplayer2(false);
                    newgame.setRatingotherplayer1(false);
                    newgame.setReminderplayer2(false);
                }
                ref2.setValue(newgame);
                accept.setAlpha(0);
                decline.setAlpha(0);
                v.setEnabled(false);
                decline.setEnabled(false);
            }
        });
        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.setValue(Integer.valueOf(2));
                Toast.makeText(getContext(),"Request Declined",Toast.LENGTH_SHORT).show();
                accept.setAlpha(0);
                decline.setAlpha(0);
                v.setEnabled(false);
                accept.setEnabled(false);
            }
        });
        custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,profileView.class);
                intent.putExtra("Player",singlereq.getplayersendID());
                context.startActivity(intent);

            }
        });
        return custom;
    }
    private chatbar createchat(Request singlereq){
        chatbar newchatbar1=new chatbar();
        newchatbar1.setPlayer1(singlereq.namerecieve);
        newchatbar1.setPlayer1id(singlereq.getPlayerrecievedID());
        newchatbar1.setPlayer2id(singlereq.getplayersendID());
        newchatbar1.setPlayer2(singlereq.namesender);
        newchatbar1.setTime(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)+":"+Calendar.getInstance().get(Calendar.MINUTE));
        newchatbar1.setLastmessage("New Chat");
        return newchatbar1;

    }
    private boolean checkforchat(chatbar chat,chatbar newchatbar1){
        return((chat.getPlayer1id().equals(newchatbar1.getPlayer1id())&&
                chat.getPlayer2id().equals(newchatbar1.getPlayer2id()))||(
                chat.getPlayer1id().equals(newchatbar1.getPlayer2id())&&
                        chat.getPlayer2id().equals(newchatbar1.getPlayer1id())));
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
