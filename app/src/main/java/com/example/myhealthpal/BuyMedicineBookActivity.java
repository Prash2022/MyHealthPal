package com.example.myhealthpal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class BuyMedicineBookActivity extends AppCompatActivity {
    EditText edname, edaddress, edcontact, edpincode;
    Button btnBooking, btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_medicine_book);

        edname = findViewById(R.id.editTextBMBFullName);
        edaddress = findViewById(R.id.editTextBMBAddress);
        edcontact = findViewById(R.id.editTextBMBContact);
        edpincode = findViewById(R.id.editTextBMBPincode);
        btnBooking = findViewById(R.id.buttonBMBBooking);
        btnBack = findViewById(R.id.buttonBMBBack);

        SharedPreferences sharedpreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        String username = sharedpreferences.getString("username", "").toString();

        Intent intent = getIntent();
        String[] price = intent.getStringExtra("price").toString().split(java.util.regex.Pattern.quote(":"));
        String date = intent.getStringExtra("date");

        displayUserData(username);

        btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Database db = new Database(getApplicationContext(),"healthpal",null,1);
                try {
                    db.addOrder(username, edname.getText().toString(), edaddress.getText().toString(),
                            edcontact.getText().toString(), Integer.parseInt(edpincode.getText().toString()),
                            date.toString(), "", Float.parseFloat(price[1].toString()), "medicine");
                    db.removeCart(username, "medicine");
                    Toast.makeText(getApplicationContext(), "Your booking is done successfully", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(BuyMedicineBookActivity.this, HomeActivity.class));
                } catch (Exception e){
                    Log.e("MyHealthPal", "Error during database operation: " + e.getMessage());
                    Toast.makeText(getApplicationContext(), "Error during database operation", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BuyMedicineBookActivity.this, CartBuyMedicineActivity.class));
            }
        });
    }
    private void displayUserData(String username) {
        Database db = new Database(this, "healthpal", null, 1);
        ArrayList<String> userData = db.getUserData(username);

        if (!userData.isEmpty()) {
            String[] userFields = userData.get(0).split("\\$");
            if(!userFields[1].equals(" ")) {
                edname.setText(userFields[1]);
            }
            if(!userFields[2].equals(" ")) {
                edaddress.setText(userFields[2]);
            }
            if(!userFields[3].equals(" ")) {
                edcontact.setText(userFields[3]);
            }
        }
    }
}