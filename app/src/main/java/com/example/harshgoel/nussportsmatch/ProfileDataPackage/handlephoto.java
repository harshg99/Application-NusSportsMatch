package com.example.harshgoel.nussportsmatch.ProfileDataPackage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.harshgoel.nussportsmatch.AppLoginPage;
import com.example.harshgoel.nussportsmatch.R;


/**
 * Created by Harsh Goel on 7/7/2017.
 */

public class handlephoto extends AppCompatActivity {

    public Button clickphoto;
    public Button choosephoto;
    public Button confirm;
    public ImageView profilephoto;
    public Toolbar photobar;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photograph);
        initialiseUi();

        clickphoto.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               Intent c = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                               startActivityForResult(c, 0);
                                           }
                                       });
        /*Todo add the necessary code for selecting photo
        clickphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent s=new Intent(Mediastore.)
            }
        });*/
    }

    public void confirmClick(View v) {
        finish();
        Intent c = new Intent(handlephoto.this, AppLoginPage.class);
        startActivity(c);
    }

    public void initialiseUi(){
        choosephoto=(Button)findViewById(R.id.ChoosePhoto);
        clickphoto= (Button) findViewById(R.id.ClickPhoto);
        confirm=(Button)findViewById(R.id.Confirm);
        profilephoto=(ImageView)findViewById(R.id.ProfilePhoto);
        photobar=(Toolbar)findViewById(R.id.Photo_bar);
        setSupportActionBar(photobar);
    }
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        Bitmap m= (Bitmap) data.getExtras().get("data");
        profilephoto.setImageBitmap(m);
        profilephoto.setScaleType(ImageView.ScaleType.FIT_XY);

    }


}
