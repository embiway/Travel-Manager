package com.example.travel_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
//object holds details of images at a place
public class PlaceImageandInfo extends AppCompatActivity {

    String photourl;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_imageand_info);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            photourl = extras.getString("photoUrl");
        }
        imageView = (ImageView)findViewById(R.id.PlaceImage);
        //imageView.setImageResource(R.drawable.ic_launcher_background);
        Picasso.get().load(photourl).into(imageView);
    }
}