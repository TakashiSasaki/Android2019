package com.gmail.takashi316.implicitintentsample;

import android.content.Intent;
import android.net.Uri;
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
    }//onCreate

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
