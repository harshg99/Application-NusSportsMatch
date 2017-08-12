package com.example.harshgoel.nussportsmatch.ThreadsForApp;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Adapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.harshgoel.nussportsmatch.Adapters.AboutYouAdapter;
import com.example.harshgoel.nussportsmatch.ListViewReplacement;
import com.example.harshgoel.nussportsmatch.Logic.Player;
import com.example.harshgoel.nussportsmatch.R;
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
    private Activity activity;
    private Player thisplayer;
    private Context context;
    private int linearLayout;
    public Init_aboutyou_background(Activity activity,Context context, int linearLayout,Player thisplayer){

        this.context=context;
        this.thisplayer=thisplayer;
        this.linearLayout=linearLayout;
        this.activity=activity;
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
        ListAdapter adapter=new AboutYouAdapter(context,strings);
        ListViewReplacement list=new ListViewReplacement(activity,context,linearLayout);
        list.setAdapter(adapter);
        list.setHeadFoot(R.layout.header_aboutyou,R.layout.footer_aboutyou);
        list.setList();
    }
}
