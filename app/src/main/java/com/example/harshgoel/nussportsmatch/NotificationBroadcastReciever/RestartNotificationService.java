package com.example.harshgoel.nussportsmatch.NotificationBroadcastReciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.harshgoel.nussportsmatch.Service.NotificationService;

/**
 * Created by harsh on 18/08/2017.
 */

public class RestartNotificationService extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("sERVICE restarting","currentdestroyed");
        Intent restartserviceintent=new Intent(context, NotificationService.class);
        restartserviceintent.putExtra("Account",intent.getStringExtra("Account"));
        context.startService(intent);
    }
}
