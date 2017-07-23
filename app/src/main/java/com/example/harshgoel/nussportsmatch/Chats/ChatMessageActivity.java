package com.example.harshgoel.nussportsmatch.Chats;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.harshgoel.nussportsmatch.R;

/**
 * Created by Harsh Goel on 7/23/2017.
 */

public class ChatMessageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);
        Toolbar toolbar=(Toolbar)findViewById(R.id.chat_toolbar);
        setSupportActionBar(toolbar);
    }
}
