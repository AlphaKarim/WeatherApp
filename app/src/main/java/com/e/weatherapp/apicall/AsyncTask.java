package com.e.weatherapp.apicall;

import android.content.Context;
import android.util.Log;


import com.e.weatherapp.model.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AsyncTask extends android.os.AsyncTask<String, String, String>  {

    ApiCalling callapi= new ApiCalling();
    ArrayList<Weather> whearedetails=new ArrayList<>();
    String resp;
    double lat, lng;
    Calback calback;


//    Constructer////


    public AsyncTask(Context context, double lat, double lng, Calback calback) {

        this.lat = lat;
        this.lng = lng;
        this.calback = calback;
    }

    @Override
    protected String doInBackground(String... params) {

        resp=callapi.ApiCall(lat,lng);
//        Log.e("resp",resp);
        return  resp;
    }

    @Override
    protected void onPreExecute() {


    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(resp);
        try {
            JSONObject object1 = new JSONObject(resp);
            String location = object1.getString("name");
//            String temp = object1.getJSONObject("main").getString("temp");
            JSONArray array = new JSONArray();
            array = object1.getJSONArray("weather");
            JSONObject arrayobj1 = new JSONObject();
            for (int i = 0; i < array.length(); i++) {
                arrayobj1=array.getJSONObject(i);
            }
            String state= arrayobj1.getString("description");
            JSONObject object2 = new JSONObject();
            object2 = object1.getJSONObject("main");
            String pressure = object2.getString("pressure");
            String temp = object2.getString("temp");
            String humidity = object2.getString("humidity");
            JSONObject object3=new JSONObject();
            object3=object1.getJSONObject("coord");
            String lat=object3.getString("lat");
            String lon=object3.getString("lon");
//            Log.e("AsyncTask","location"+location);
            Weather list = new Weather(location, state, pressure, humidity, temp);
            whearedetails.add(list);


            Log.e("AsyncTask",""+whearedetails.size());
            calback.callBackListener(whearedetails);

        } catch (JSONException e) {
            Log.e("JSONException",e.toString());
            e.printStackTrace();
        }
    }
}
