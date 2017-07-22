package com.example.harshgoel.nussportsmatch.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.harshgoel.nussportsmatch.Adapters.NotificationRecieve;
import com.example.harshgoel.nussportsmatch.Logic.Request;
import com.example.harshgoel.nussportsmatch.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Harsh Goel on 7/20/2017.
 */

public class Fragment_notifications extends Fragment{
    public ListView recieve;
    public ListView status;
    DatabaseReference ref;
    FirebaseAuth auth;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View fragment_notifications=inflater.inflate(R.layout.notifications,container,false);
        auth=FirebaseAuth.getInstance();
        ref= FirebaseDatabase.getInstance().getReference();
        ref=ref.child("Request");
        recieve=(ListView)fragment_notifications.findViewById(R.id.listViewsentrequests);
        status=(ListView)fragment_notifications.findViewById(R.id.listViewrecieverequest);
        final ProgressDialog progress=new ProgressDialog(getContext());
        progress.setMessage("Receiving...");
        progress.show();

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Request>requests=new ArrayList<Request>();
                for(DataSnapshot singledata:dataSnapshot.getChildren()){
                    Request singlereq=singledata.getValue(Request.class);
                    if(singlereq.getPlayerrecievedID().equals(auth.getCurrentUser().getUid())) {
                        requests.add(singlereq);
                    }
                }
                recieve.setAdapter(new NotificationRecieve(getContext(),requests));
                progress.cancel();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return fragment_notifications;
    }
}
