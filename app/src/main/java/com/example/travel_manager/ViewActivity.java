package com.example.travel_manager;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
//Not implemented yet
public class ViewActivity extends AppCompatActivity {
   private ListView listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         listview=findViewById(R.id.list_view);
        setContentView(R.layout.activity_view);
        final ArrayList<String> list=new ArrayList<>();

        final ArrayAdapter arrayAdapter=new ArrayAdapter<String>(ViewActivity.this,R.layout.list_item,list);
         listview.setAdapter(arrayAdapter);
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("MyTrip");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    Information information=snapshot.getValue(Information.class);
                    String txt=information.getPlace();
                    list.add(txt);
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}