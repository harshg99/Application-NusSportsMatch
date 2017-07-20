package com.example.harshgoel.nussportsmatch.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.harshgoel.nussportsmatch.Fragments.Fragment_calendar;
import com.example.harshgoel.nussportsmatch.Fragments.Fragment_chats;
import com.example.harshgoel.nussportsmatch.Fragments.Fragment_match;
import com.example.harshgoel.nussportsmatch.Fragments.Fragment_profile;

/**
 * Created by Harsh Goel on 7/16/2017.
 */

public class ProfileTabLayoutAdapter extends FragmentPagerAdapter {
    public String[] titles=new String[]{"Match","Chats","Profile","Calendar"};
    private Context context;
    private int pagecount=4;
    public ProfileTabLayoutAdapter(FragmentManager fm,Context context) {
        super(fm);
        this.context=context;
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0){
            return new Fragment_match();
        }
        else if(position==1){
            return new Fragment_chats();
        }
        else if(position==2){
            return new Fragment_profile();
        }
        else if(position==3){
            return  new Fragment_calendar();
        }
        else
        {
            return new Fragment();
        }
    }

    @Override
    public int getCount() {
        return pagecount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
