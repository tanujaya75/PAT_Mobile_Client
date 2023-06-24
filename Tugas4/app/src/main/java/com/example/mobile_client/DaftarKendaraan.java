package com.example.mobile_client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DaftarKendaraan extends AppCompatActivity {
    EditText jns,plat;
    Button sbt,ccl;
    public String s;
    public String msg,Roles;
    Intent i5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_kendaraan);
        jns = findViewById(R.id.JenisKendaraan);
        plat = findViewById(R.id.PlatNomor);
        sbt = findViewById(R.id.Submit3);
        ccl = findViewById(R.id.Cancel2);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String urname = sharedPreferences.getString("Username", null); // Retrieve the JSON string
        String prwd = sharedPreferences.getString("Password", null);
        String rle = sharedPreferences.getString("Role", null);

        if ("2".equals(rle)) {
            Roles = "User";
        }

        sbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> mkey = new ArrayList<String>();
                ArrayList mval = new ArrayList();
                mkey.add("Username");
                mkey.add("Password");
                mkey.add("Role");
                mkey.add("Jenis");
                mkey.add("PlatNomor");
                mval.add(urname);
                mval.add(prwd);
                mval.add(Roles);
                mval.add(jns.getText().toString());
                mval.add(plat.getText().toString());

                if(!jns.getText().toString().isEmpty() && !plat.getText().toString().isEmpty())
                {
                    Executor executor = Executors.newSingleThreadExecutor();
                    executor.execute(() -> {
                        HttpHandler httpHandler = new HttpHandler();
                        String u = link.url + "/DaftarKendaraan";
                        s = httpHandler.postAccess(u, mkey, mval);
                        msg = s;
                        if (s != null) {
                            try
                            {
                                runOnUiThread(new Runnable() {

                                    public void run () {
                                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                                        i5 = new Intent(DaftarKendaraan.this, Dashboard.class);
                                        startActivity(i5);
                                        finish();
                                    }

                                });
                            }
                            catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }

                    });

                }
            }
        });

        ccl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Membatalkan Pendaftaran Kendaraan!!!", Toast.LENGTH_SHORT).show();
                i5 = new Intent(DaftarKendaraan.this, Dashboard.class);
                startActivity(i5);
                finish();
            }
        });
    }
}