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

import com.example.harshgoel.nussportsmatch.Chats.chatbar;
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
        super(context, R.layout.request_recieved,listreq);
        ref= FirebaseDatabase.getInstance().getReference();
        auth=FirebaseAuth.getInstance();
        this.context=context;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater viewinflate=LayoutInflater.from(getContext());

        final View custom=viewinflate.inflate(R.layout.request_recieved,parent,false);
        final Request singlereq=getItem(position);
        final TextView name =(TextView) custom.findViewById(R.id.Name);
        TextView Date =(TextView) custom.findViewById(R.id.date);
        Date.setText(singlereq.getDate());
        TextView sporttext =(TextView) custom.findViewById(R.id.Sport);
        sporttext.setText(singlereq.Sport);
        TextView Time=(TextView) custom.findViewById(R.id.time);
        Time.setText(singlereq.getTime());
        final TextView netrate= (TextView) custom.findViewById(R.id.NetSkill);

        final DatabaseReference reference=FirebaseDatabase.getInstance().getReference()
                .child("Request").child(singlereq.RequestID).child("accepted");
        final Button accept=(Button)custom.findViewById(R.id.accept);
        final Button decline=(Button)custom.findViewById(R.id.decline);
        if(singlereq.accepted==1){
            custom.setBackgroundColor(Color.WHITE);
            accept.setEnabled(false);
            decline.setEnabled(false);
        }
        else if(singlereq.accepted==2){
            custom.setBackgroundColor(Color.RED);
            accept.setEnabled(false);
            decline.setEnabled(false);
        }
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("users").child(singlereq.getplayersendID()).child("name");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String namep=(String)dataSnapshot.getValue();
                name.setText(namep);
                netrate.setText(((float)((int)(singlereq.netrating*100))/100)+"");
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
                custom.setBackgroundColor(Color.WHITE);
                final DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("chats");
                final chatbar newchatbar1=new chatbar();
                newchatbar1.setSenderofmessagename(singlereq.namerecieve);
                newchatbar1.setSenderofmessageid(singlereq.getPlayerrecievedID());
                newchatbar1.setGetRecieverofmessageid(singlereq.getplayersendID());
                newchatbar1.setRecieverofmessage(singlereq.namesender);
                newchatbar1.setTime(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)+":"+Calendar.getInstance().get(Calendar.MINUTE));
                newchatbar1.setLastmessage("New Chat");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<chatbar>chatsall=new ArrayList<chatbar>();
                        boolean contains=false;
                        for(DataSnapshot alldata:dataSnapshot.getChildren()){
                            chatbar singlechat=alldata.getValue(chatbar.class);
                            if(singlechat.getGetRecieverofmessageid().equals(newchatbar1.getGetRecieverofmessageid())&&
                                    singlechat.getSenderofmessageid().equals(newchatbar1.getSenderofmessageid())){
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

                v.setEnabled(false);
                decline.setEnabled(false);
            }
        });
        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.setValue(Integer.valueOf(2));
                Toast.makeText(getContext(),"Request Declined",Toast.LENGTH_SHORT).show();
                custom.setBackgroundColor(Color.RED);
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


}
