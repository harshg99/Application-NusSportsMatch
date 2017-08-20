package com.example.harshgoel.nussportsmatch.Chats;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.harshgoel.nussportsmatch.Adapters.ChatMessageAdapter;
import com.example.harshgoel.nussportsmatch.AppLoginPage;
import com.example.harshgoel.nussportsmatch.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Harsh Goel on 7/23/2017.
 */

public class ChatMessageActivity extends AppCompatActivity {
    private String key;
    private DatabaseReference ref;
    private FirebaseAuth auth;

    private ListView chatsalllist;
    private EditText messagetext;
    private ImageButton send;

    private chatbar data;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);
        Toolbar toolbar=(Toolbar)findViewById(R.id.chat_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        key=getIntent().getStringExtra("Chats-id");

        chatsalllist=(ListView)findViewById(R.id.messageView);
        messagetext=(EditText)findViewById(R.id.messagetype);
        send=(ImageButton)findViewById(R.id.send);

        ref= FirebaseDatabase.getInstance().getReference().child("chats");
        auth=FirebaseAuth.getInstance();

        initialisechatdata();
        initialisechatmessages();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!messagetext.getText().toString().isEmpty()) {
                    chatmessage newmessage = initmessage();
                    DatabaseReference ref2 = ref;
                    ref2 = ref2.child(key).child("messages");
                    ref2 = ref2.push();
                    newmessage.setKey(ref2.getKey());
                    ref2.setValue(newmessage);
                    messagetext.setText("");
                    messagetext.setHint("Type a Message");
                    initialisechatmessages();
                }

            }
        });

    }

    private void initialisechatdata(){
        DatabaseReference ref2=ref.child(key);
        ref2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                data=dataSnapshot.getValue(chatbar.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void initialisechatmessages(){
        DatabaseReference ref2=ref.child(key).child("messages");
        final List<chatmessage> messagelist=new ArrayList<>();
        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("Count: ", "" + dataSnapshot.getChildrenCount());
                for(DataSnapshot chat : dataSnapshot.getChildren()){
                    chatmessage message=chat.getValue(chatmessage.class);
                    messagelist.add(message);
                }
                if(messagelist.size()!=0) {
                    ChatMessageAdapter adapter = new ChatMessageAdapter(ChatMessageActivity.this,messagelist, auth);
                    chatsalllist.setAdapter(adapter);
                    chatsalllist.setSelection(adapter.getCount() - 1);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                Intent i=new Intent(this,AppLoginPage.class);
                startActivity(i);
                return true;
            default:return super.onOptionsItemSelected(item);

        }
    }
    private chatmessage initmessage(){
        chatmessage newmessage=new chatmessage();
        newmessage.setMessage(messagetext.getText().toString());
        newmessage.setSender(auth.getCurrentUser().getUid());
        newmessage.setTime(System.currentTimeMillis());
        return  newmessage;
    }

}
