package com.example.harshgoel.nussportsmatch.Adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.harshgoel.nussportsmatch.Chats.chatbar;
import com.example.harshgoel.nussportsmatch.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

/**
 * Created by Harsh Goel on 7/23/2017.
 */

public class ChatBarAdapter extends ArrayAdapter<chatbar> {
    public ChatBarAdapter(Context context, @NonNull List<chatbar> viewchats) {
        super(context, R.layout.chat_barlist, viewchats);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater viewinflate=LayoutInflater.from(getContext());
        chatbar singlechat=getItem(position);
        FirebaseAuth auth=FirebaseAuth.getInstance();
        View custom=viewinflate.inflate(R.layout.chat_barlist,parent,false);
        if(singlechat!=null) {
            TextView nametext = (TextView) custom.findViewById(R.id.nameofchat);
            if(singlechat.getSenderofmessageid().equals(auth.getCurrentUser().getUid())) {
                nametext.setText(singlechat.getRecieverofmessage());
            }
            else if(singlechat.getGetRecieverofmessageid().equals(auth.getCurrentUser().getUid())){
                nametext.setText(singlechat.getSenderofmessagename());
            }
            TextView lasttext = (TextView) custom.findViewById(R.id.lastmessage);
            lasttext.setText(singlechat.getLastmessage());
            TextView timetext = (TextView) custom.findViewById(R.id.time);
            timetext.setText(singlechat.getTime());
        }
        return custom;
    }
}
