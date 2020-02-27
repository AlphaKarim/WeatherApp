package com.e.weatherapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.e.weatherapp.R;
import com.e.weatherapp.apicall.AsyncTask;
import com.e.weatherapp.apicall.Calback;
import com.e.weatherapp.model.Weather;

import java.util.ArrayList;

public class MainScreen extends AppCompatActivity implements LocationListener, Calback {

    ImageView bgapp, clover;
    LinearLayout textsplash, texthome, menus;
    Animation frombottom;
    TextView location;
    TextView temp;
    TextView state;
    protected LocationListener locationListener;
    protected LocationManager locationManager;
    ArrayList<Weather> whearedetails = new ArrayList<>();
    Calback calback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);


        bgapp = (ImageView) findViewById(R.id.bgapp);
        clover = (ImageView) findViewById(R.id.clover);
        textsplash = (LinearLayout) findViewById(R.id.textsplash);
        texthome = (LinearLayout) findViewById(R.id.texthome);
        menus = (LinearLayout) findViewById(R.id.menus);

        bgapp.animate().translationY(-1250).setDuration(800).setStartDelay(2000);
        clover.animate().alpha(0).setDuration(800).setStartDelay(2000);
        textsplash.animate().translationY(140).alpha(0).setDuration(800).setStartDelay(2000);

        menus.startAnimation(frombottom);
        texthome.startAnimation(frombottom);
        ViewIntialization();
        calback = this;
        latLonValues();
    }

    public void ViewIntialization() {
        location = (TextView) findViewById(R.id.location);
        temp = (TextView) findViewById(R.id.temp);
        state = (TextView) findViewById(R.id.state);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


    }

    public void SetValues(double lat, double lng) {
        AsyncTask run = new AsyncTask(MainScreen.this, lat, lng, calback);
        run.execute();
    }

    public void latLonValues() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e("latLonValues", "requestPermissions");
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        } else {
            Log.e("latLonValues", "else");
            getLocation();
        }

    }

    void getLocation() {
        try {
//            Toast.makeText(this, "Getting Results, Please wait", Toast.LENGTH_SHORT).show();
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
//        latlon.setText("Latitude: " + location.getLatitude() + "\n Longitude: " + location.getLongitude());


        try {
          /*  Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            latlon.setText(latlon.getText() + "\n"+addresses.get(0).getAddressLine(0)+", "+
                    addresses.get(0).getAddressLine(1)+", "+addresses.get(0).getAddressLine(2));*/
            Log.e("Latitude: ", "" + location.getLatitude());
            Log.e("Longitude: ", "" + location.getLongitude());
            if (location.getLongitude() > 0) {
                SetValues(location.getLatitude(), location.getLongitude());
              /*  ApiCalling call = new ApiCalling();
                call.ApiCall(location.getLatitude(),location.getLongitude());*/
            }


        } catch (Exception e) {

        }

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(MainScreen.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void callBackListener(ArrayList<Weather> datalist) {
        whearedetails = new ArrayList<>();
        whearedetails.addAll(datalist);

        location.setText(whearedetails.get(0).getLocation());
        temp.setText(whearedetails.get(0).getTemp());
        state.setText(whearedetails.get(0).getState());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101) {


            // if (permissions[i].equals(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.e("onRequestPermission", "PERMISSION_GRANTED");
                getLocation();
            } else {
//                    denidPopup();
            }
            //}

        }
    }

}
