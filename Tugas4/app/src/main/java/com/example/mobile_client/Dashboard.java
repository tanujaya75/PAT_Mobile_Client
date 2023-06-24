package com.example.mobile_client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Dashboard extends AppCompatActivity {
    public ListView lv;
    public Button dft,lgt,bkg;
    public Intent i3,i4,i7;
    public String Roles;
    public String RESPONDED;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        lv = (ListView) findViewById(R.id.lv);
        lgt = findViewById(R.id.Logout);
        dft = findViewById(R.id.Kendaraan);
        bkg = findViewById(R.id.Booking);
        lgt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Executor executor = Executors.newSingleThreadExecutor();
                executor.execute(() -> {
                    HttpHandler httpHandler = new HttpHandler();
                    String u = link.url + "/Logout";
                    String s = httpHandler.getAccess(u);
                    RESPONDED = s;
                    if(s!=null){
                        System.out.println(s);
                        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();
                        i3 = new Intent(Dashboard.this, MainActivity.class);
                        startActivity(i3);
                        finish();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), RESPONDED, Toast.LENGTH_SHORT).show();
                        }
                    });
                });
            }
        });

        dft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i4 = new Intent(Dashboard.this,KendaraanList.class);
                startActivity(i4);
                finish();
            }
        });

        bkg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i7 = new Intent(Dashboard.this,Booking.class);
                startActivity(i7);
                finish();
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String urname = sharedPreferences.getString("Username", null); // Retrieve the JSON string
        String prwd = sharedPreferences.getString("Password", null);
        String rle = sharedPreferences.getString("Role", null);

            if ("2".equals(rle)) {
            Roles = "User";
            }
            ArrayList<String> mkey = new ArrayList<String>();
            ArrayList mval = new ArrayList();
            mkey.add("Username");
            mkey.add("Role");
            mval.add(urname);
            mval.add(Roles);

            Executor executor = Executors.newSingleThreadExecutor();
            executor.execute(() -> {
                HttpHandler httpHandler = new HttpHandler();
                String u = link.url + "/Listparkiran";
                String s = httpHandler.getAccess2(u, mkey, mval);

                try {
                    JSONArray jsonArray = new JSONArray(s);

                    // Array untuk menyimpan data LahanParkir
                    String[] lahanParkir = new String[jsonArray.length()];

                    // Array untuk menyimpan data Status
                    String[] status = new String[jsonArray.length()];
                    String statusString = null;

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        // Mengambil data LahanParkir dan memasukkannya ke dalam array
                        String lahanParkirData = jsonObject.getString("LahanParkir");
                        lahanParkir[i] = lahanParkirData;

                        // Mengambil data Status dan memasukkannya ke dalam array
                        int statusData = jsonObject.getInt("Status");

                        if (statusData == 0){
                            statusString =  "Kosong";
                        } else if (statusData == 1) {
                            statusString = "Booked";
                        } else if (statusData == 2) {
                            statusString = "Terisi";
                        }
                       //(statusData == 2) ?  :  :;
                        status[i] = statusString;
                    }
                    for (int i = 0; i < lahanParkir.length; i++) {
                        Log.d("REST", "Lahan Parkir: " + lahanParkir[i] + ", Status: " + status[i]);
                    }
                    List a = new List(lahanParkir, status);
                    lv.setAdapter(a);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                Log.d("REST", s);

            });


    }
}