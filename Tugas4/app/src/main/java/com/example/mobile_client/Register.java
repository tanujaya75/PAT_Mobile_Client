package com.example.mobile_client;

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

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Register extends AppCompatActivity {
    public EditText uname,eml,pwd;
    public Button sbmt,ccl;
    public Intent i2;
    public String msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        uname = findViewById(R.id.Username2);
        eml = findViewById(R.id.Email);
        pwd = findViewById(R.id.Password2);
        sbmt = findViewById(R.id.Submit2);
        ccl = findViewById(R.id.Cancel);
        sbmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> mkey = new ArrayList<String>();
                ArrayList mval = new ArrayList();
                mkey.add("Username");
                mkey.add("Email");
                mkey.add("Password");

                mval.add(uname.getText().toString());
                mval.add(eml.getText().toString());
                mval.add(pwd.getText().toString());
                Executor executor = Executors.newSingleThreadExecutor();
                executor.execute(() -> {
                    HttpHandler httpHandler = new HttpHandler();
                    String u = link.url + "/RegisterAndroid";
                    String s = httpHandler.postAccess(u, mkey, mval);
                    Log.d("REST",s);
                    if(s!=null && !s.isEmpty()){
                        try {
                            JSONObject js = new JSONObject(s);
                            msg = js.getString("Message");
                        }
                        catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            public void run () {
                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    i2 = new Intent(Register.this, MainActivity.class);
                    startActivity(i2);
                    finish();
                });

            }
        });

        ccl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Membatalkan Registrasi", Toast.LENGTH_SHORT).show();
                i2 = new Intent(Register.this, MainActivity.class);
                startActivity(i2);
                finish();
            }
        });
    }
}