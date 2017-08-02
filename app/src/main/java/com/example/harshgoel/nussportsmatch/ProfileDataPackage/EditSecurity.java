package com.example.harshgoel.nussportsmatch.ProfileDataPackage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harshgoel.nussportsmatch.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Harsh Goel on 8/1/2017.
 */

public class EditSecurity extends AppCompatActivity {
    private TextView header;
    private EditText contentold;
    private EditText contentnew;
    private Button save;
    private Toolbar toolbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_security);
        header=(TextView)findViewById(R.id.Contenthead);
        contentold=(EditText)findViewById(R.id.Content1);
        contentnew=(EditText)findViewById(R.id.Content2);
        save=(Button)findViewById(R.id.Save);
        toolbar=(Toolbar)findViewById(R.id.edit_content_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        header.setText(getIntent().getStringExtra("Content"));
        contentold.setHint("Old "+getIntent().getStringExtra("Content"));
        contentnew.setHint("New "+getIntent().getStringExtra("Content"));

        final FirebaseAuth auth=FirebaseAuth.getInstance();
        final DatabaseReference ref= FirebaseDatabase.getInstance().getReference()
                .child("users").child(auth.getCurrentUser().getUid());

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getIntent().getStringExtra("Content").equals("Password")) {
                    final String oldpass = contentold.getText().toString();
                    final String newPass = contentnew.getText().toString();
                    if (!oldpass.isEmpty() &&!newPass.isEmpty()) {
                        final FirebaseUser user = auth.getCurrentUser();
                        AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), oldpass);

                        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    user.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (!task.isSuccessful()) {
                                                Toast.makeText(EditSecurity.this, "Something went wrong. Please try again later", Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(EditSecurity.this, "Password Successfully Modified", Toast.LENGTH_LONG).show();
                                                Map<String, Object> passfield = new HashMap<String, Object>();
                                                passfield.put("password", contentnew.getText().toString().trim());
                                                ref.updateChildren(passfield);
                                            }
                                        }
                                    });
                                }
                            }
                        });


                    }
                }
                else{
                    final String oldemail = contentold.getText().toString();
                    final String newemail = contentnew.getText().toString();
                    if(!oldemail.isEmpty()&&!oldemail.isEmpty()){
                        FirebaseUser user=auth.getCurrentUser();
                        if(oldemail.equals(user.getEmail())) {
                            user.updateEmail(newemail).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(EditSecurity.this, "Something went wrong. Please try again later", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(EditSecurity.this, "E-Mail Successfully Modified", Toast.LENGTH_LONG).show();
                                        Map<String, Object> passfield = new HashMap<String, Object>();
                                        passfield.put("email", contentnew.getText().toString().trim());
                                        ref.updateChildren(passfield);
                                    }
                                }
                            });
                        }
                        else{
                            Toast.makeText(EditSecurity.this, "In-Correct Email Address", Toast.LENGTH_LONG).show();
                        }
                    }
                }
                finish();
                startActivity(new Intent(EditSecurity.this,EditProfile.class));
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                startActivity(new Intent(this,EditProfile.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
