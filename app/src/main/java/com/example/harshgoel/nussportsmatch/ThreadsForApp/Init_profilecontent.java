package com.example.harshgoel.nussportsmatch.ThreadsForApp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;

import com.example.harshgoel.nussportsmatch.Adapters.EditProfileAdapter;
import com.example.harshgoel.nussportsmatch.Logic.Player;
import com.example.harshgoel.nussportsmatch.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Harsh Goel on 8/1/2017.
 */

public class Init_profilecontent extends AsyncTask<Void,Void,Map<String,String>> {
    private Context context;
    private Player thisplayer;
    private ExpandableListView contentlistview;
    private List<String> headers;
    private AppCompatActivity activity;
    public Init_profilecontent(AppCompatActivity activity,Context context, Player thisplayer, ExpandableListView contentlistview){
        this.context=context;
        this.thisplayer=thisplayer;
        this.contentlistview=contentlistview;
        this.activity=activity;
    }

    @Override
    protected Map<String,String> doInBackground(Void... params) {
        Map<String,String>values=new HashMap<String, String>();
        headers=new ArrayList<String>();
        values.put("Name",thisplayer.getName());headers.add("Name");
        values.put("Address",thisplayer.getAddress());headers.add("Address");
        values.put("Gender",thisplayer.getGender());headers.add("Gender");
        values.put("Faculty",thisplayer.getFaculty());headers.add("Faculty");
        values.put("Major",thisplayer.getMajor());headers.add("Major");
        values.put("Year",thisplayer.getYear());headers.add("Year");
        return values;
    }

    @Override
    protected void onPostExecute(Map<String, String> values) {
        EditProfileAdapter Adapter=new EditProfileAdapter(activity,context,values,headers);
        contentlistview.setAdapter(Adapter);
        int count=headers.size();
        for(int i=0;i<count;i++) {
            contentlistview.expandGroup(i);
        }
    }
}
