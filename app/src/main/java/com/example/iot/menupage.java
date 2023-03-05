package com.example.iot;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.iot.databinding.ActivityMenupageBinding;
import com.google.firebase.database.FirebaseDatabase;
public class menupage extends AppCompatActivity {
    FirebaseDatabase db;
    private CheckBox t1,t2;
    com.google.android.material.textview.MaterialTextView tx1,tx2;
    private EditText moneyinput;
    ImageView paybtn;
    String option;
    String time;
    int shortMoney;
    int chocoprice;
    int cakeprice;
    String stepp = "0";
    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menupage);
        db = FirebaseDatabase.getInstance();
        t1 = findViewById(R.id.chocobox);
        t2 = findViewById(R.id.cakebox);
        tx1 = findViewById(R.id.chocolateprice);
        tx2 = findViewById(R.id.cakeprice);
        moneyinput = findViewById(R.id.moneyinput);
        paybtn = findViewById(R.id.paybtn);
        //get chocoprice and cakeprice from firebase
        db.getReference().child("Product").child("Chocolate").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                chocoprice = Integer.parseInt(task.getResult().getValue().toString());
            }
        });
        db.getReference().child("Product").child("Cake").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                cakeprice = Integer.parseInt(task.getResult().getValue().toString());
            }
        });
        db.getReference().child("Product").child("Chocolate").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                tx1.setText(task.getResult().getValue().toString()+" Taka");
            }
        });
        db.getReference().child("Product").child("Cake").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                tx2.setText(task.getResult().getValue().toString()+" Taka");
            }
        });
        paybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveData();
            }
        });
    }
    public void fireupload(String option,String money){
        db.getReference().child("Payment").child("Step").setValue(stepp);
        db.getReference().child("Payment").child("Option").setValue(option);
        db.getReference().child("Payment").child("Money").setValue(money);
        int num = 1; // the number to add to the string
        int value = Integer.parseInt(stepp) + num;
        stepp = String.valueOf(value);
    }
    public void storeupload(String option,String money){
        db.getReference().child("Purchase").child(time).child("Option").setValue(option);
        db.getReference().child("Purchase").child(time).child("Money").setValue(money);
    }
    public void SaveData(){
        time=java.text.DateFormat.getDateTimeInstance().format(java.util.Calendar.getInstance().getTime());
        if(t1.isChecked()){
            option = "Kitkat";
        }
        else if(t2.isChecked()){
            option = "Cake";
        }
        if(!t1.isChecked() && !t2.isChecked()){
            Toast.makeText(this, "Please Select an Item 🤨", Toast.LENGTH_SHORT).show();
            return;
        }
        String money = moneyinput.getText().toString();
        if(money.isEmpty()){
            moneyinput.setError("Please enter money");
            moneyinput.requestFocus();
            return;
        }
        int moneyint = Integer.parseInt(money);
        if(option.equals("Kitkat")){
            if(moneyint >=chocoprice ){
                //Toast
                Toast.makeText(this, "😎 Payment Successful", Toast.LENGTH_SHORT).show();
                moneyinput.setText("");
                t1.setEnabled(true);
               fireupload(option,money);
               storeupload(option,money);
            }
            else{
                shortMoney = chocoprice - moneyint;
                Toast.makeText(this, "😢 Insufficient money.Add Tk "+shortMoney, Toast.LENGTH_SHORT).show();
                shortMoney = 0;
            }
        }
        else if(option.equals("Cake")){
            if(moneyint >= cakeprice){
                Toast.makeText(this, "😎 Payment Successful", Toast.LENGTH_SHORT).show();
                moneyinput.setText("");
                 t2.setEnabled(true);
                fireupload(option,money);
                storeupload(option,money);
            }
            else{
                shortMoney = cakeprice - moneyint;
                Toast.makeText(this, "😢 Insufficient money.Add Tk "+shortMoney, Toast.LENGTH_SHORT).show();
                shortMoney = 0;
            }
        }

    }
}