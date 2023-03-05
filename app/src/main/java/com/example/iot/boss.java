package com.example.iot;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class boss extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    EditText t1,t2;
    Button b1,b2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boss);
        t1 = findViewById(R.id.admin_chocolate_price);
        t2 = findViewById(R.id.admin_cake_price);
        b1= findViewById(R.id.admin_chocolate_updatebtn);
        b2= findViewById(R.id.admin_cake_updatebtn);
        //send data to firebase
        b1.setOnClickListener(v -> {
            String chocolate_price = t1.getText().toString();
            database.getReference().child("Product").child("Chocolate").setValue(chocolate_price);
            Toast.makeText(this, "Updated Price of Chocolate", Toast.LENGTH_SHORT).show();
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cake_price = t2.getText().toString();
                database.getReference().child("Product").child("Cake").setValue(cake_price);
                Toast.makeText(boss.this, "Updated Price of Cake", Toast.LENGTH_SHORT).show();
            }
        });
    }
}