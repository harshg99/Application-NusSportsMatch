package com.example.harshgoel.nussportsmatch.ThreadsForApp;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;

import com.example.harshgoel.nussportsmatch.Adapters.AboutYouAdapter;
import com.example.harshgoel.nussportsmatch.Logic.Player;
import com.example.harshgoel.nussportsmatch.SignIn;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Harsh Goel on 7/31/2017.
 */

public class Init_aboutyou_background extends AsyncTask<Void,Void,List<String>> {
    private View view;
    private ListView thislist;
    private Player thisplayer;
    private Context context;

    public Init_aboutyou_background(Context context,View parentview, ListView parentlist,Player thisplayer){
        view=parentview;
        thislist=parentlist;
        this.context=context;
        this.thisplayer=thisplayer;
    }

    @Override
    protected List<String> doInBackground(Void... params) {
        List<String> fields=new ArrayList<String>();
        fields.add(thisplayer.getEmail());
        fields.add(thisplayer.getAddress());
        fields.add(thisplayer.getMajor());
        fields.add(thisplayer.getFaculty());
        fields.add(thisplayer.getYear());
        return fields;
    }

    @Override
    protected void onPostExecute(List<String> strings) {
        thislist.setAdapter(new AboutYouAdapter(context,strings));
    }
}
