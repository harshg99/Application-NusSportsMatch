package com.example.harshgoel.nussportsmatch.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harshgoel.nussportsmatch.Chats.ChatMessageActivity;
import com.example.harshgoel.nussportsmatch.Chats.chatbar;
import com.example.harshgoel.nussportsmatch.Logic.Game;
import com.example.harshgoel.nussportsmatch.Logic.Player;
import com.example.harshgoel.nussportsmatch.ProfileDataPackage.Questionaire;
import com.example.harshgoel.nussportsmatch.R;
import com.example.harshgoel.nussportsmatch.Rate_Others.Rate;
import com.example.harshgoel.nussportsmatch.Rate_Others.ViewRate;
import com.google.android.gms.common.api.BooleanResult;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by harsh on 22/08/2017.
 */

public class GameAdapter extends ArrayAdapter<Game> {
    private Context context;
    private Activity activity;
    private DatabaseReference useref;
    private List<String> sport=new ArrayList<String>();
    private Map<String,ArrayList<String>> sportskills=new HashMap<String, ArrayList<String>>();
    public GameAdapter(Context context, @NonNull List<Game> viewgame, Activity activity) {
        super(context, R.layout.games_item_list, viewgame);
        this.context=context;
        this.activity=activity;
        datainit();
    }
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull ViewGroup parent) {
        LayoutInflater viewinflate=LayoutInflater.from(getContext());
        final Game singlegame=getItem(position);
        final FirebaseAuth auth=FirebaseAuth.getInstance();
        View custom=viewinflate.inflate(R.layout.games_item_list,parent,false);
        final TextView Contenttext= (TextView) custom.findViewById(R.id.textView14);
        final ImageView imageprofile =(ImageView) custom.findViewById(R.id.profilepic);
        String uid=null;
        final String UID;
        final Player[] player = new Player[1];
        final Boolean isRate;
        Boolean israted=true;

        if(singlegame!=null) {


            if(singlegame.getPlayer1id().equals(auth.getCurrentUser().getUid())) {
                uid=singlegame.getPlayer2id();
                israted=singlegame.isRatingotherplayer1();

            }
            else if(singlegame.getPlayer2id().equals(auth.getCurrentUser().getUid())){
                uid=singlegame.getPlayer1id();
                israted=singlegame.isRatingotherplayer2();

            }
            if(israted){
                custom.setBackgroundColor(context.getResources().getColor(R.color.ltgrey));
            }


            Log.d("uid",uid);
            UID=uid;
            useref = FirebaseDatabase.getInstance().getReference().child("users").child(uid);
            useref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Player userplayer=dataSnapshot.getValue(Player.class);
                    player[0] =userplayer;
                    if(userplayer!=null) {
                        Log.d("Userplayer:", "Got the snapshot" + userplayer.getUserID());

                        String namep=userplayer.getName();
                        String content1="Game with ";
                        String content2=namep;
                        String content3=" for ";
                        String content4=singlegame.getSport();
                        String content5=" on ";
                        String content6=singlegame.getDate();
                        String content7=" at ";
                        String content8=singlegame.getTime();
                        String content=content1+content2+content3+content4+content5+content6+content7+content8;
                        Spannable sb = new SpannableString( content );
                        sb.setSpan(new StyleSpan(android.graphics.Typeface.BOLD),content.indexOf(namep), content.indexOf(namep)+ content2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); //bold
                        sb.setSpan(new StyleSpan(android.graphics.Typeface.BOLD),content.indexOf(content4), content.indexOf(content4)+ content4.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        sb.setSpan(new StyleSpan(android.graphics.Typeface.BOLD),content.indexOf(content6), content.indexOf(content6)+ content6.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        sb.setSpan(new StyleSpan(android.graphics.Typeface.BOLD),content.indexOf(content8), content.indexOf(content8)+ content8.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                        Contenttext.setText(sb);




                        Log.d("Name: "+position, userplayer.getName());
                        if (userplayer.getProfilephoto().isEmpty()) {
                            if (userplayer.getGender().equals("Male")) {
                                imageprofile.setImageDrawable(context.getResources().getDrawable(R.drawable.male));
                                imageprofile.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            } else {
                                imageprofile.setImageDrawable(context.getResources().getDrawable(R.drawable.female));
                                imageprofile.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            }
                        } else {
                            StorageReference storref = FirebaseStorage.getInstance().getReference().child("Photos").child(UID);
                            storref.child(userplayer.getProfilephoto()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Picasso.with(context).load(uri).fit().into(imageprofile);
                                }
                            });
                        }

                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(context,"Could not do stuff",Toast.LENGTH_LONG);

                }
            });
        }
        ImageButton imageButton=(ImageButton)custom.findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_INSERT)
                        .setData(CalendarContract.Events.CONTENT_URI)
                        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, singlegame.getDatetimemillis())
                        .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, singlegame.getDatetimemillis()+3600000)
                        .putExtra(CalendarContract.Events.TITLE, singlegame.getSport()+" with "+player[0].getName())
                        .putExtra(CalendarContract.Events.DESCRIPTION, Contenttext.getText())
                        .putExtra(CalendarContract.Events.EVENT_LOCATION,singlegame.getSport()+"Court" )
                        .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
                        .putExtra(Intent.EXTRA_EMAIL, auth.getCurrentUser().getEmail());
                activity.startActivity(intent);
            }
        });


        Button viewrate= (Button) custom.findViewById(R.id.ViewRate);
        Button rate= (Button) custom.findViewById(R.id.Rate);
        isRate=israted;
            rate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(System.currentTimeMillis()>(singlegame.getDatetimemillis()+120000)) {
                        if(! isRate) {
                            activity.finish();
                            Intent intent = new Intent().setClass(context, Rate.class);
                            intent.putExtra("sport", sport.get(position).toLowerCase());
                            intent.putExtra("Skills", sportskills.get(sport.get(position)));
                            intent.putExtra("GameID", singlegame.getKey());
                            context.startActivity(intent);
                        }
                        else
                        {
                            AlertDialog.Builder notice=new AlertDialog.Builder(activity);
                            notice.setMessage("You have rated this opponent already ")
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                            notice.create().show();
                        }
                    }
                    else{
                        AlertDialog.Builder notice=new AlertDialog.Builder(activity);
                        notice.setMessage("Cannot Rate before playing the game with opponent ")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        notice.create().show();
                    }
                }
            });


        viewrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
                Intent intent = new Intent().setClass(context, ViewRate.class);
                intent.putExtra("sport", sport.get(position).toLowerCase());
                intent.putExtra("Skills", sportskills.get(sport.get(position)));
                intent.putExtra("GameID", singlegame.getKey());
                context.startActivity(intent);

            }
        });




        return custom;
    }
    private void datainit(){

        sport.add("Tennis");sport.add("Squash");sport.add("Badminton");sport.add("TT");

        String skillt[]={"Backhand","Forehand","Serve","Smash"};
        String skilltt[]={"Backhand Drive","Forehand Drive","Backhand Push","Forehand Push"};
        String skillb[]={"Clear","Drop","Smash","Drive"};
        String skills[]={"Drive","Drop","Boast","Lob"};

        ArrayList<String> tennisskill=new ArrayList<String>(Arrays.asList(skillt));
        ArrayList<String>squashskill=new ArrayList<String>(Arrays.asList(skills));
        ArrayList<String>ttskill=new ArrayList<String>(Arrays.asList(skilltt));
        ArrayList<String>badmintonskill=new ArrayList<String>(Arrays.asList(skillb));

        sportskills.put("Tennis",tennisskill);
        sportskills.put("Squash",squashskill);
        sportskills.put("Badminton",badmintonskill);
        sportskills.put("TT",ttskill);
    }
}
