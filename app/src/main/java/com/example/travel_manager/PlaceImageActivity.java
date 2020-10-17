package com.example.travel_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class PlaceImageActivity extends AppCompatActivity {

    String photourl;
    ImageView imageView;
    Double latitude;
    Double longitude;
    private FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_image);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            photourl = extras.getString("photoUrl");
            latitude = extras.getDouble("latitude");
            longitude = extras.getDouble("longitude");
        }
        imageView = (ImageView)findViewById(R.id.PlaceImage);
        //imageView.setImageResource(R.drawable.ic_launcher_background);
        Picasso.get().load(photourl).into(imageView);
        Toast.makeText(this, Double.toString(latitude)  +" "+ Double.toString(longitude), Toast.LENGTH_SHORT).show();
        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Trip");
    }
    public  void openMapActivity(View view)
    {
        // auth.getUid();
        startActivity(new Intent(PlaceImageActivity.this,AddnewTripActivity.class));

    }



}