package com.example.travel_manager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import mehdi.sakout.fancybuttons.FancyButton;
//Register Page
public class RegisterActivity extends AppCompatActivity {
    private EditText email,password;
    private FancyButton register;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        register=findViewById(R.id.register);
        auth=FirebaseAuth.getInstance();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text_email=email.getText().toString();
                String text_password=password.getText().toString();
                if(TextUtils.isEmpty(text_email)||TextUtils.isEmpty(text_password))
                {
                    Toast.makeText(RegisterActivity.this,"empty credentials",Toast.LENGTH_SHORT).show();
                }
                else if(text_password.length()<6)
                    Toast.makeText(RegisterActivity.this,"password is too short",Toast.LENGTH_SHORT).show();
                else
                {

                    registerUser(text_email,text_password);
                }
            }
        });
    }
    private void registerUser(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Registring user succesfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                }else
                    Toast.makeText(RegisterActivity.this,"registration failed",Toast.LENGTH_SHORT).show();

            }
        });
    }
}