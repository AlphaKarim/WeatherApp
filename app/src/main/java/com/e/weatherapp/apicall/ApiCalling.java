package com.e.weatherapp.apicall;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiCalling {
    public static final String REQUEST_METHOD = "GET";
    public static final int READ_TIMEOUT = 4000;
    public static final int CONNECTION_TIMEOUT = 4000;

    public String ApiCall(double lat, double lng) {
        String result;
        String inputLine;
        HttpURLConnection urlConnection = null;
        try {
            URL myUrl = new URL(" YOUR API URL ");
            Log.e("url", ""+myUrl);
            HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();
            connection.setRequestMethod(REQUEST_METHOD);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);

            connection.connect();
            InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(streamReader);
            StringBuilder stringBuilder = new StringBuilder();
            while ((inputLine = reader.readLine()) != null) {
                stringBuilder.append(inputLine);
            }
            reader.close();
            streamReader.close();
            result = stringBuilder.toString();
        } catch (Exception e) {
            Log.e("JSONException",e.toString());
            e.printStackTrace();
            result = null;


        }
        return  result;

    }
}
