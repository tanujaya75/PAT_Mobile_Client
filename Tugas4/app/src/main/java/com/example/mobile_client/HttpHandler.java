package com.example.mobile_client;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class HttpHandler {
    public String getAccess(String url){
        String response =  null;
        URL u = null;
        try {
            u = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setRequestMethod("GET");
            InputStream i = new BufferedInputStream(conn.getInputStream());
            response = converttostring(i);
        }catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return response;
    }

    public String getAccess2(String url, ArrayList<String> key, ArrayList value){
        String response =  null;
        URL u = null;
        try {
            u = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type","application/json; charset=utf-8");
            JSONObject params = new JSONObject();
            for (int x=0;x<key.size();x++){
                params.put(key.get(x),value.get(x));
            }
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(params.toString());
            wr.flush();
            InputStream i = new BufferedInputStream(conn.getInputStream());
            response = converttostring(i);
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (JSONException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response);
        return response;
    }

    public String postAccess(String url, ArrayList<String> key, ArrayList value){
        String response = null;
        URL u = null;
        try{
            u = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type","application/json; charset=utf-8");
            JSONObject params = new JSONObject();
            for (int x=0;x<key.size();x++){
                params.put(key.get(x),value.get(x));
            }
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(params.toString());
            wr.flush();
            InputStream i = new BufferedInputStream(conn.getInputStream());
            response = converttostring(i);

        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return response;

    }

    private String converttostring(InputStream i) {
        BufferedReader r = new BufferedReader(new InputStreamReader(i));
        StringBuilder sb = new StringBuilder();
        String dummy;
        try {
            while ((dummy = r.readLine()) != null) {
                sb.append(dummy).append("\n");
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return sb.toString();
    }
}
