package com.gmail.takashi316.implicitintentsample;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class IntentStartActivity extends AppCompatActivity {

    private double _longitude = 0;
    private double _latitude = 0;
    private TextView _tvLatitude;
    private TextView _tvLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_start);
        _tvLatitude = findViewById(R.id.tvLatitude);
        _tvLongitude = findViewById(R.id.tvLongitude);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions
                    (IntentStartActivity.this, permissions, 1000);
            return;
        }//if
        LocationManager locationManager
                = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                0, 0, new GPSLocationListener());
    }//onCreate

    @Override
    public void onRequestPermissionsResult
            (int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000 && grantResults[0]
                == PackageManager.PERMISSION_GRANTED) {
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission
                    (IntentStartActivity.this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates
                        (LocationManager.GPS_PROVIDER,
                                0,0,
                                new GPSLocationListener());
            }//if
        }//if
    }//onRequestPermissionsResult

    class GPSLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            _longitude = location.getLongitude();
            _latitude = location.getLatitude();
            _tvLatitude.setText(""+_latitude);
            _tvLongitude.setText(""+_longitude);
        }//onLocationChanged

        @Override
        public void onStatusChanged(String provider,
                                    int status, Bundle extras) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onProviderDisabled(String provider) {}
    }

    public void onMapSearchButtonClick(View view){
        EditText etSearchWord = findViewById(R.id.etSearchWord);
        String searchWord = etSearchWord.getText().toString();

        try{
            //バイト列をURLエンコーディング（%エンコーディング）する
            searchWord = URLEncoder.encode(searchWord, "UTF-8");
            String uriStr = "geo:0,0?q=" + searchWord;
            Uri uri = Uri.parse(uriStr);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        } catch (UnsupportedEncodingException e){
            Log.e("IntentStartActivity", "検索キーワード変換失敗");
        }
    }

    public void onMapShowCurrentButtonClick(View view) {
        String uriStr = "geo:" + _latitude + "," + _longitude;
        Uri uri = Uri.parse(uriStr);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }//onMapShowCurrentButtonClick

}//IntentStartActivity
