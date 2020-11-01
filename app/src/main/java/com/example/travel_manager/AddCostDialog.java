package com.example.travel_manager;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.travel_manager.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AddCostDialog extends Dialog implements
    android.view.View.OnClickListener{
        public Activity c;
        public Dialog d;
        public EditText place,review,cost;
        public Button save;
        public DatabaseReference databaseReference;
        public FirebaseDatabase firebaseDatabase;
        public FirebaseAuth auth;
        public String placeClicked;
    public AddCostDialog(Activity a) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.addcost_dialog);
            place = (EditText)findViewById(R.id.place);
            cost = (EditText)findViewById(R.id.cost);
            save = (Button)findViewById(R.id.save_button);
            auth = FirebaseAuth.getInstance();
            firebaseDatabase = FirebaseDatabase.getInstance();
            String uid = auth.getUid().toString();
            databaseReference = firebaseDatabase.getReference().child(uid);
            save.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.save_button:
                    String placeX = place.getText().toString();
                    String costX =  cost.getText().toString();
                    Toast.makeText(c, costX, Toast.LENGTH_SHORT).show();
                    if(placeX!="" && costX!="" ) {
                        SharedPreferences sharedPref = getContext().getSharedPreferences("myPrefs", Context.MODE_WORLD_READABLE);
                        placeClicked=sharedPref.getString("placeClicked",placeClicked);
                        String uid = auth.getUid().toString();
                        databaseReference = firebaseDatabase.getReference().child(uid+placeClicked+"cost");
                        databaseReference.push().setValue(new  Cost_info(placeX,costX));
                    }
                    dismiss();
                    break;
                default:
                    break;
            }
            dismiss();
        }
}

