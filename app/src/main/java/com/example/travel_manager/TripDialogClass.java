package com.example.travel_manager;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class TripDialogClass extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button photo,experience,cost;
    String place;

    public TripDialogClass(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPref = getContext().getSharedPreferences("myPrefs", Context.MODE_WORLD_READABLE);
        place=sharedPref.getString("place",place);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.trip_info_menu);
         photo= (Button) findViewById(R.id.photo);
         experience= (Button) findViewById(R.id.experience);
         cost =  (Button) findViewById(R.id.cost);
        photo.setOnClickListener(this);
         experience.setOnClickListener(this);
         cost.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.photo:
                Intent intent = new Intent(getContext(), PhotoActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("place",place);
                getContext().startActivity(intent);
                break;
            case R.id.experience:

                dismiss();
                break;
            case R.id.cost:
                Intent intent1 = new Intent(getContext(), CostActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.putExtra("place",place);
                getContext().startActivity(intent1);
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}