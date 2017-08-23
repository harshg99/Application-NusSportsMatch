package com.example.harshgoel.nussportsmatch.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harshgoel.nussportsmatch.Chats.ChatMessageActivity;
import com.example.harshgoel.nussportsmatch.Chats.chatbar;
import com.example.harshgoel.nussportsmatch.Logic.Player;
import com.example.harshgoel.nussportsmatch.R;
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

import java.util.List;

/**
 * Created by Harsh Goel on 7/23/2017.
 */

public class ChatBarAdapter extends ArrayAdapter<chatbar> {
    private Context context;
    private Activity activity;
    private String uid;
    private TextView nametext;
    private ImageView imageprofile;
    private Player userplayer;
    private DatabaseReference useref;
    public ChatBarAdapter(Context context, @NonNull List<chatbar> viewchats,Activity activity) {
        super(context, R.layout.chat_barlist, viewchats);
        this.context=context;
        this.activity=activity;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable final View convertView, @NonNull ViewGroup parent) {
        LayoutInflater viewinflate=LayoutInflater.from(getContext());
        final chatbar singlechat=getItem(position);
        FirebaseAuth auth=FirebaseAuth.getInstance();
        View custom=viewinflate.inflate(R.layout.chat_barlist,parent,false);
        TextView lasttext = (TextView) custom.findViewById(R.id.lastmessage);
        lasttext.setText(singlechat.getLastmessage());
        TextView timetext = (TextView) custom.findViewById(R.id.time);
        timetext.setText(singlechat.getTime());
        nametext = (TextView) custom.findViewById(R.id.nameofchat);
        imageprofile =(ImageView) custom.findViewById(R.id.profilepic);

        if(singlechat!=null) {


            if(singlechat.getPlayer1id().equals(auth.getCurrentUser().getUid())) {
                uid=singlechat.getPlayer2id();

            }
            else if(singlechat.getPlayer2id().equals(auth.getCurrentUser().getUid())){
                uid=singlechat.getPlayer1id();
            }
            Log.d("uid",uid);
                useref = FirebaseDatabase.getInstance().getReference().child("users").child(uid);
                useref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        userplayer=dataSnapshot.getValue(Player.class);
                        if(userplayer!=null) {
                            Log.d("Userplayer:", "Got the snapshot" + userplayer.getUserID());
                            nametext.setText(userplayer.getName());
                            Log.d("Name:::", userplayer.getName());
                            if (userplayer.getProfilephoto() != null) {
                                if (userplayer.getProfilephoto().isEmpty()) {
                                    if (userplayer.getGender().equals("Male")) {
                                        imageprofile.setImageDrawable(context.getResources().getDrawable(R.drawable.male));
                                        imageprofile.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                    } else {
                                        imageprofile.setImageDrawable(context.getResources().getDrawable(R.drawable.female));
                                        imageprofile.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                    }
                                } else {
                                    StorageReference storref = FirebaseStorage.getInstance().getReference().child("Photos").child(uid);
                                    storref.child(userplayer.getProfilephoto()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            Picasso.with(context).load(uri).fit().into(imageprofile);
                                        }
                                    });
                                }

                            }
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(context,"Could not do stuff",Toast.LENGTH_LONG);

                    }
                });
        }
        custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        Intent i = new Intent(context, ChatMessageActivity.class);
                        i.putExtra("Chats-id",singlechat.getKey());
                        activity.finish();
                        context.startActivity(i);
            }
        });

        return custom;
    }
}
