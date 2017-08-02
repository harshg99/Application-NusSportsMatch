package com.example.harshgoel.nussportsmatch;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import com.example.harshgoel.nussportsmatch.Adapters.ProfileTabLayoutAdapter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


/**
 * Created by Harsh Goel on 6/25/2017.
 */
// the display after login is done
public class AppLoginPage extends AppCompatActivity {
    private FirebaseAuth author;

    private Toolbar tool_bar;
    private ViewPager pager;
    private TabLayout tab_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        author=FirebaseAuth.getInstance();
        if(author.getCurrentUser()==null)
        {
            finish();
            Intent intent=new Intent()
                    .setClass(com.example.harshgoel.nussportsmatch.AppLoginPage.this,SignUp.class);
            startActivity(intent);
        }

        setContentView(R.layout.profilelogin);
        initialiseUI();
    }



    public void initialiseUI(){

        tool_bar=(Toolbar)findViewById(R.id.profile_bar);
        setSupportActionBar(tool_bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        tab_layout=(TabLayout)findViewById(R.id.profile_tab);
        pager=(ViewPager)findViewById(R.id.Viewpager);
        pager.setAdapter(new ProfileTabLayoutAdapter(getSupportFragmentManager(),AppLoginPage.this));
        pager.setCurrentItem(2);
        tab_layout.setupWithViewPager(pager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.apploginpagemenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                AlertDialog.Builder exitalert = new AlertDialog.Builder(this);
                exitalert.setMessage("Do you want to exit?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AppLoginPage.this.finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                exitalert.show(
                );
                return true;
            case R.id.LogOutMenu:{
                author.signOut();
                this.finish();
                Intent intent=new Intent()
                        .setClass(this,SignIn.class);
                startActivity(intent);
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
