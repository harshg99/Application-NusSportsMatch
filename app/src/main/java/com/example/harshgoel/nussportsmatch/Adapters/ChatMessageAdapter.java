package com.example.harshgoel.nussportsmatch.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.harshgoel.nussportsmatch.Chats.chatbar;
import com.example.harshgoel.nussportsmatch.Chats.chatmessage;
import com.example.harshgoel.nussportsmatch.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

/**
 * Created by harsh on 19/08/2017.
 */

public class ChatMessageAdapter extends ArrayAdapter<chatmessage> {
    private Context context;
    private FirebaseAuth auth;
    public ChatMessageAdapter(Context context, @NonNull List<chatmessage> viewchats, FirebaseAuth auth) {
        super(context, R.layout.chats_message, viewchats);
        this.context=context;
        this.auth=auth;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater viewinflate=LayoutInflater.from(getContext());
        final chatmessage singlemessage=getItem(position);
        View custom=viewinflate.inflate(R.layout.chats_message,parent,false);

        TextView messageother=(TextView)custom.findViewById(R.id.messageother);
        TextView messagethis=(TextView)custom.findViewById(R.id.messagethisuser);

        if(singlemessage.getSender().equals(auth.getCurrentUser().getUid())){
            messagethis.setBackground(context.getResources().getDrawable(R.drawable.messagethisuser));
            messagethis.setText(singlemessage.getMessage());
        }
        else{
            messageother.setBackground(context.getResources().getDrawable(R.drawable.messageother));
            messageother.setText(singlemessage.getMessage());

        }
        /*
        chatmessage prevchat=getItem(position-1);
        if(!singlemessage.getSender().equals(prevchat.getSender())) {
            custom.setPadding(0,6,0,0);
        }
        else{
            custom.setPadding(0,2,0,0);
        }
        */

        return custom;

    }
}
