package com.example.travel_manager;

import android.content.Intent;
import android.os.Bundle;
// <<<<<<< tripupdate
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomePage extends AppCompatActivity {

    private Button register;
    private Button login;
// =======
// import android.util.Log;
// import android.view.View;
// import android.widget.Button;
// import android.widget.EditText;

// import androidx.appcompat.app.AppCompatActivity;

// public class HomePage extends AppCompatActivity {

//     EditText text ;
//     Button button;
// >>>>>>> master
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
// <<<<<<< tripupdate
       // DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("MyTrip");
        //reference.push().setValue(new trip_info("dsfeg"));
        register=findViewById(R.id.register);
        login=findViewById(R.id.login);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePage.this,RegisterActivity.class));
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePage.this,LoginActivity.class));
                finish();
            }
        });

// =======
//         text = (EditText)findViewById(R.id.textview);
//         button = (Button)findViewById(R.id.button);
//     }
//     public void openMapActivity(View view){
//         String city = String.valueOf(text.getText());
//         city = city.toLowerCase();
//         Log.d("OpenMap" , "MAp opened");
//         Intent intent = new Intent(this , MainActivity.class);
//         intent.putExtra("CITY_NAME", city);
//         startActivity(intent);
// >>>>>>> master
    }
}