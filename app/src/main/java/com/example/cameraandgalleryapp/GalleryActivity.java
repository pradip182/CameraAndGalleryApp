package com.example.cameraandgalleryapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class GalleryActivity extends AppCompatActivity {

    ImageView imgclickable;
    Button btncamera;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK)
        {
            if (data==null)
            {
                Toast.makeText(this, "Please select an imnge", Toast.LENGTH_SHORT).show();
            }
        }
        Uri uri=data.getData();
        imgclickable.setImageURI(uri);
        String imagepath=getRealPathFromUri(uri);
        Toast.makeText(this, "Image Path is "+imagepath, Toast.LENGTH_SHORT).show();

    }

    private String getRealPathFromUri(Uri uri)
    {
        String[] projection={MediaStore.Images.Media.DATA};
        CursorLoader loader=new CursorLoader(getApplicationContext(),uri,projection,null,null,null);
        Cursor cursor=loader.loadInBackground();
        int colIndex=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result=cursor.getString(colIndex);
        cursor.close();
        return result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        imgclickable=findViewById(R.id.imgclickable);
        btncamera=findViewById(R.id.btncamera);

        btncamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(GalleryActivity.this,MainActivity.class);
                startActivity(intent);

            }
        });

        imgclickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadgallery();
            }
        });
    }

    private void loadgallery()
    {
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,0);
    }
}