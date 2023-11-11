package com.example.myhealthpal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class EditProfileActivity extends AppCompatActivity {
    EditText etEmail, etFullName, etAddress, etPhone;
    Button btnUpdate, btnBack, btnExit;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        etEmail = findViewById(R.id.editTextEmail);
        etFullName = findViewById(R.id.editTextFullName);
        etAddress = findViewById(R.id.editTextAddress);
        etPhone = findViewById(R.id.editTextPhone);
        btnUpdate = findViewById(R.id.buttonUpdate);
        btnBack = findViewById(R.id.buttonBack);
        btnExit = findViewById(R.id.buttonExit);

        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username","").toString();

        // Display user information
        displayUserData(username);

        // Update button click listener
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get updated values
                String email = etEmail.getText().toString();
                String fullName = etFullName.getText().toString();
                String address = etAddress.getText().toString();
                String phone = etPhone.getText().toString();

                // Update user in the database
                updateUserInDatabase(username, email, fullName, address, phone);

                // Display a message
                Toast.makeText(EditProfileActivity.this, "User updated successfully", Toast.LENGTH_SHORT).show();
            }
        });
        //back to home activity
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditProfileActivity.this, HomeActivity.class));
            }
        });
        //Log out
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedpreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.apply();
                startActivity(new Intent(EditProfileActivity.this,LoginActivity.class));
            }
        });


    }

    // Helper method to display user information
    private void displayUserData(String username) {
        Database db = new Database(this, "healthpal", null, 1);
        ArrayList<String> userData = db.getUserData(username);

        if (!userData.isEmpty()) {
            String[] userFields = userData.get(0).split("\\$");
            etEmail.setText(userFields[1]);
            etFullName.setText(userFields[2]);
            etAddress.setText(userFields[3]);
            etPhone.setText(userFields[4]);
        }
    }

    // Helper method to update user in the database
    private void updateUserInDatabase(String username, String email, String fullName, String address, String phone) {
        Database db = new Database(this, "healthpal", null, 1);
        db.updateUser(username, email, fullName, address, phone);
    }
}