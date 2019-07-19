package com.example.servicesample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        boolean fromNotification = intent.getBooleanExtra("fromNotification", false);
        if(fromNotification) {
            findViewById(R.id.btPlay).setEnabled(false);
            findViewById(R.id.btStop).setEnabled(true);
        }
    }

    void onPlayButtonClick(View view){
        Intent intent = new Intent
                (getApplicationContext(),
                        SoundManageService.class);
        startService(intent);
        ((Button)findViewById(R.id.btPlay)).setEnabled(false);
        ((Button)findViewById(R.id.btStop)).setEnabled(true);
    }

    void onStopButtonClick(View view){
        Intent intent = new Intent(
                getApplicationContext(),
                SoundManageService.class
        );
        stopService(intent);
        ((Button)findViewById(R.id.btPlay)).setEnabled(true);
        ((Button)findViewById(R.id.btStop)).setEnabled(false);
    }
}
