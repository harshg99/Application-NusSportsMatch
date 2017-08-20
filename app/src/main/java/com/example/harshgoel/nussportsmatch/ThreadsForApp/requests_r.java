package com.example.harshgoel.nussportsmatch.ThreadsForApp;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

import com.example.harshgoel.nussportsmatch.Adapters.NotificationRecieve;
import com.example.harshgoel.nussportsmatch.Logic.Request;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by harsh on 20/08/2017.
 */

public class requests_r extends AsyncTask<Void,Void,Void> {
    private ListView listview;
    private Context context;
    private List<Request> list_r;
    public requests_r(ListView listview, Context context, List<Request> list_r){
        this.list_r=list_r;
        this.listview=listview;
        this.context=context;
    }
    @Override
    protected Void doInBackground(Void... params) {
        Collections.reverse(list_r);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        listview.setAdapter(new NotificationRecieve(context,list_r));
    }
}
