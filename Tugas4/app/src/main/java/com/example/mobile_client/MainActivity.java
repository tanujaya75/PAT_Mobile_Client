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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    public EditText uname, pwd;
    public Button log, reg;
    public Intent i1,i2;
    public String s = null;
    public String msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        uname = findViewById(R.id.Username);
        pwd = findViewById(R.id.Password);
        log = findViewById(R.id.Login);
        reg = findViewById(R.id.Register);

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> mkey = new ArrayList<String>();
                ArrayList mval = new ArrayList();
                mkey.add("Username");
                mkey.add("Password");
                mkey.add("Role");
                mval.add(uname.getText().toString());
                mval.add(pwd.getText().toString());
                mval.add("User");
                if (!uname.getText().toString().isEmpty() && !pwd.getText().toString().isEmpty()){
                    Executor executor = Executors.newSingleThreadExecutor();
                    executor.execute(() -> {
                    HttpHandler httpHandler = new HttpHandler();
                    String u = link.url + "/Login";
                    s = httpHandler.postAccess(u, mkey, mval);
                    if (s != null) {
                        try {
                            JSONObject js = new JSONObject(s);
                            String un = js.getString("Username");
                            String pd = js.getString("Password");
                            String rl = js.getString("Role");
                            msg = js.getString("Message");
                            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("Username", un);
                            editor.putString("Password", pd);
                            editor.putString("Role", rl);
                            editor.apply();
                            String urname = sharedPreferences.getString("Username", null); // Retrieve the JSON string
                            String prwd = sharedPreferences.getString("Password", null);
                            String rle = sharedPreferences.getString("Role", null);
                            Log.d("REST", "Username: " + urname + "," + "Password: " + prwd + "," + "Role :" + rle);
                        }
                        catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {

                            public void run () {
                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                                i2 = new Intent(MainActivity.this, Dashboard.class);
                                startActivity(i2);
                                finish();
                            }

                        });
                    }

                });
                }
                else if (uname.getText().toString().isEmpty()){
                    Toast toast = Toast.makeText(MainActivity.this, "Username Kosong!!!", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if (pwd.getText().toString().isEmpty()) {
                    Toast toast = Toast.makeText(MainActivity.this, "Password Kosong!!!", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if (uname.getText().toString().isEmpty() && pwd.getText().toString().isEmpty()) {
                    Toast toast = Toast.makeText(MainActivity.this, "Username dan Password Kosong!!!", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i1 = new Intent(MainActivity.this, Register.class);
                i1.putExtra("Message: ", "Silahkan Registrasi!!");
                startActivity(i1);
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String urname = sharedPreferences.getString("Username", null); // Retrieve the JSON string
        String prwd = sharedPreferences.getString("Password", null);
        String rle = sharedPreferences.getString("Role", null);
        System.out.println(rle);
        if (urname != null && rle != null && !urname.isEmpty() && !rle.isEmpty()) {
            // User is already signed in, proceed to the main activity
            i2 = new Intent(MainActivity.this, Dashboard.class);
            startActivity(i2);
            finish();
        }
    }
}