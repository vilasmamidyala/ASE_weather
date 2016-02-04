package com.example.vilas.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Homepage extends AppCompatActivity {

    TextView displayText;
    String response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        displayText = (TextView) findViewById(R.id.display);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    public void Signout(View vv)
    {
        if (vv.getId()==R.id.signout)
        {
            Intent i = new Intent(Homepage.this, MainActivity.class);
            startActivity(i);
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void weather(View v) {
        if (v.getId() == R.id.weather) {
            String zipCode = ((EditText) findViewById(R.id.zip)).getText().toString();
            final String client_id = "YFl3BnK2VKw8uDGzTac49";
            final String client_secret = "QZEsvIi0lktKzykzr3SROaPjCgxMUVIHpDxrl5JD";
            final String urlText = "http://api.aerisapi.com/observations/"+zipCode+"?+client_id="+client_id+"&client_secret="+client_secret;


            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        URL url = new URL(urlText);
                        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                        urlConnection.setRequestMethod("GET");
                        urlConnection.setDoInput(true);
                        urlConnection.setDoOutput(true);
                        StringBuilder stringBuilder = new StringBuilder();
                        InputStream is;
                        is = urlConnection.getInputStream();
                        BufferedReader br = new BufferedReader(new InputStreamReader(is));
                        String line;
                        while ((line = br.readLine()) != null) {
                            stringBuilder.append(line);
                        }
                        response = stringBuilder.toString();
                        System.out.println(stringBuilder.toString());
                        is.close();
                        urlConnection.disconnect();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    try
                    {
                     JSONObject ResponseObject = new JSONObject(response);
                     JSONObject j = ResponseObject.getJSONObject("response").getJSONObject("ob");
                        JSONObject ja = ResponseObject.getJSONObject("response").getJSONObject("ob");
                     System.out.println(j.getString("weather"));
                    displayText.setText(j.getString("weather")+ja.getString("tempC"));

                    }
                    catch(JSONException e){}
                }

            },1500);


        }
    }
}

