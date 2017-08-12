package com.example.harshgoel.nussportsmatch.ProfileDataPackage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.harshgoel.nussportsmatch.Adapters.EditProfileAdapter;
import com.example.harshgoel.nussportsmatch.AppLoginPage;
import com.example.harshgoel.nussportsmatch.Logic.Player;
import com.example.harshgoel.nussportsmatch.R;
import com.example.harshgoel.nussportsmatch.ThreadsForApp.Init_Securitycontent;
import com.example.harshgoel.nussportsmatch.ThreadsForApp.Init_profilecontent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Harsh Goel on 7/7/2017.
 */

public class EditProfile extends AppCompatActivity {

    public Button confirm;
    private boolean isupdate;
    private FirebaseAuth auth;
    private boolean ifreauth=true;
    public Toolbar toolbar;
    private DatabaseReference data;
    private ExpandableListView list_content;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_profile);
        list_content=(ExpandableListView)findViewById(R.id.ProfileContents);
        final ListView list_security=(ListView)findViewById(R.id.securitycontents);
        auth=FirebaseAuth.getInstance();
        data=FirebaseDatabase.getInstance().getReference().child("users").child(auth.getCurrentUser().getUid());
        data.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Player userplayer=dataSnapshot.getValue(Player.class);
                new Init_profilecontent(EditProfile.this,getApplicationContext(),userplayer,list_content)
                        .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                new Init_Securitycontent(EditProfile.this,getApplicationContext(),list_security)
                        .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        toolbar=(Toolbar)findViewById(R.id.edit_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                    finish();
                    Intent c = new Intent(EditProfile.this, AppLoginPage.class);
                    startActivity(c);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}

