package com.example.travel_manager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CostActivity extends AppCompatActivity {
    int TotalCost =0;
    int x=0;
    TextView totalcost;
    String placeClicked;
    ListView listView;
    ImageView imageView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth auth;
    ChildEventListener childEventListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            placeClicked = extras.getString("place");
        }
        listView = (ListView) findViewById(R.id.listview);
        imageView = (ImageView)findViewById(R.id.additem);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }

        });

        totalcost = (TextView)findViewById(R.id.totalcost);
        ArrayList<Cost_info> list = new ArrayList<Cost_info>();
        //  Toast.makeText(this, Integer.toString(list.size()), Toast.LENGTH_SHORT).show();
//        TourismPlace u1 = new TourismPlace("icon","Delhi","near taj hotel","https://developers.google.com/places/web-service/supported_types#table2");
//        TourismPlace u2 = new TourismPlace("icon","Mumbai","near taj hotel","https://developers.google.com/places/web-service/supported_types#table2");
//        list.add(u1);
//        list.add(u2);
        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        String uid = auth.getUid().toString();
        databaseReference = firebaseDatabase.getReference().child(uid+placeClicked+"cost");
        //Cost_info u = new Cost_info("placeX","costX");
        // list.add(u);
       Cost_infoAdapter adapter = new Cost_infoAdapter(this,list);
        listView.setAdapter(adapter);
        //adapter.add(u);
        adapter.notifyDataSetChanged();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CostActivity.this, "fedw", Toast.LENGTH_SHORT).show();
                Context context = CostActivity.this;
                AddCostDialog cdd = new AddCostDialog(CostActivity.this);
                SharedPreferences sharedPref = getSharedPreferences("myPrefs", Context.MODE_PRIVATE );
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("placeClicked", placeClicked);
                editor.commit();
                cdd.show();
            }
        });
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Cost_info costInfo = dataSnapshot.getValue(Cost_info.class);
                try {
                    x =  Integer.valueOf(costInfo.Cost);
                }
                catch (NumberFormatException e) {
                    System.out.println("Invalid String");
                }
                TotalCost+=x;
                String tcost = Integer.toString(TotalCost);
                totalcost.setText("Cost:"+tcost);
                adapter.add(costInfo);
                //adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

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