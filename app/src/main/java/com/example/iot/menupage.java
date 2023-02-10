package com.example.iot;
import androidx.appcompat.app.AppCompatActivity;
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
    private CheckBox kitkat,chips,juice;
    private EditText moneyinput;
    ImageView paybtn;
    String option;
    String time;
    boolean paymentstatus;
    int shortMoney;
    int kitkatprice = 35;
    int chipsprice = 10;
    int juiceprice = 25;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menupage);
        db = FirebaseDatabase.getInstance();
        kitkat = findViewById(R.id.kitkat);
        chips = findViewById(R.id.chips);
        juice = findViewById(R.id.juice);
        moneyinput = findViewById(R.id.moneyinput);
        paybtn = findViewById(R.id.paybtn);
        paybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveData();
            }
        });
    }
    public void SaveData(){
        time=java.text.DateFormat.getDateTimeInstance().format(java.util.Calendar.getInstance().getTime());
        if(kitkat.isChecked()){
            option = "Kitkat";
            //disable other checkboxes
            chips.setEnabled(false);
            juice.setEnabled(false);
        }
        else if(chips.isChecked()){
            option = "Chips";
            //disable other checkboxes
            kitkat.setEnabled(false);
            juice.setEnabled(false);
        }
        else if(juice.isChecked()){
            option = "Juice";
            //disable other checkboxes
            kitkat.setEnabled(false);
            chips.setEnabled(false);
        }
        String money = moneyinput.getText().toString();
        if(money.isEmpty()){
            moneyinput.setError("Please enter money");
            moneyinput.requestFocus();
            return;
        }
        int moneyint = Integer.parseInt(money);
        if(option.equals("Kitkat")){
            if(moneyint >= kitkatprice){
                paymentstatus = true;
                //Toast
                Toast.makeText(this, "😎 Payment Successful", Toast.LENGTH_SHORT).show();
                moneyinput.setText("");
                kitkat.setEnabled(true);
            }
            else{
                shortMoney = chipsprice - moneyint;
                paymentstatus = false;
                Toast.makeText(this, "😢 Insufficient money.Add Tk "+shortMoney, Toast.LENGTH_SHORT).show();
            }
        }
        else if(option.equals("Chips")){
            if(moneyint >= chipsprice){
                paymentstatus = true;
                Toast.makeText(this, "😎 Payment Successful", Toast.LENGTH_SHORT).show();
                moneyinput.setText("");
                chips.setEnabled(true);
            }
            else{
                shortMoney = chipsprice - moneyint;
                paymentstatus = false;
                Toast.makeText(this, "😢 Insufficient money.Add Tk "+shortMoney, Toast.LENGTH_SHORT).show();
            }
        }
        else if(option.equals("Juice")){
            if(moneyint >= juiceprice){
                paymentstatus = true;
                Toast.makeText(this, "😎 Payment Successful", Toast.LENGTH_SHORT).show();
                moneyinput.setText("");
                juice.setEnabled(true);
            }
            else{
                shortMoney = chipsprice - moneyint;
                paymentstatus = false;
                Toast.makeText(this, "😢 Insufficient money.Add Tk "+shortMoney, Toast.LENGTH_SHORT).show();
            }
        }

       db.getReference().child("Payment").child(time).child("Option").setValue(option);
         db.getReference().child("Payment").child(time).child("Money").setValue(money);
            db.getReference().child("Payment").child(time).child("Payment Status").setValue(paymentstatus);

    }
}