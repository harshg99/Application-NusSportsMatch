package com.example.harshgoel.nussportsmatch.ThreadsForApp;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.harshgoel.nussportsmatch.Adapters.SecurityAdapter;
import com.example.harshgoel.nussportsmatch.Logic.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Harsh Goel on 8/1/2017.
 */

public class Init_Securitycontent extends AsyncTask<Void,Void,Void> {
    private Context context;
    private List<String> headers;
    private ListView seecuritycontent;
    private AppCompatActivity activity;
    public Init_Securitycontent(AppCompatActivity activity,Context context, ListView securitycontent){
        this.context=context;
        headers=new ArrayList<String>();
        this.activity=activity;
        this.seecuritycontent=securitycontent;
    }

    @Override
    protected Void doInBackground(Void... params) {
        headers.add("Email");
        headers.add("Password");
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        seecuritycontent.setAdapter(new SecurityAdapter(activity,context,headers));
        super.onPostExecute(aVoid);
    }
}
