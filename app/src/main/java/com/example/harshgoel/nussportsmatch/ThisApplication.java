package com.example.harshgoel.nussportsmatch;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.harshgoel.nussportsmatch.Service.NotificationService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

/**
 * Created by Harsh Goel on 8/1/2017.
 */

public class ThisApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        FirebaseAuth author=FirebaseAuth.getInstance();
        if(!isNotificationServiceRunning(NotificationService.class)) {
            Intent intent = new Intent(this, NotificationService.class);
            intent.putExtra("Account", author.getCurrentUser().getUid());
            startService(intent);
        }
    }
    private boolean isNotificationServiceRunning(Class<?> serviceClass){
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("isMyServiceRunning?", true+"");
                return true;
            }
        }
        Log.i ("isMyServiceRunning?", false+"");
        return false;
    }


}
