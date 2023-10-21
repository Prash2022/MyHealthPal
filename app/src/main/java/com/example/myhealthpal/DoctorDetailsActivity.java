package com.example.myhealthpal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class DoctorDetailsActivity extends AppCompatActivity {
    private String[][] doctor_details1 =
            {
                    {"Doctor Name : Nadiia Marchynska", "Hospital Address: Burnaby", "Exp: 5yrs", "Mobile No: 2365895674", "100"},
                    {"Doctor Name : Prashneel Chand", "Hospital Address: New West", "Exp: 3yrs", "Mobile No: 2365895674", "200"},
                    {"Doctor Name : Ting Nan Hsia", "Hospital Address: Vancouver", "Exp: 10yrs", "Mobile No: 2365895674", "300"},
                    {"Doctor Name : Xing Liu", "Hospital Address: Surrey", "Exp: 12yrs", "Mobile No: 2365895674", "400"},
                    {"Doctor Name : Lou Jenny", "Hospital Address: Richmond", "Exp: 15yrs", "Mobile No: 2365895674", "500"}
            };
    private String[][] doctor_details2 =
            {
                    {"Doctor Name : Nadiia Marchynska", "Hospital Address: Burnaby", "Exp: 5yrs", "Mobile No: 2365895674", "600"},
                    {"Doctor Name : Prashneel Chand", "Hospital Address: New West", "Exp: 3yrs", "Mobile No: 2365895674", "700"},
                    {"Doctor Name : Ting Nan Hsia", "Hospital Address: Vancouver", "Exp: 10yrs", "Mobile No: 2365895674", "800"},
                    {"Doctor Name : Xing Liu", "Hospital Address: Surrey", "Exp: 12yrs", "Mobile No: 2365895674", "900"},
                    {"Doctor Name : Lou Jenny", "Hospital Address: Richmond", "Exp: 15yrs", "Mobile No: 2365895674", "1000"}
            };

    private String[][] doctor_details3 =
            {
                    {"Doctor Name : Nadiia Marchynska", "Hospital Address: Burnaby", "Exp: 5yrs", "Mobile No: 2365895674", "1100"},
                    {"Doctor Name : Prashneel Chand", "Hospital Address: New West", "Exp: 3yrs", "Mobile No: 2365895674", "1200"},
                    {"Doctor Name : Ting Nan Hsia", "Hospital Address: Vancouver", "Exp: 10yrs", "Mobile No: 2365895674", "1300"},
                    {"Doctor Name : Xing Liu", "Hospital Address: Surrey", "Exp: 12yrs", "Mobile No: 2365895674", "1400"},
                    {"Doctor Name : Lou Jenny", "Hospital Address: Richmond", "Exp: 15yrs", "Mobile No: 2365895674", "1500"}
            };
    private String[][] doctor_details4 =
            {
                    {"Doctor Name : Nadiia Marchynska", "Hospital Address: Burnaby", "Exp: 5yrs", "Mobile No: 2365895674", "1600"},
                    {"Doctor Name : Prashneel Chand", "Hospital Address: New West", "Exp: 3yrs", "Mobile No: 2365895674", "1700"},
                    {"Doctor Name : Ting Nan Hsia", "Hospital Address: Vancouver", "Exp: 10yrs", "Mobile No: 2365895674", "1800"},
                    {"Doctor Name : Xing Liu", "Hospital Address: Surrey", "Exp: 12yrs", "Mobile No: 2365895674", "1900"},
                    {"Doctor Name : Lou Jenny", "Hospital Address: Richmond", "Exp: 15yrs", "Mobile No: 2365895674", "2000"}
            };
    private String[][] doctor_details5 =
            {
                    {"Doctor Name : Nadiia Marchynska", "Hospital Address: Burnaby", "Exp: 5yrs", "Mobile No: 2365895674", "2100"},
                    {"Doctor Name : Prashneel Chand", "Hospital Address: New West", "Exp: 3yrs", "Mobile No: 2365895674", "2200"},
                    {"Doctor Name : Ting Nan Hsia", "Hospital Address: Vancouver", "Exp: 10yrs", "Mobile No: 2365895674", "2300"},
                    {"Doctor Name : Xing Liu", "Hospital Address: Surrey", "Exp: 12yrs", "Mobile No: 2365895674", "2400"},
                    {"Doctor Name : Lou Jenny", "Hospital Address: Richmond", "Exp: 15yrs", "Mobile No: 2365895674", "2500"}
            };
    TextView tv;
    Button btn;
    String[][] doctor_details = {};
    ArrayList list;
    SimpleAdapter sa;
    HashMap<String, String> item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);

        tv = findViewById(R.id.textViewCart);
        btn = findViewById(R.id.buttonCartCheckout);

        Intent it = getIntent();
        String title = it.getStringExtra("title");
        tv.setText(title);
        if(title.compareTo("Family Physicians")==0)
            doctor_details = doctor_details1;
        else if(title.compareTo("Dietician")==0)
            doctor_details = doctor_details2;
        else if(title.compareTo("Dentist")==0)
            doctor_details = doctor_details3;
        else if(title.compareTo("Surgeon")==0)
            doctor_details = doctor_details4;
        else
            doctor_details = doctor_details5;

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DoctorDetailsActivity.this, FindDoctorActivity.class));
            }
        });
    list = new ArrayList();
    for(int i = 0; i < doctor_details.length; i++){
        item = new HashMap<String, String>();
        item.put("Line1", doctor_details[i][0]);
        item.put("Line2", doctor_details[i][1]);
        item.put("Line3", doctor_details[i][2]);
        item.put("Line4", doctor_details[i][3]);
        item.put("Line5", "Cons Fees: " +doctor_details[i][4] +"$");
        list.add(item);
    }
    sa = new SimpleAdapter(this,list,
            R.layout.multi_lines,
            new String[]{"Line1","Line2","Line3","Line4","Line5"},
            new int[]{R.id.line_a,R.id.line_b,R.id.line_c,R.id.line_d,R.id.line_e}
            );
    ListView lst = findViewById(R.id.listViewLT);
    lst.setAdapter(sa);
    lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent it = new Intent(DoctorDetailsActivity.this, BookAppointmentActivity.class);
            it.putExtra("text1", title);
            it.putExtra("text2", doctor_details[i][0]);
            it.putExtra("text3", doctor_details[i][1]);
            it.putExtra("text4", doctor_details[i][3]);
            it.putExtra("text5", doctor_details[i][4]);
            startActivity(it);
        }
    });
    }
}