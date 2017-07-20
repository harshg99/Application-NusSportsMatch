package com.example.harshgoel.nussportsmatch;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import com.example.harshgoel.nussportsmatch.Adapters.ProfileTabLayoutAdapter;

import com.google.firebase.auth.FirebaseAuth;


/**
 * Created by Harsh Goel on 6/25/2017.
 */
// the display after login is done
public class AppLoginPage extends AppCompatActivity {
    private Button logOut;
    private FirebaseAuth author;
    private Button sportsBut;
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
        /*sportsBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), Sports.class);
                startActivity(i);
            }
        });

*/
        setContentView(R.layout.profilelogin);
        initialiseUI();

        /*profileBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ProfileData.class);
                startActivity(i);
            }
        });*/

        /*chatBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), Chats.class);
                startActivity(i);
            }
        });*/

        /*calendarBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), Calendar.class);
                startActivity(i);

           }
        });
        */
    }



    public void initialiseUI(){
        logOut= (Button) findViewById(R.id.logout);
        tool_bar=(Toolbar)findViewById(R.id.profile_bar);
        setSupportActionBar(tool_bar);
        tab_layout=(TabLayout)findViewById(R.id.profile_tab);
        pager=(ViewPager)findViewById(R.id.Viewpager);
        pager.setAdapter(new ProfileTabLayoutAdapter(getSupportFragmentManager(),AppLoginPage.this));
        pager.setCurrentItem(2);
        tab_layout.setupWithViewPager(pager);
    }
}
