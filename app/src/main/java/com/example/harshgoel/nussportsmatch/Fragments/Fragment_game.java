package com.example.harshgoel.nussportsmatch.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.harshgoel.nussportsmatch.R;

/**
 * Created by Harsh Goel on 7/16/2017.
 */

public class Fragment_game extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_calendar,container,false);
    }
}
