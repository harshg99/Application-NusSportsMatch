
package com.example.harshgoel.nussportsmatch.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.harshgoel.nussportsmatch.R;

/**
 * Created by Harsh Goel on 7/16/2017.
 */

public class Fragment_match extends Fragment {
    private Spinner sportSpinner;
    private ArrayAdapter<CharSequence> SpinnerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment_match=inflater.inflate(R.layout.sport_match,container,false);
        sportSpinner=(Spinner)fragment_match.findViewById(R.id.SpinnerSportsSelect);
        SpinnerAdapter =
                ArrayAdapter.createFromResource(getActivity(),
                        R.array.SportsOptions,
                        android.R.layout.simple_spinner_item);
        SpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sportSpinner.setAdapter(SpinnerAdapter);
        sportSpinner.setSelection(SpinnerAdapter.getPosition("Select to Sport to Matchup"));
        return fragment_match;
    }
}
