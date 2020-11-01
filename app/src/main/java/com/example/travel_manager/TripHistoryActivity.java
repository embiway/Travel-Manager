package com.example.travel_manager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
//Activity used to show Trips done by user
public class TripHistoryActivity extends AppCompatActivity {

    int TotalCost =0;
    int x=0;
    TextView totalcost;
    String placeClicked;
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                trip_info selectedItem = (trip_info) parent.getItemAtPosition(position);
                String place = selectedItem.placeName;
                TripDialogClass cdd = new TripDialogClass(TripHistoryActivity.this);
                SharedPreferences sharedPref = getSharedPreferences("myPrefs", Context.MODE_PRIVATE );
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("place", place);
                editor.commit();
                cdd.show();
            }

        });

        totalcost = (TextView)findViewById(R.id.totalcost);
        ArrayList<trip_info> list = new ArrayList<trip_info>();
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
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View v,
                                           int index, long arg3) {
                // TODO Auto-generated method stub
                trip_info selectedItem = (trip_info) arg0.getItemAtPosition(index);
                list.remove(index);
                adapter.notifyDataSetChanged();

                return true;
            }
        });
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                trip_info tripInfo = dataSnapshot.getValue(trip_info.class);
                try {
                    x =  Integer.valueOf(tripInfo.Cost);
                }
                catch (NumberFormatException e) {
                    System.out.println("Invalid String");
                }
                TotalCost+=x;
                String tcost = Integer.toString(TotalCost);
                totalcost.setText("Cost:"+tcost);
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