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

public class KendaraanList extends AppCompatActivity {
    public String Roles;
    public ListView lv;
    public Button dftkdr,bck;
    public Intent I5,I6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kendaraan_list);
        lv = (ListView) findViewById(R.id.lv2);
        dftkdr = findViewById(R.id.DaftarKendaraan);
        bck = findViewById(R.id.Back);
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
        mkey.add("Password");
        mkey.add("Role");
        mval.add(urname);
        mval.add(prwd);
        mval.add(Roles);

        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            HttpHandler httpHandler = new HttpHandler();
            String u = link.url + "/Kendaraan";
            String s = httpHandler.getAccess2(u, mkey, mval);
            if(s!=null){
            try {
                JSONObject js = new JSONObject(s);
                String rsp = js.getString("Respon");
                JSONArray jsonArray = new JSONArray(rsp);

                // Array untuk menyimpan data LahanParkir
                String[] kendaraan = new String[jsonArray.length()];

                // Array untuk menyimpan data Status
                String[] platnomor = new String[jsonArray.length()];

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    // Mengambil data LahanParkir dan memasukkannya ke dalam array
                    String kendaraanData = jsonObject.getString("Jenis");
                    kendaraan[i] = kendaraanData;

                    // Mengambil data Status dan memasukkannya ke dalam array
                    String platnomorData = jsonObject.getString("PlatNomor");
                    //String statusString = (statusData == 1) ? "Booked" : "Kosong";
                    platnomor[i] = platnomorData;
                }
                for (int i = 0; i < kendaraan.length; i++) {
                    Log.d("REST", "Kendaraan: " + kendaraan[i] + ", PlatNomor: " + platnomor[i]);
                }
                List2 a = new List2(kendaraan, platnomor);
                lv.setAdapter(a);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            Log.d("REST", s);
            }

            else if(s==null || s.isEmpty()){
                Toast.makeText(getApplicationContext(), "Anda Belum Mendaftarkan Kendaraan!!!!", Toast.LENGTH_SHORT).show();
            }
            Log.d("REST", s);
        });

        dftkdr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                I5 = new Intent(KendaraanList.this,DaftarKendaraan.class);
                startActivity(I5);
                finish();
            }
        });
        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                I6 = new Intent(KendaraanList.this,Dashboard.class);
                startActivity(I6);
                finish();
            }
        });
    }
}