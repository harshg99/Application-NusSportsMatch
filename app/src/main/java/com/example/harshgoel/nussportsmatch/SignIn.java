package com.example.harshgoel.nussportsmatch;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.harshgoel.nussportsmatch.Connection.ConnectionManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.content.ContentValues.TAG;

public class SignIn extends AppCompatActivity {
    public EditText password;
    public EditText email;
    public RelativeLayout layout;
    public Button Login;
    public Button sign_up;
    public ImageView icon;
    public ProgressDialog progressDialog;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null && user.isEmailVerified()) {
            // User is signed in
            Toast.makeText(getApplicationContext(), "Successfully registered", Toast.LENGTH_LONG).show();
            Intent intent=new Intent()
                    .setClass(com.example.harshgoel.nussportsmatch.SignIn.this,AppLoginPage.class);
            startActivity(intent);
            com.example.harshgoel.nussportsmatch.SignIn.this.finish();



        } else {
            // User is signed out
            Toast.makeText(getApplicationContext(), "Please Sign In", Toast.LENGTH_LONG).show();
            Log.d(TAG, "onAuthStateChanged:signed_out");
        }
        setContentView(R.layout.activity_sign_in);
        initialiseUI();

    }

    //check the profilelogin for the entered data
    public void checkprofile(View view){
        boolean flag=false;
        String textemail=email.getText().toString();
        String getpass=password.getText().toString();
        //local data compared
        if(TextUtils.isEmpty(textemail)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(getpass)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage("Signing In");
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(textemail, getpass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            progressDialog.cancel();
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast alert=Toast.makeText(SignIn.this, "Incorrect Username/Password",
                                    Toast.LENGTH_SHORT);
                            alert.setGravity(Gravity.CENTER,0,10);
                            alert.show();
                            finish();
                            Intent intent = new Intent(SignIn.this, SignIn.class);
                            startActivity(intent);

                        }
                        else{
                            FirebaseUser user=mAuth.getCurrentUser();
                            progressDialog.cancel();
                            if(user.isEmailVerified()) {
                                Toast alert = Toast.makeText(SignIn.this, "SignIn Successful",
                                        Toast.LENGTH_SHORT);
                                alert.setGravity(Gravity.CENTER, 0, 10);
                                alert.show();
                                finish();
                                Intent intent = new Intent(SignIn.this, AppLoginPage.class);
                                startActivity(intent);
                            }
                            else{
                                layout.setAlpha((float)0.5);
                                Snackbar.make(layout,"Please Verify Email",Snackbar.LENGTH_INDEFINITE)
                                        .setAction("OK", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                SignIn.this.finish();
                                            }
                                        }).show();
                            }

                        }

                        // ...
                    }
                });

    }


    public void signup(View view){
        finish();
        Intent i = new Intent(this, SignUp.class);
        startActivity(i);

    }
    //initialising the UI elements
    public void initialiseUI(){
        layout=(RelativeLayout)findViewById(R.id.signinlayout);
        password=(EditText)findViewById(R.id.password);
        email=(EditText)findViewById(R.id.email);
        Login=(Button)findViewById(R.id.logout);
        new ConnectionManager(layout,SignIn.this).execute();
        sign_up=(Button)findViewById(R.id.signup);
        icon = (ImageView) findViewById(R.id.imageView2);
        progressDialog=new ProgressDialog(SignIn.this);
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

    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
/*To confirm if tool bar is removed
*/
//Accessing the network state


}