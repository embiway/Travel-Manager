package com.example.travel_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddnewTripActivity extends AppCompatActivity {
    EditText place,review,cost;
    Button save;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnew_trip);
        place = (EditText)findViewById(R.id.place);
        cost = (EditText)findViewById(R.id.cost);
        review = (EditText)findViewById(R.id.review);
        save = (Button)findViewById(R.id.save_button);
        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        String uid = auth.getUid().toString();
        databaseReference = firebaseDatabase.getReference().child(uid);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String placeX = place.getText().toString();
                 String costX =  cost.getText().toString();
                 String reviewX = review.getText().toString();
                 place.setText("");
                 cost.setText("");
                 review.setText("");
                 String vicinity="";
                 String latitude = "";
                 String longitude = "";
                 trip_info obj = new trip_info(placeX,costX,reviewX,vicinity,latitude,longitude);
                 if(placeX!="" && costX!="" && reviewX!="")
                databaseReference.push().setValue(obj);
            }
        });

    }
}