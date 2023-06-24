package com.example.mobile_client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Booking extends AppCompatActivity {
    public String Roles;
    public Button bck,bk,ccl;
    public Intent i8,i10;

    public Spinner dropjeniskendaraan;
    public Spinner dropplatnomor;
    public Spinner droplahanparkir;

    public String JNS,PLT,LHN;
    String[] JenisKendaraan;
    String[] PlatNomor;
    String[] LahanParkir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        bck = findViewById(R.id.Back);
        bk = findViewById(R.id.Book);
        ccl = findViewById(R.id.CancelBook);
        dropjeniskendaraan = findViewById(R.id.Jenis);
        dropplatnomor = findViewById(R.id.Plat);
        droplahanparkir = findViewById(R.id.Parkir);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String urname = sharedPreferences.getString("Username", null); // Retrieve the JSON string
        String prwd = sharedPreferences.getString("Password", null);
        String rle = sharedPreferences.getString("Role", null);

        if ("2".equals(rle))
        {
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

            try {
                JSONObject js = new JSONObject(s);
                String rsp = js.getString("Respon");
                JSONArray jsonArray = new JSONArray(rsp);

                JenisKendaraan = new String[jsonArray.length()];
                PlatNomor = new String[jsonArray.length()];

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String JenisKendaraanData= jsonObject.getString("Jenis");
                    JenisKendaraan[i] = JenisKendaraanData;

                    String PlatNomorData = jsonObject.getString("PlatNomor");
                    PlatNomor[i] = PlatNomorData;
                }

                runOnUiThread(() -> {

                    ArrayAdapter<String> adapterkendaraan = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, JenisKendaraan);
                    dropjeniskendaraan.setAdapter(adapterkendaraan);


                    ArrayAdapter<String> adapterplatnomor = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, PlatNomor);
                    dropplatnomor.setAdapter(adapterplatnomor);

                });

            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

            String u2 = link.url + "/Listparkiran2";
            String s2 = httpHandler.getAccess2(u2, mkey, mval);

            try {
                JSONArray jsonArray = new JSONArray(s2);

                LahanParkir = new String[jsonArray.length()];

                for (int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String LahanParkirnData = jsonObject.getString("LahanParkir");
                        LahanParkir[i] = LahanParkirnData;

                }
                runOnUiThread(() -> {

                    ArrayAdapter<String> adapterlahanparkir = new ArrayAdapter<>(Booking.this, android.R.layout.simple_spinner_dropdown_item, LahanParkir);
                    droplahanparkir.setAdapter(adapterlahanparkir);
                });

            }
            catch (JSONException e) {
                e.printStackTrace();
            }

            Log.d("REST", s);
            Log.d("REST", s2);

        });


        dropjeniskendaraan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                JNS = adapterView.getItemAtPosition(i).toString();
                Log.d("REST1", JNS);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });

        dropplatnomor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                PLT = adapterView.getItemAtPosition(i).toString();
                Log.d("REST1", PLT);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });

        droplahanparkir.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                LHN = adapterView.getItemAtPosition(i).toString();
                Log.d("REST1", LHN);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });


        bk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String JENISKENDARAAN = JNS;
                String PLATNOMOR = PLT;
                String LAHANPARKIR = LHN;

                ArrayList<String> mkey2 = new ArrayList<String>();
                ArrayList mval2 = new ArrayList();
                mkey2.add("Username");
                mkey2.add("Password");
                mkey2.add("Role");
                mkey2.add("Parkiran");
                mkey2.add("Jenis");
                mkey2.add("PlatNomor");
                mval2.add(urname);
                mval2.add(prwd);
                mval2.add(Roles);
                mval2.add(LAHANPARKIR);
                mval2.add(JENISKENDARAAN);
                mval2.add(PLATNOMOR);

                Executor executor = Executors.newSingleThreadExecutor();
                executor.execute(() -> {
                    HttpHandler httpHandler = new HttpHandler();
                    String u3 = link.url + "/Booking";
                    String s3 = httpHandler.postAccess(u3, mkey2, mval2);
                    String msg = s3;
                    String Pesan;
                    try {
                        JSONObject js = new JSONObject(msg);
                        Pesan = js.getString("Status");

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }


                    if (s3 != null) {
                        try
                        {
                            runOnUiThread(new Runnable()
                            {

                                public void run ()
                                {
                                    Toast.makeText(getApplicationContext(), Pesan, Toast.LENGTH_SHORT).show();

                                }

                            });
                        }
                        catch (Exception ex)
                        {
                            ex.printStackTrace();
                        }
                    }

                });
            }
        });

        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i8 = new Intent(Booking.this,Dashboard.class);
                startActivity(i8);
                finish();
            }
        });
        ccl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i10 = new Intent(Booking.this,CancelBooking.class);
                startActivity(i10);
                finish();
            }
        });

    }
}