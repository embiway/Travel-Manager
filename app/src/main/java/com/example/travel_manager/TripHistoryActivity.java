package com.example.travel_manager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
//Activity used to show Trips done by user
public class TripHistoryActivity extends AppCompatActivity {

    ListView listView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth auth;
    ChildEventListener childEventListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_history);
        listView = (ListView) findViewById(R.id.listview);
        ArrayList<trip_info> list = new ArrayList<trip_info>();
        //  Toast.makeText(this, Integer.toString(list.size()), Toast.LENGTH_SHORT).show();
//        TourismPlace u1 = new TourismPlace("icon","Delhi","near taj hotel","https://developers.google.com/places/web-service/supported_types#table2");
//        TourismPlace u2 = new TourismPlace("icon","Mumbai","near taj hotel","https://developers.google.com/places/web-service/supported_types#table2");
//        list.add(u1);
//        list.add(u2);
        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        String uid = auth.getUid().toString();
        databaseReference = firebaseDatabase.getReference().child(uid);
        trip_info u = new trip_info("placeX","costX","reviewX","vicinity","70","80");
       // list.add(u);
        Trip_InfoAdapter adapter = new Trip_InfoAdapter(this,list);
        listView.setAdapter(adapter);
        //adapter.add(u);
        adapter.notifyDataSetChanged();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                               trip_info tripInfo = dataSnapshot.getValue(trip_info.class);
                                adapter.add(tripInfo);
                                //adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                trip_info tripInfo = dataSnapshot.getValue(trip_info.class);
                adapter.remove(tripInfo);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //databaseReference.addChildEventListener(childEventListener);
    }
}