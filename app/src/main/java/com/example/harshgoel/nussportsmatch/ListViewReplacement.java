package com.example.harshgoel.nussportsmatch;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

import java.util.List;

/**
 * Created by Harsh Goel on 8/2/2017.
 */

public class ListViewReplacement {
    private ListAdapter adapter;
    private Context context;
    private Activity activity;
    private View resourceHeader;
    private View resourceFooter;
    private LinearLayout layout;

    public ListViewReplacement(Activity activity,Context context,int resourceLayout){
        this.activity=activity;
        this.context=context;
        resourceFooter=null;
        resourceHeader=null;
        layout=(LinearLayout)activity.findViewById(resourceLayout);
    }
    public void setAdapter(ListAdapter adapter){
        this.adapter=adapter;
    }
    private void setResourceHeader(int resourceHeader){
        LayoutInflater viewinflate=LayoutInflater.from(context);
        View custom=viewinflate.inflate(resourceHeader,layout,false);
        this.resourceHeader=custom;
    }
    private void setResourceFooter(int resourceFooter){
        LayoutInflater viewinflate=LayoutInflater.from(context);
        View custom=viewinflate.inflate(resourceFooter,layout,false);
        this.resourceFooter=custom;
    }
    public void setHeadFoot(int rh,int rf){
        setResourceFooter(rf);
        setResourceHeader(rh);
    }
    public void setList() {
        if (layout != null) {
            if (resourceHeader != null) {
                layout.addView(resourceHeader);
            }
            if (adapter != null) {
                for (int i = 0; i < adapter.getCount(); i++) {
                    layout.addView(adapter.getView(i, null, layout));
                }
            }
            if (resourceFooter != null) {
                layout.addView(resourceFooter);
            }
        }
    }
}
