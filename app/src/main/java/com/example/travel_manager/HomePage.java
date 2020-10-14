package com.example.travel_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class HomePage extends AppCompatActivity {

    EditText text ;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        text = (EditText)findViewById(R.id.textview);
        button = (Button)findViewById(R.id.button);
    }
    public void openMapActivity(View view){
        String city = String.valueOf(text.getText());
        city = city.toLowerCase();
        Intent intent = new Intent(this , MainActivity.class);
        intent.putExtra("CITY_NAME", city);
        startActivity(intent);
    }
}