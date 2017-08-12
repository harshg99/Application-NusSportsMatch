package com.example.harshgoel.nussportsmatch.ThreadsForApp;

import android.os.AsyncTask;

import com.example.harshgoel.nussportsmatch.Fragments.Fragment_match;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by Harsh Goel on 7/28/2017.
 */

public class EvaluateFlags extends AsyncTask<Void,Integer,Fragment_match.checkfields> {

    FirebaseAuth auth;
    DatabaseReference ref;
    public EvaluateFlags(){

    }
    @Override
    protected void onPostExecute(Fragment_match.checkfields checkfields) {
        super.onPostExecute(checkfields);

    }

    @Override
    protected Fragment_match.checkfields doInBackground(Void... params) {
        return null;
    }

}
