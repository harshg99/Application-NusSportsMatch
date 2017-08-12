package com.example.harshgoel.nussportsmatch.ProfileDataPackage;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.harshgoel.nussportsmatch.AppLoginPage;
import com.example.harshgoel.nussportsmatch.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Harsh Goel on 8/1/2017.
 */

public class EditContent extends AppCompatActivity {

    private TextView header;
    private EditText content;
    private Button save;
    private Toolbar toolbar;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_basic);
        header=(TextView)findViewById(R.id.Contenthead);
        content=(EditText)findViewById(R.id.Content);
        save=(Button)findViewById(R.id.save);
        toolbar=(Toolbar)findViewById(R.id.edit_content_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        header.setText(getIntent().getStringExtra("Content"));
        content.setHint(getIntent().getStringExtra("Content"));
        FirebaseAuth auth=FirebaseAuth.getInstance();
        final DatabaseReference ref= FirebaseDatabase.getInstance().getReference()
                .child("users").child(auth.getCurrentUser().getUid());

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newfield=content.getText().toString().trim();
                if(!newfield.isEmpty()) {
                    Map<String, Object> field = new HashMap<String, Object>();
                    field.put(getIntent().getStringExtra("Content").toLowerCase(),newfield );
                    ref.updateChildren(field);
                    finish();
                    startActivity(new Intent(EditContent.this,EditProfile.class));
                }
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                startActivity(new Intent(EditContent.this,EditProfile.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
