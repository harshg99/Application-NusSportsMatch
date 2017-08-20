package com.example.harshgoel.nussportsmatch.ThreadsForApp;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

import com.example.harshgoel.nussportsmatch.Adapters.NotificationRecieve;
import com.example.harshgoel.nussportsmatch.Adapters.NotificationSent;
import com.example.harshgoel.nussportsmatch.Logic.Request;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by harsh on 20/08/2017.
 */

public class request_s extends AsyncTask<Void,Void,Void> {
    private ListView listview;
    private Context context;
    private List<Request> list_s;
    public request_s(ListView listview, Context context, List<Request> list_s){
        this.list_s=list_s;
        this.listview=listview;
        this.context=context;
    }
    @Override
    protected Void doInBackground(Void... params) {
        Collections.reverse(list_s);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        listview.setAdapter(new NotificationSent(context,list_s));
    }
}