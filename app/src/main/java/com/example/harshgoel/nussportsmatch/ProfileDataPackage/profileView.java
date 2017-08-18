package com.example.harshgoel.nussportsmatch.ProfileDataPackage;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.harshgoel.nussportsmatch.AppLoginPage;
import com.example.harshgoel.nussportsmatch.Logic.Player;
import com.example.harshgoel.nussportsmatch.R;
import com.example.harshgoel.nussportsmatch.ThreadsForApp.Init_Sports_Background;
import com.example.harshgoel.nussportsmatch.ThreadsForApp.Init_aboutyou_background;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

/**
 * Created by harsh on 17/08/2017.
 */

public class profileView extends AppCompatActivity {
    private ImageView profilephoto;
    private TextView profilename;
    private Toolbar toolbar;
    private DatabaseReference ref;
    private String DisplayId;
    private Player userplayer;
    private ExpandableListView sportsreg;
    private StorageReference storref;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_view);
        toolbar=(Toolbar)findViewById(R.id.viewbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        DisplayId=getIntent().getStringExtra("Player");
        ref= FirebaseDatabase.getInstance().getReference().child("users").child(DisplayId);
        profilephoto=(ImageView)findViewById(R.id.ProfilePhoto);
        final ProgressDialog dialog=new ProgressDialog(this);
        dialog.setMessage("Retrieving...");
        dialog.show();
        sportsreg=(ExpandableListView)findViewById(R.id.SportsList);
        profilename=(TextView)this.findViewById(R.id.Name);
        storref= FirebaseStorage.getInstance().getReference().child("Photos").child(DisplayId);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                userplayer=dataSnapshot.getValue(Player.class);
                profilename.setText(userplayer.getName());

                if(userplayer.getProfilephoto().isEmpty()) {
                    if (userplayer.getGender().equals("Male")) {
                        profilephoto.setImageDrawable(getResources().getDrawable(R.drawable.male));
                        profilephoto.setScaleType(ImageView.ScaleType.FIT_XY);
                    } else {
                        profilephoto.setImageDrawable(getResources().getDrawable(R.drawable.female));
                        profilephoto.setScaleType(ImageView.ScaleType.FIT_XY);
                    }
                }
                else{
                    storref.child(userplayer.getProfilephoto()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Picasso.with(profileView.this).load(uri).fit().into(profilephoto);
                        }
                    });
                }
                new Init_aboutyou_background(profileView.this,profileView.this,R.id.whoareyoulayout,userplayer)
                        .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                new Init_Sports_Background(profileView.this,dialog,sportsreg,userplayer)
                        .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


                Log.d("UserPlayer:",userplayer.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Database Reference:","Read ERROR");
                dialog.cancel();
                AlertDialog.Builder SelectSports=new AlertDialog.Builder(profileView.this);
                SelectSports.setTitle("No Internet Connection")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                profileView.this.finish();
                            }
                        });
                SelectSports.create().show();
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:return super.onOptionsItemSelected(item);
        }

    }
}
