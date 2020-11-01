package com.example.travel_manager;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.travel_manager.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddActivityDialogClass extends Dialog implements
        android.view.View.OnClickListener {
    public Activity c;
    public Dialog d;
    public EditText place,review,cost;
    public Button save;
    public DatabaseReference databaseReference;
    public  FirebaseDatabase firebaseDatabase;
    public  FirebaseAuth auth;
    public AddActivityDialogClass(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.addactivity_dialog);
        place = (EditText)findViewById(R.id.place);
        cost = (EditText)findViewById(R.id.cost);
        review = (EditText)findViewById(R.id.review);
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
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}

