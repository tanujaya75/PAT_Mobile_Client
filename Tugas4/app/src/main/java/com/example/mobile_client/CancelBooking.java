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


public class CancelBooking extends AppCompatActivity {
    public String Roles;

    public Button sbt,bck;
    public Intent i9;

    public Spinner dropjeniskendaraan;
    public Spinner dropplatnomor;
    public Spinner dropbookingid;


    public String JNS,PLT,BKID;
    String[] JenisKendaraan;
    String[] PlatNomor;
    String[] BookID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_booking);
        bck = findViewById(R.id.Back2);
        sbt = findViewById(R.id.Cancel3);
        dropjeniskendaraan = findViewById(R.id.Jenis2);
        dropplatnomor = findViewById(R.id.Plat2);
        dropbookingid = findViewById(R.id.BookingId);

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

            String u2 = link.url + "/ShowBookingId";
            String s2 = httpHandler.getAccess2(u2, mkey, mval);

            try {
                JSONObject js2 = new JSONObject(s2);
                String rsp2 = js2.getString("Respon");
                JSONArray jsonArray = new JSONArray(rsp2);


                BookID = new String[jsonArray.length()];

                for (int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String BookingIDData = jsonObject.getString("ID_Booking");
                    BookID[i] = BookingIDData;

                }
                runOnUiThread(() -> {

                    ArrayAdapter<String> adapterbookingid = new ArrayAdapter<>(CancelBooking.this, android.R.layout.simple_spinner_dropdown_item, BookID);
                    dropbookingid.setAdapter(adapterbookingid);
                });

            }
            catch (JSONException e) {
                e.printStackTrace();
            }

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
                Log.d("REST2", PLT);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });

        dropbookingid.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                BKID= adapterView.getItemAtPosition(i).toString();
                Log.d("REST3", BKID);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });
        sbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String JENISKENDARAAN = JNS;
                String PLATNOMOR = PLT;
                String BOOKINGID= BKID;

                ArrayList<String> mkey2 = new ArrayList<String>();
                ArrayList mval2 = new ArrayList();
                mkey2.add("Username");
                mkey2.add("Password");
                mkey2.add("Role");
                mkey2.add("ID_Booking");
                mkey2.add("Jenis");
                mkey2.add("PlatNomor");
                mval2.add(urname);
                mval2.add(prwd);
                mval2.add(Roles);
                mval2.add(BOOKINGID);
                mval2.add(JENISKENDARAAN);
                mval2.add(PLATNOMOR);

                Executor executor = Executors.newSingleThreadExecutor();
                executor.execute(() -> {
                    HttpHandler httpHandler = new HttpHandler();
                    String u3 = link.url + "/Cancel";
                    String s3 = httpHandler.postAccess(u3, mkey2, mval2);
                    String msg = s3;
                    if (s3 != null) {
                        try
                        {
                            runOnUiThread(new Runnable()
                            {

                                public void run ()
                                {
                                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

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
                i9 = new Intent(CancelBooking.this,Booking.class);
                startActivity(i9);
                finish();
            }
        });
    }
}