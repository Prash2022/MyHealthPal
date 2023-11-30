package com.example.myhealthpal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class BuyMedicineActivity extends AppCompatActivity {

    private String username;

    private int selectedPosition = ListView.INVALID_POSITION;

    private ArrayList<HashMap<String, String>> filteredList;

    private String[][] packages =
            {
                    {"Uprise-D3 1000IU Capsule", "", "", "", "50"},
                    {"HealthVit Chromium Picolinate 200mcg Capsule", "", "", "", "305"},
                    {"Vitamin B Complex Capsules", "", "", "", "448"},
                    {"Inlife Vitamin E Wheat Germ Oil Capsule", "", "", "", "539"},
                    {"Dolo 650 Tablet", "", "", "", "30"},
                    {"Crocin 650 Advance Tablet", "", "", "", "50"},
                    {"Strepsils Medicated Lozenges for Sore Throat", "", "", "", "40"},
                    {"Tata 1mg Calcium + Vitamin D3", "", "", "", "30"},
                    {"Feronia -XT Tablet", "", "", "", "130"},
            };
    private String[] package_details = {
            "Building and keeping the bones & teeth strong\n" +
                    "Reducing Fatigue/stress and muscular pains\n" +
                    "Boosting immunity and increasing resistance against infection",
            "Chromium is an essential trace mineral that plays an important role in helping insulin regulate blood glucose.",
            "Provides relief from vitamin B deficiencies\n" +
                    "Helps in formation of red blood cells\n" +
                    "Maintains healthy nervous system",
            "It promotes health as well as skin benefit.\n" +
                    "It helps reduce skin blemish and pigmentation.\n" +
                    "It act as safeguard the skin from the harsh UVA and UVB sun rays.",
            "Dolo 650 Tablet helps relieve pain and fever by blocking the release of certain chemical messengers responsible for fever and pain.",
            "Helps relieve fever and bring down a high temperature\n" +
                    "Suitable for people with a heart condition or high blood pressure",
            "Relieves the symptoms of a bacterial throat infection and soothes the recovery process\n" +
                    "Provides a warm and comforting feeling during sore throat",
            "Reduces the risk of calcium deficiency, Rickets, and Osteoporosis\n" +
                    "Promotes mobility and flexibility of joints",
            "Helps to reduce the iron deficiency due to chronic blood loss or low intake of iron"
    };

    HashMap<String,String> item;
    private int i;
    ArrayList list;
    SimpleAdapter sa;
    ListView lst;
    Button btnBack, btnGoToCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_medicine);

        lst = findViewById(R.id.listViewBM);
        btnBack = findViewById(R.id.buttonBMBack);
        btnGoToCart = findViewById(R.id.buttonBMGoToCart);
        lst = findViewById(R.id.listViewBM);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("AddToCart", "Item clicked at position: " + i);
                selectedPosition = i;
                addToCart();
            }
        });

        btnGoToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart();
                startActivity(new Intent(BuyMedicineActivity.this, CartBuyMedicineActivity.class));
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BuyMedicineActivity.this,HomeActivity.class));
            }
        });

        list = new ArrayList();
        for(int i = 0; i < packages.length; i++){
            item = new HashMap<String,String>();
            item.put( "line1", packages[i][0]);
            item.put( "line2", packages[i][1]);
            item.put( "line3", packages[i][2]);
            item.put( "line4", packages[i][3]);
            item.put( "line5", "Total Cost:" + packages[i][4] + "$");
            list.add( item );
        }

        sa = new SimpleAdapter(this, list,
                R.layout.multi_lines,
                new String[] { "line1","line2", "line3", "line4", "line5" },
                new int[] {R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e});
        lst.setAdapter(sa);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent(BuyMedicineActivity.this, BuyMedicineDetailsActivity.class);
                it.putExtra("text1",packages[i][0]);
                it.putExtra("text2",package_details[i]);
                it.putExtra("text3",packages[i][4]);
                startActivity(it);
            }
        });

        final SearchView searchView = findViewById(R.id.searchViewBM);


        filteredList = new ArrayList<>(list);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });
    }

    private void filterList(String keyword) {
        filteredList.clear();

        for (int i = 0; i < list.size(); i++) {
            Object originalItem = list.get(i);

            if (originalItem instanceof HashMap) {
                HashMap<String, String> originalHashMap = (HashMap<String, String>) originalItem;
                HashMap<String, String> newItem = new HashMap<>();

                for (String key : originalHashMap.keySet()) {
                    String value = originalHashMap.get(key);
                    newItem.put(key, value);
                }

                if (originalHashMap.get("line1").toLowerCase().contains(keyword.toLowerCase())) {
                   
                    newItem.put("originalIndex", String.valueOf(i));
                    filteredList.add(newItem);
                }
            }
        }

        sa = new SimpleAdapter(this, filteredList,
                R.layout.multi_lines,
                new String[]{"line1", "line2", "line3", "line4", "line5"},
                new int[]{R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e});

        lst.setAdapter(sa);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent(BuyMedicineActivity.this, BuyMedicineDetailsActivity.class);

                // 取得原始的索引
                int originalIndex = Integer.parseInt(filteredList.get(i).get("originalIndex"));

                it.putExtra("text1", packages[originalIndex][0]);
                it.putExtra("text2", package_details[originalIndex]);
                it.putExtra("text3", packages[originalIndex][4]);
                startActivity(it);
            }
        });
    }





    private void addToCart() {
        HashMap<String, String> selectedItem;


        if (filteredList != null && selectedPosition != ListView.INVALID_POSITION && selectedPosition < filteredList.size()) {
            selectedItem = filteredList.get(selectedPosition);
        } else {
            Log.e("AddToCart", "Invalid selectedPosition or filteredList is null.");
            Toast.makeText(BuyMedicineActivity.this, "Please select an item.", Toast.LENGTH_SHORT).show();
            return;
        }

        String productName = selectedItem.get("line1");
        float productPrice = Float.parseFloat(selectedItem.get("line5").replaceAll("[^\\d.]+|\\.(?!\\d)", ""));


        String username = "your_username";


        Database db = new Database(getApplicationContext(), "healthpal", null, 1);
        if (db.checkCart(username, productName) == 1) {
            Toast.makeText(BuyMedicineActivity.this, "Product Already Added", Toast.LENGTH_SHORT).show();
        } else {

            db.addCart(username, productName, productPrice, "medicine");
            Toast.makeText(BuyMedicineActivity.this, "Record Inserted to Cart", Toast.LENGTH_SHORT).show();


            Intent cartIntent = new Intent(BuyMedicineActivity.this, CartBuyMedicineActivity.class);
            cartIntent.putExtra("productName", productName);
            cartIntent.putExtra("productPrice", productPrice);
            startActivity(cartIntent);
        }
    }












}
