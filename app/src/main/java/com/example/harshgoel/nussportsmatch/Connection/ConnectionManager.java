package com.example.harshgoel.nussportsmatch.Connection;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Harsh Goel on 7/26/2017.
 */

public class ConnectionManager extends AsyncTask<Void,Void,Boolean> {
    private View connectView;
    private Context connectContext;
    private AppCompatActivity activity;
    public ConnectionManager(View v,Context c){
        connectView=v;
        connectContext=c;
        activity=(AppCompatActivity)c;
    }
    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) connectContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
    public boolean isInternetWorking() {
        boolean success = false;
        if (isNetworkAvailable()){
            try {
                URL url = new URL("https://google.com");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(10000);
                connection.connect();
                success = connection.getResponseCode() == 200;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return success;
        }
        return false;
    }


    @Override
    protected Boolean doInBackground(Void... params) {

        return isInternetWorking();
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        if(!aBoolean){
            connectView.setEnabled(false);
            connectView.setAlpha((float)0.5);
            Snackbar.make(connectView,"No Internet Connection",Snackbar.LENGTH_INDEFINITE)
                    .setAction("Close", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.finish();
                }
            })
                    .show();
        }
        else
        {
            connectView.setAlpha(1);
        }
    }
}
