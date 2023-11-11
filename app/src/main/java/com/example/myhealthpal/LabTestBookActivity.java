package com.example.myhealthpal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LabTestBookActivity extends AppCompatActivity {

    EditText edname,edaddress,edcontact,edpincode;
    Button btnBooking, btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_test_book);

        edname = findViewById(R.id.editTextLTBFullName);
        edaddress = findViewById(R.id.editTextLTBAddress);
        edcontact = findViewById(R.id.editTextLTBContact);
        edpincode = findViewById(R.id.editTextLTBPincode);
        btnBooking = findViewById(R.id.buttonLTBBooking);
        btnBack = findViewById(R.id.buttonLTBBack);

        Intent intent = getIntent();
        String[] price = intent.getStringExtra("price").toString().split(java.util.regex.Pattern.quote(":"));
        String date = intent.getStringExtra("date");
        String time = intent.getStringExtra("time");
        Log.d("Date&Time are ", date + " and " + time);

        btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                String username = sharedPreferences.getString("username", "").toString();

                Database db = new Database(getApplicationContext(), "healthpal", null, 1);
                Log.d("Database opening ", "Database initialized");
                try {
                    Log.d("Try ", "trying...");
                    db.addOrder(username, edname.getText().toString(), edaddress.getText().toString(),
                            edcontact.getText().toString(), Integer.parseInt(edpincode.getText().toString()),
                            date.toString(), time.toString(), Float.parseFloat(price[1].toString()), "lab");
                    Log.d("Add ", "Added!!");
                    db.removeCart(username, "lab"); //why???
                    Toast.makeText(getApplicationContext(), "Your booking is done successfully", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(LabTestBookActivity.this, HomeActivity.class));
                } catch (Exception e){
                    Log.e("MyHealthPal", "Error during database operation: " + e.getMessage());
                    Toast.makeText(getApplicationContext(), "Error during database operation", Toast.LENGTH_LONG).show();
                }
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LabTestBookActivity.this, CartLabActivity.class));
            }
        });
    }
}