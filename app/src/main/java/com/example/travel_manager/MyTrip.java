package com.example.travel_manager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class MyTrip extends AppCompatActivity {
  private Button add,view,share;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_trip);
         add=findViewById(R.id.add);
          view=findViewById(R.id.view);
          share =(Button) findViewById(R.id.share);
          auth = FirebaseAuth.getInstance();
//            Log.i("cdsfdregr" ,auth.getCurrentUser().getEmail().toString());
//        ArrayList<TourismPlace> adapter = (ArrayList<TourismPlace>) getIntent().getSerializableExtra("list");
//          add.setOnClickListener(new View.OnClickListener() {
//              @Override
//              public void onClick(View v) {
//                  HashMap<String, Object> mpp = new HashMap<>();
//                  for(int i=0;i<adapter.size();i++) {
//
//                      mpp.put("PLACE", adapter.get(i).name);
//                      Log.d("hello",adapter.get(i).name);
//                  }
//                      FirebaseDatabase.getInstance().getReference().child("MyTrip").setValue(mpp);
//
//              }
//          });
          view.setOnClickListener(new View.OnClickListener() {


              @Override
              public void onClick(View v) {
                  startActivity(new Intent(MyTrip.this,TripHistoryActivity.class));

              }
          });

          add.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  startActivity(new Intent(MyTrip.this,AddnewTripActivity.class));
              }
          });
          share.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  startActivity(new Intent(MyTrip.this,ShareExperience.class));
              }
          });

    }
}