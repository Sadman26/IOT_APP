package com.example.iot;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class admin extends AppCompatActivity {
    EditText email, password;
    Button login;
    FirebaseAuth fAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        fAuth= FirebaseAuth.getInstance();
        email= findViewById(R.id.admin_email);
        password= findViewById(R.id.admin_password);
        login= findViewById(R.id.admin_loginbtn);
        //make firebaes auth
        login.setOnClickListener(v -> {
            String email1= email.getText().toString();
            String password1= password.getText().toString();
            if(email1.isEmpty() || password1.isEmpty()){
                Toast.makeText(admin.this, "Please enter all the details", Toast.LENGTH_SHORT).show();
            }
            else{
                fAuth.signInWithEmailAndPassword(email1, password1).addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Toast.makeText(admin.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), boss.class));
                    }
                    else{
                        Toast.makeText(admin.this, "Error! "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}