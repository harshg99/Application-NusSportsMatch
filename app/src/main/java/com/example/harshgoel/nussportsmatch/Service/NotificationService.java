package com.example.harshgoel.nussportsmatch.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import com.example.harshgoel.nussportsmatch.Adapters.NotificationRecieve;
import com.example.harshgoel.nussportsmatch.AppLoginPage;
import com.example.harshgoel.nussportsmatch.Logic.Request;
import com.example.harshgoel.nussportsmatch.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.content.ContentValues.TAG;

/**
 * Created by harsh on 17/08/2017.
 */

public class NotificationService extends Service {
    private DatabaseReference ref;
    private String accountuid;

    @Override
    public IBinder onBind(Intent arg0)
    {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Log.e(TAG, "onStartCommand");
        super.onStartCommand(intent, flags, startId);
        accountuid=intent.getStringExtra("Account");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Request newreq=dataSnapshot.getValue(Request.class);
                String name=newreq.namesender;
                if(newreq.getPlayerrecievedID().equals(accountuid)){
                    notification(name);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return START_STICKY;
    }


    @Override
    public void onCreate(){
        Log.e(TAG, "onCreate");
        ref=FirebaseDatabase.getInstance().getReference().child("Request");
    }

    @Override
    public void onDestroy(){
        Log.e(TAG, "onDestroy");
        super.onDestroy();
        Log.d("RESTARTING sERVICE","currentdestroyed");
        Intent broadcastIntent = new Intent("com.example.harshgoel.restartservice");
        broadcastIntent.putExtra("Account",accountuid);
        sendBroadcast(broadcastIntent);

    }
    private void notification(String k){
        Notification.Builder notification=new Notification.Builder(this);
        notification.setAutoCancel(true);
        notification.setSmallIcon(R.drawable.logo);
        notification.setContentTitle("New Play Request");
        notification.setContentText("You have a play request from "+k);
        notification.setWhen(System.currentTimeMillis());
        Intent intent=new Intent(this,AppLoginPage.class);
        PendingIntent pintent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pintent);
        NotificationManager nm=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        nm.notify(1000,notification.build());
    }

}
