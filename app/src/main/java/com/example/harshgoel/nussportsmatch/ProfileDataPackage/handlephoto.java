package com.example.harshgoel.nussportsmatch.ProfileDataPackage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.harshgoel.nussportsmatch.AppLoginPage;
import com.example.harshgoel.nussportsmatch.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Harsh Goel on 7/7/2017.
 */

public class handlephoto extends AppCompatActivity {

    public Button clickphoto;
    public Button choosephoto;
    public Button confirm;
    public ImageView profilephoto;
    public Toolbar photobar;
    private Uri photo;
    private StorageReference photoref;
    private FirebaseAuth auth;
    private DatabaseReference ref;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photograph);
        initialiseUi();
        photoref= FirebaseStorage.getInstance().getReference();
        auth=FirebaseAuth.getInstance();
        photoref=photoref.child("Photos").child(auth.getCurrentUser().getUid());
        ref= FirebaseDatabase.getInstance().getReference();

        clickphoto.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               dispatchTakePictureIntent();
                                           }
                                       });

        choosephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent s=new Intent(Intent.ACTION_PICK);
                s.setType("image/*");
                s.putExtra(Intent.EXTRA_LOCAL_ONLY, false);
                startActivityForResult(s,1);
            }
        });
    }

    public void confirmClick(View v) {
        if(photo!=null){
            final ProgressDialog dialog=new ProgressDialog(handlephoto.this);
            photoref=photoref.child(photo.getLastPathSegment());
            dialog.setMessage("Uploading..");
            dialog.show();
            photoref.putFile(photo).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    dialog.cancel();
                    ref.child("users").child(auth.getCurrentUser().getUid()).child("profilephoto").setValue(photo.getLastPathSegment());
                    finish();
                    Intent c = new Intent(handlephoto.this, AppLoginPage.class);
                    startActivity(c);
                    Toast.makeText(handlephoto.this,"Profile Photo updated",Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    public void initialiseUi(){
        choosephoto=(Button)findViewById(R.id.ChoosePhoto);
        clickphoto= (Button) findViewById(R.id.ClickPhoto);
        confirm=(Button)findViewById(R.id.Confirm);
        profilephoto=(ImageView)findViewById(R.id.ProfilePhoto);
        photobar=(Toolbar)findViewById(R.id.Photo_bar);
        photo=null;
        setSupportActionBar(photobar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    protected void onActivityResult(int requestCode,int resultCode,Intent data){

            if (requestCode == 0 && resultCode == RESULT_OK){
                Picasso.with(handlephoto.this).load(photo).into(profilephoto);
            } else if (requestCode == 1 && resultCode == RESULT_OK) {
                if(data!=null) {
                Uri uri=data.getData();
                photo=uri;
                //Bitmap bitmap = MediaStore.Images.Media.getBitmap(handlephoto.this.getContentResolver(), uri);
                //use the bitmap as you like
                profilephoto.setImageURI(uri);
                profilephoto.setScaleType(ImageView.ScaleType.FIT_XY);
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                Intent c = new Intent(handlephoto.this, AppLoginPage.class);
                startActivity(c);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
    private String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(handlephoto.this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                photo=photoURI;
                startActivityForResult(takePictureIntent, 0);
            }
        }
    }


}
