package com.example.harshgoel.nussportsmatch.ThreadsForApp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ExpandableListView;

import com.example.harshgoel.nussportsmatch.Adapters.SportsRegAdapter;
import com.example.harshgoel.nussportsmatch.Logic.Player;
import com.example.harshgoel.nussportsmatch.Logic.Rating;
import com.example.harshgoel.nussportsmatch.Logic.sportsPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Harsh Goel on 7/31/2017.
 */

public class Init_Sports_Background extends AsyncTask<Void,Void,Map<String,sportsPlayer>> {
    private ProgressDialog dialogbar;
    private ExpandableListView sportslist;
    private Player thisplayer;
    private Context context;
    List<String>header=new ArrayList<String>();
    public Init_Sports_Background(Context context,ProgressDialog dialogbar, ExpandableListView sportslist, Player thisplayer){
        this.dialogbar=dialogbar;
        this.sportslist=sportslist;
        this.thisplayer=thisplayer;
        this.context=context;
    }
    @Override
    protected Map<String,sportsPlayer> doInBackground(Void... params) {
        Map<String,sportsPlayer>explistval=new HashMap<String, sportsPlayer>();
        explistval.put("Tennis",thisplayer.getTennis());header.add("Tennis");
        explistval.put("Badminton",thisplayer.getBadminton());header.add("Badminton");
        explistval.put("Squash",thisplayer.getSquash());header.add("Squash");
        explistval.put("Table Tennis",thisplayer.getTt());header.add("Table Tennis");
        return explistval;
    }

    @Override
    protected void onPostExecute(Map<String,sportsPlayer>values)
    {
        SportsRegAdapter adapter=new SportsRegAdapter(context,values,header);
        sportslist.setAdapter(adapter);
        dialogbar.cancel();
    }
}
