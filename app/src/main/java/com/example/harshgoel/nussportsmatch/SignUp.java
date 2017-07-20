package com.example.harshgoel.nussportsmatch;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.harshgoel.nussportsmatch.Logic.Player;
import com.example.harshgoel.nussportsmatch.Logic.sportsPlayer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.content.ContentValues.TAG;

/**
 * Created by Harsh Goel on 6/26/2017.
 */
//declaration of the signup class
public class SignUp extends AppCompatActivity {
    //UI components
    public EditText email;
    public EditText pass;
    public EditText confirmpass;
    public Button confirmbut;
    public ImageView icon;
    private FirebaseAuth auth;
    private DatabaseReference data;
    private FirebaseAuth.AuthStateListener mAuthListener;
    public ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        data= FirebaseDatabase.getInstance().getReference();
        if (user != null) {
            // User is signed in
            Toast.makeText(getApplicationContext(), "Already Signed In", Toast.LENGTH_LONG).show();
            com.example.harshgoel.nussportsmatch.SignUp.this.finish();
            Intent intent=new Intent()
                    .setClass(com.example.harshgoel.nussportsmatch.SignUp.this,AppLoginPage.class);
            startActivity(intent);

        } else {
            // User is signed out
            Toast.makeText(getApplicationContext(), "Please Sign Up", Toast.LENGTH_LONG).show();
            Log.d(TAG, "onAuthStateChanged:signed_out");
        }
        setContentView(R.layout.activity_sign_up);
        // getSupportActionBar().setHomeButtonEnabled(true);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initialiseUI();

    }

    //initialises the UI of the signup activity
    public void initialiseUI() {
        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.pass);
        confirmpass = (EditText) findViewById(R.id.confirmpass);
        confirmbut = (Button) findViewById(R.id.confirmbut);
        auth = FirebaseAuth.getInstance();
        icon = (ImageView) findViewById(R.id.imageView);
        progressDialog=new ProgressDialog(SignUp.this);
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());


                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }


    //addprofile invoked when button confirm is clicked
    public void addprofile(View view) {
        //adds the profilelogin if the password and confirm password strings match
        String textemail=email.getText().toString();
        String password = pass.getText().toString();
        String confirmpassword = pass.getText().toString();
        if(TextUtils.isEmpty(textemail)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(confirmpassword)){
            Toast.makeText(this,"Please confirm password",Toast.LENGTH_LONG).show();
            return;
        }

            boolean checkpass = analyse_password(password, confirmpassword);
        //Dialog box is shown
        if (checkpass == true) {
            AlertDialog.Builder dialogbox = new AlertDialog.Builder(com.example.harshgoel.nussportsmatch.SignUp.this);
            dialogbox.setMessage("Do You Want To Proceed ?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            final Player newplayer = new Player();
                            newplayer.setEmail(email.getText().toString().trim());
                            newplayer.setpassword(pass.getText().toString().trim());
                            newplayer.setName("Default_Name");
                            progressDialog.setMessage("Signing Up");
                            progressDialog.show();
                            auth.createUserWithEmailAndPassword(newplayer.getEmail(), newplayer.getpassword())
                                    .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            //checking if success
                                            if (task.isSuccessful()) {
                                                //display some message her
                                                inituserdata(newplayer,auth.getCurrentUser().getUid());
                                                Toast.makeText(getApplicationContext(), "Successfully registered", Toast.LENGTH_LONG).show();
                                                Intent intent=new Intent()
                                                        .setClass(com.example.harshgoel.nussportsmatch.SignUp.this,SignIn.class);
                                                startActivity(intent);
                                                com.example.harshgoel.nussportsmatch.SignUp.this.finish();
                                            } else {
                                                //display some message here
                                                Toast.makeText(getApplicationContext(), "Registration Error", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                          //  firebaseadd(newplayer.getEmail(), newplayer.getpassword());

                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = dialogbox.create();
            alert.show();
        }

    }
    public void inituserdata(Player k,String Uid){
        data.child("users").child(Uid).setValue(k);
        sportsPlayer Tennis=new sportsPlayer();
        sportsPlayer squash =new sportsPlayer();
        sportsPlayer TT=new sportsPlayer();
        sportsPlayer badminton=new sportsPlayer();
        data.child("users").child(auth.getCurrentUser().getUid()).child("tennis").child("SportsRate").setValue(Tennis.getRating());
        data.child("users").child(auth.getCurrentUser().getUid()).child("tennis").child("isAdded").setValue(Tennis.getisAdded());
        data.child("users").child(auth.getCurrentUser().getUid()).child("tennis")
                .child("questionaireCompleted").setValue(Tennis.isQuestionaireCompleted());
        data.child("users").child(auth.getCurrentUser().getUid()).child("squash").child("SportsRate").setValue(squash.getRating());
        data.child("users").child(auth.getCurrentUser().getUid()).child("squash").child("isAdded").setValue(squash.getisAdded());
        data.child("users").child(auth.getCurrentUser().getUid()).child("squash")
                .child("questionaireCompleted").setValue(squash.isQuestionaireCompleted());
        data.child("users").child(auth.getCurrentUser().getUid()).child("tt").child("SportsRate").setValue(TT.getRating());
        data.child("users").child(auth.getCurrentUser().getUid()).child("tt").child("isAdded").setValue(TT.getisAdded());
        data.child("users").child(auth.getCurrentUser().getUid()).child("tt")
                .child("questionaireCompleted").setValue(TT.isQuestionaireCompleted());
        data.child("users").child(auth.getCurrentUser().getUid()).child("badminton").child("SportsRate").setValue(badminton.getRating());
        data.child("users").child(auth.getCurrentUser().getUid()).child("badminton").child("isAdded").setValue(badminton.getisAdded());
        data.child("users").child(auth.getCurrentUser().getUid()).child("badminton")
                .child("questionaireCompleted").setValue(badminton.isQuestionaireCompleted());

    }
    public boolean analyse_password(String pass1, String pass2) {

        if (pass1.equals(pass2)) {
            if (pass.length() < 8) {
                Toast toast = Toast.makeText(getApplicationContext(), "Password too short(above 8 chars)", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 10);
                toast.show();
                return false;
            } else if (pass1.isEmpty() || pass2.isEmpty()) {
                Toast toast = Toast.makeText(getApplicationContext(), "Password not entered", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 10);
                toast.show();
                return false;
            } else {
                return true;
            }
        } else {
            AlertDialog.Builder dialogbox = new AlertDialog.Builder(com.example.harshgoel.nussportsmatch.SignUp.this);
            dialogbox.setMessage("Passwords Dont Match ")
                    .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = dialogbox.create();
            alert.show();
            return false;
        }
    }
    public void signin(View view){
        finish();
        Intent i=new Intent(this,SignIn.class);
        startActivity(i);

    }
    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            auth.removeAuthStateListener(mAuthListener);
        }
    }
}



