package com.example.implicitintentsample;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class IntentStartActivity extends AppCompatActivity {

    private double _latitude = 40;
    private double _longitude = 140;
    private double _accuracy = 0;
    private TextView _tvLatitude;
    private TextView _tvLongitude;
    private TextView _tvAccuracy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_start);
        _tvLatitude = findViewById(R.id.tvLatitude);
        _tvLongitude = findViewById(R.id.tvLongitude);
        _tvAccuracy = findViewById(R.id.tvAccuracy);
        LocationManager locationManager =
                (LocationManager) getSystemService
                        (Context.LOCATION_SERVICE);
        if (checkSelfPermission
                (Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            String[] permissions =
                    {Manifest.permission.ACCESS_FINE_LOCATION};
            requestPermissions(permissions, 1000);
            //return;
        }
        try {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 0, 0,
                    new GPSLocationListener());
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }//onCreate

    @Override
    public void onRequestPermissionsResult
            (int requestCode, @NonNull String[] permissions,
             @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(getApplicationContext(), IntentStartActivity.class);
            startActivity(intent);
            /*LocationManager locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
            if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
              != PackageManager.PERMISSION_GRANTED){
                return;
            }//if
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    0,0, new GPSLocationListener());
            */
        }
    }

    private class GPSLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            _latitude = location.getLatitude();
            _longitude = location.getLongitude();
            _accuracy = location.getAccuracy();
            _tvLatitude.setText(Double.toString(_latitude));
            _tvLongitude.setText(Double.toString(_longitude));
            _tvAccuracy.setText(Double.toString(_accuracy));
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }

    void onMapSearchButtonClick(View view) {
        EditText etSearchWord = findViewById(R.id.etSearchWord);
        String searchWord = etSearchWord.getText().toString();

        try {
            searchWord = URLEncoder.encode(searchWord, "UTF-8");
            String uriStr = "geo:0,0?q=" + searchWord;
            //uriStr = "geo:40,140";
            Log.d("onMapSearchButtonClick", uriStr);
            Uri uri = Uri.parse(uriStr);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    void onMapShowCurrentButtonClick(View view) {
        String uriStr = "geo:" + _latitude + "," + _longitude;
        Uri uri = Uri.parse(uriStr);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
