package com.example.harshgoel.nussportsmatch.ProfileDataPackage;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.harshgoel.nussportsmatch.AppLoginPage;
import com.example.harshgoel.nussportsmatch.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Harsh Goel on 7/7/2017.
 */

public class EditProfile extends AppCompatActivity {

    public Button confirm;
    private boolean isupdate;
    private int cpass;
    private int cname;
    private int cemail;
    public EditText emailchange;
    public EditText passwordchange;
    public EditText namechange;
    private FirebaseAuth auth;
    public Toolbar toolbar;
    private DatabaseReference data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);
        namechange= (EditText) findViewById(R.id.textname);
        passwordchange= (EditText) findViewById(R.id.textpassword);
        emailchange= (EditText) findViewById(R.id.textemail);
        confirm=(Button)findViewById(R.id.Confirmchanges);
        toolbar=(Toolbar)findViewById(R.id.edit_bar);
        setSupportActionBar(toolbar);
        auth=FirebaseAuth.getInstance();
        cpass=0;
        cname=0;
        cemail=0;
        data= FirebaseDatabase.getInstance().getReference();
        data.child("users").child(auth.getCurrentUser().getUid()).child("UserID").setValue(auth.getCurrentUser().getUid());

    }
    public void ConfirmChanges(View v) {
        checkfilled();
        Log.d("Value thrown "," cpass "+cpass+" cname "+cname+" cemail "+cemail);
        if(cemail>0 && cpass>0 && cname>0) {
            finish();
            Intent c = new Intent(EditProfile.this, AppLoginPage.class);
            startActivity(c);
        }
    }
    public void back(View v){
        if(cemail>0 && cpass>0 && cname>0){
            finish();
            Intent c = new Intent(EditProfile.this, AppLoginPage.class);
            startActivity(c);
        }
        else
        {
            AlertDialog.Builder dialogbox = new AlertDialog.Builder(this);
            dialogbox.setMessage("Are you sure you want to exit?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            Intent c = new Intent(EditProfile.this, AppLoginPage.class);
                            startActivity(c);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            dialogbox.create().show();
        }
    }
    public void checkfilled(){
        if(TextUtils.isEmpty(emailchange.getText().toString())){
            AlertDialog.Builder dialogbox = new AlertDialog.Builder(this);
            dialogbox.setMessage("Do you want to keep original email?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            cemail=1;
                            dialog.cancel();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            Toast.makeText(EditProfile.this,"Please enter email",Toast.LENGTH_LONG).show();
                        }
                    });
            dialogbox.create().show();
        }
        else {
            cemail = 2;
            Map<String,Object> emailfield=new HashMap<String,Object>();
            emailfield.put("email",emailchange.getText().toString().trim());
            data.child("users").child(auth.getCurrentUser().getUid()).updateChildren(emailfield);
        }
        if(TextUtils.isEmpty(passwordchange.getText().toString())){
            AlertDialog.Builder dialogbox = new AlertDialog.Builder(this);
            dialogbox.setMessage("Do you want to keep original password?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                             cpass=1;
                            dialog.cancel();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(EditProfile.this, "Please enter password", Toast.LENGTH_LONG).show();
                            dialog.cancel();
                        }

                    });
            dialogbox.create().show();
        }
        else {
            cpass = 2;
            Map<String, Object> passfield = new HashMap<String, Object>();
            passfield.put("password", passwordchange.getText().toString().trim());
            data.child("users").child(auth.getCurrentUser().getUid()).updateChildren(passfield);
        }
        if(TextUtils.isEmpty(namechange.getText().toString())) {
            AlertDialog.Builder dialogbox = new AlertDialog.Builder(this);
            dialogbox.setMessage("Do you want to keep original name?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            cname = 1;
                            dialog.cancel();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(EditProfile.this, "Please enter name", Toast.LENGTH_LONG).show();
                            dialog.cancel();
                        }

                    });
            dialogbox.create().show();
        }
        else {
            cname = 2;
            Map<String, Object> namefield = new HashMap<String, Object>();
            namefield.put("name", namechange.getText().toString().trim());
            data.child("users").child(auth.getCurrentUser().getUid()).updateChildren(namefield);
        }
    }

}

