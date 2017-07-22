package com.example.harshgoel.nussportsmatch.Adapters;

import android.content.Context;
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

import com.example.harshgoel.nussportsmatch.Logic.Player;
import com.example.harshgoel.nussportsmatch.Logic.Request;
import com.example.harshgoel.nussportsmatch.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.util.List;

/**
 * Created by Harsh Goel on 7/22/2017.
 */

public class NotificationRecieve extends ArrayAdapter<Request> {
    private DatabaseReference ref;
    private FirebaseAuth auth;
    public NotificationRecieve(Context context, List<Request> listreq){
        super(context, R.layout.custom_generatematchlist,listreq);
        ref= FirebaseDatabase.getInstance().getReference();
        auth=FirebaseAuth.getInstance();
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
        Button accept=(Button)custom.findViewById(R.id.accept);
        Button decline=(Button)custom.findViewById(R.id.decline);
        if(singlereq.accepted==1){
            custom.setBackgroundColor(Color.WHITE);
            accept.setEnabled(false);
            decline.setEnabled(true);
        }
        else if(singlereq.accepted==2){
            custom.setBackgroundColor(Color.RED);
            accept.setEnabled(true);
            decline.setEnabled(false);
        }

        name.setText(singlereq.namesender);
        netrate.setText(((float)((int)(singlereq.netrating*100))/100)+"");
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.setValue(Integer.valueOf(1));
                Toast.makeText(getContext(),"Request Accepted",Toast.LENGTH_SHORT).show();
                custom.setBackgroundColor(Color.WHITE);
            }
        });
        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.setValue(Integer.valueOf(2));
                Toast.makeText(getContext(),"Request Declined",Toast.LENGTH_SHORT).show();
                custom.setBackgroundColor(Color.RED);
            }
        });
        return custom;
    }

}
