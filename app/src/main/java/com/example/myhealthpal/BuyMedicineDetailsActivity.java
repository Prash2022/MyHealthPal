package com.example.myhealthpal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BuyMedicineDetailsActivity extends AppCompatActivity {

    TextView tvSubTitle,tvTotalCost;
    EditText edDetails;
    Button btnBack,btnAddToCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_medicine_details);

        tvSubTitle = findViewById(R.id.textViewBMDSubTitle);
        edDetails = findViewById(R.id.editTextBMDMultiLine);
        edDetails.setKeyListener(null);
        tvTotalCost = findViewById(R.id.textViewBMDTotalCost);
        btnBack = findViewById(R.id.buttonBMDBack);
        btnAddToCart = findViewById(R.id.buttonBMDAddToCart);

        Intent intent = getIntent();
        tvSubTitle.setText(intent.getStringExtra("text1"));
        edDetails.setText(intent.getStringExtra("text2"));
        tvTotalCost.setText("Total Cost : " + intent.getStringExtra("text3") + "$");

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BuyMedicineDetailsActivity.this, BuyMedicineActivity.class));
            }
        });


        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedpreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                String username = sharedpreferences.getString("username", "").toString();
                String product = tvSubTitle.getText().toString();
                float price = Float.parseFloat(intent.getStringExtra("text3").toString());

                Database db = new Database(getApplicationContext(), "healthpal", null, 1);

                if (db.checkCartWithType(username, product, "medicine") == 1) {
                    Toast.makeText(getApplicationContext(), "Product Already Added", Toast.LENGTH_SHORT).show();
                } else {
                    db.addCart(username, product, price, "medicine");
                    Toast.makeText(getApplicationContext(), "Record Inserted to Cart", Toast.LENGTH_SHORT).show();

                    // Pass product information to CartBuyMedicineActivity
                    Intent cartIntent = new Intent(BuyMedicineDetailsActivity.this, CartBuyMedicineActivity.class);
                    cartIntent.putExtra("productName", product);
                    cartIntent.putExtra("productPrice", price);
                    startActivity(cartIntent);
                }
            }
        });
    }


}