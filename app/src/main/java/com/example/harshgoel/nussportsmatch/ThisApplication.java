package com.example.harshgoel.nussportsmatch;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Harsh Goel on 8/1/2017.
 */

public class ThisApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
